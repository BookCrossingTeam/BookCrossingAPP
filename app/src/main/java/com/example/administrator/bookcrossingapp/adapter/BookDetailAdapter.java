package com.example.administrator.bookcrossingapp.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.activity.ExchangeBookDetailsActivity;
import com.example.administrator.bookcrossingapp.activity.PosingWantingActivity;
import com.example.administrator.bookcrossingapp.activity.SearchDetailActivity;
import com.example.administrator.bookcrossingapp.activity.ShareListActivity;
import com.example.administrator.bookcrossingapp.activity.WantListActivity;
import com.example.administrator.bookcrossingapp.datamodel.BookDetail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 57010 on 2017/5/17.
 */

public class BookDetailAdapter extends RecyclerView.Adapter<BookDetailAdapter.ViewHolder> {

    private List<BookDetail> mBookDetailList;
    private static final String TAG = "BookDetailAdapter";
    private Context context;
    private int flagIntent = 1;
    private Activity mActivity;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView bookName;
        TextView author;
        TextView posetime;
        ImageView bookimg;
        ImageView nameheadImg;
        View bookView;
        RelativeLayout boxView;


        public ViewHolder(View view) {
            super(view);
            bookView = view;
            boxView = (RelativeLayout) view.findViewById(R.id.book_box);
            username = (TextView) view.findViewById(R.id.book_detail_username);
            bookName = (TextView) view.findViewById(R.id.book_detail_bookname);
            author = (TextView) view.findViewById(R.id.book_detail_author);
            bookimg = (ImageView) view.findViewById(R.id.book_detail_pic);
            posetime = (TextView) view.findViewById(R.id.book_detail_posetime);
            nameheadImg = (ImageView) view.findViewById(R.id.book_detail_icon);
        }
    }

    public BookDetailAdapter(List<BookDetail> BookDetailList) {
        mBookDetailList = BookDetailList;
    }

    public BookDetailAdapter(List<BookDetail> BookDetailList, Activity activity) {
        mActivity = activity;
        mBookDetailList = BookDetailList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_detail_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        if (flagIntent == 1) {
            holder.bookView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    BookDetail bookDetail = mBookDetailList.get(position);
                    Intent intent = new Intent(v.getContext(), ExchangeBookDetailsActivity.class);
                    intent.putExtra("Username", bookDetail.getUsername());
                    intent.putExtra("BookName", bookDetail.getBookName());
                    intent.putExtra("Author", bookDetail.getAuthor());
                    intent.putExtra("Press", bookDetail.getPress());
                    intent.putExtra("RecommendedReason", bookDetail.getRecommendedReason());
                    intent.putExtra("BookImageUrl", bookDetail.getBookImageUrl());
                    intent.putExtra("userid", bookDetail.getUserid());
                    intent.putExtra("nameheadUrl", bookDetail.getUserheadpath());
                    intent.putExtra("bookid", bookDetail.getBookid());
                    intent.putExtra("bookType", bookDetail.getBookType());
                    view.getContext().startActivity(intent);
                }
            });

            holder.bookView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(!(mActivity instanceof ShareListActivity))
                        return false;

                    final int position = holder.getAdapterPosition();
                    final BookDetail bookDetail = mBookDetailList.get(position);
                    Log.i(TAG, "onLongClick: " + bookDetail.getBookName());

                    SharedPreferences pref = mActivity.getSharedPreferences("user_info", MODE_PRIVATE);
                    int userid = pref.getInt("userid", 0);
                    if(userid!=bookDetail.getUserid())
                        return false;

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setTitle("刷新");
                    alertDialog.setMessage("刷新\t" + bookDetail.getBookName());

                    alertDialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // 获取数据
                                    try {
                                        SharedPreferences pref = mActivity.getSharedPreferences("user_info", MODE_PRIVATE);
                                        int userid = pref.getInt("userid", 0);

                                        OkHttpClient client = new OkHttpClient();
                                        RequestBody requestBody = new FormBody.Builder().add("id", bookDetail.getBookid() + "")
                                                .add("userid", userid + "")
                                                .build();
                                        Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/ShareBookUpdate").post(requestBody).build();
                                        Response response = client.newCall(request).execute();
                                        if (response.isSuccessful()) {
                                            final String responseData = response.body().string();
                                            if (responseData.equals("OK"))
                                                mActivity.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        BookDetail bookDetail = mBookDetailList.get(position);
                                                        bookDetail.setPosetime(System.currentTimeMillis());
                                                        bookDetail.setExchangeState(0);
                                                        mBookDetailList.remove(position);
                                                        mBookDetailList.add(0,bookDetail);
                                                        ((ShareListActivity) mActivity).adapter.notifyDataSetChanged();
                                                    }
                                                });
                                            else
                                            {
                                                mActivity.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(mActivity, responseData, Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                    });
                    alertDialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }

                    });
                    alertDialog.setNeutralButton("下架", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // 获取数据
                                    try {
                                        SharedPreferences pref = mActivity.getSharedPreferences("user_info", MODE_PRIVATE);
                                        int userid = pref.getInt("userid", 0);

                                        OkHttpClient client = new OkHttpClient();
                                        RequestBody requestBody = new FormBody.Builder().add("id", bookDetail.getBookid() + "")
                                                .add("userid", userid + "")
                                                .build();
                                        Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/ShareBookDel").post(requestBody).build();
                                        Response response = client.newCall(request).execute();
                                        if (response.isSuccessful()) {
                                            final String responseData = response.body().string();
                                            if (responseData.equals("OK"))
                                                mActivity.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        BookDetail bookDetail = mBookDetailList.get(position);
                                                        bookDetail.setExchangeState(1);
                                                        mBookDetailList.remove(position);
                                                        mBookDetailList.add(bookDetail);
                                                        ((ShareListActivity) mActivity).adapter.notifyDataSetChanged();
                                                    }
                                                });
                                            else
                                            {
                                                mActivity.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(mActivity, responseData, Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                    });
                    alertDialog.show();

                    return true;
                }
            });
        }

        if (flagIntent == 0) {
            holder.bookView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final int position = holder.getAdapterPosition();
                    final BookDetail bookDetail = mBookDetailList.get(position);
                    Log.i(TAG, "onLongClick: " + bookDetail.getBookName());

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setTitle("删除");
                    alertDialog.setMessage("删除\t" + bookDetail.getBookName());

                    alertDialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // 获取数据
                                    try {
                                        SharedPreferences pref = mActivity.getSharedPreferences("user_info", MODE_PRIVATE);
                                        int userid = pref.getInt("userid", 0);

                                        OkHttpClient client = new OkHttpClient();
                                        RequestBody requestBody = new FormBody.Builder().add("id", bookDetail.getBookid() + "")
                                                .add("userid", userid + "")
                                                .build();
                                        Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/wantDel").post(requestBody).build();
                                        Response response = client.newCall(request).execute();
                                        if (response.isSuccessful()) {
                                            String responseData = response.body().string();
                                            if (responseData.equals("OK"))
                                                mActivity.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        mBookDetailList.remove(position);
                                                        ((WantListActivity) mActivity).adapter.notifyDataSetChanged();
                                                    }
                                                });
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                    });
                    alertDialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }

                    });
                    alertDialog.show();

                    return true;
                }
            });

            holder.bookView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    BookDetail bookDetail = mBookDetailList.get(position);
                    Intent intent = new Intent(v.getContext(), SearchDetailActivity.class);
                    //这里会添加需要传递的消息
                    intent.putExtra("searchContent", bookDetail.getBookName());
                    view.getContext().startActivity(intent);
                }
            });
        }

        if (flagIntent == 2)
            holder.bookView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    BookDetail bookDetail = mBookDetailList.get(position);
                    Intent intent = new Intent(v.getContext(), ExchangeBookDetailsActivity.class);
                    //intent.putExtra("Username", bookDetail.getUsername());
                    //intent.putExtra("BookName", bookDetail.getBookName());
                    //intent.putExtra("Author", bookDetail.getAuthor());
                    //intent.putExtra("Press", bookDetail.getPress());
                    //intent.putExtra("RecommendedReason", bookDetail.getRecommendedReason());
                    intent.putExtra("BookImageUrl", bookDetail.getBookImageUrl());
                    //intent.putExtra("userid", bookDetail.getUserid());
                    //intent.putExtra("nameheadUrl",bookDetail.getUserheadpath());
                    intent.putExtra("bookid", bookDetail.getBookid());
                    intent.putExtra("bookType", bookDetail.getBookType());
                    mActivity.setResult(1, intent);
                    mActivity.finish();
                }
            });

        if (flagIntent == 3) {
            holder.nameheadImg.setVisibility(View.INVISIBLE);
            holder.username.setVisibility(View.GONE);
            holder.posetime.setVisibility(View.GONE);

            holder.bookView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    BookDetail bookDetail = mBookDetailList.get(position);
                    Intent intent = new Intent(v.getContext(), PosingWantingActivity.class);
                    intent.putExtra("BookName", bookDetail.getBookName());
                    intent.putExtra("Author", bookDetail.getAuthor());
                    intent.putExtra("Press", bookDetail.getPress());
                    intent.putExtra("BookImageUrl", bookDetail.getBookImageUrl());
                    view.getContext().startActivity(intent);
                }
            });
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BookDetail bookdetail = mBookDetailList.get(position);
        holder.username.setText(bookdetail.getUsername());
        holder.bookName.setText(bookdetail.getBookName());
        holder.author.setText(bookdetail.getAuthor());
        int exchangeState = bookdetail.getExchangeState();
        if (exchangeState == 1) {
            holder.boxView.setBackgroundResource(R.drawable.grey_box);
        } else {
            holder.boxView.setBackgroundResource(R.drawable.white_box);
        }

        holder.posetime.setText(new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date(bookdetail.getPosetime())));
        if (flagIntent == 3) {
            Glide.with(context).load(bookdetail.getBookImageUrl()).into(holder.bookimg);
            return;
        }
        Glide.with(context).load("http://120.24.217.191/Book/img/bookImg/" + bookdetail.getBookImageUrl()).into(holder.bookimg);
        Glide.with(context).load("http://120.24.217.191/Book/img/headImg/" + bookdetail.getUserheadpath()).error(R.drawable.icon).into(holder.nameheadImg);
    }

    @Override
    public int getItemCount() {
        return mBookDetailList.size();
    }

    public void setIntent(int cmd) {
        flagIntent = cmd;
    }
}
