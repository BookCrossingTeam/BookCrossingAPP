package com.example.administrator.bookcrossingapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.activity.ReviewsDetailActivity;
import com.example.administrator.bookcrossingapp.datamodel.ReviewItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/8.
 */

public class ReviewItemAdapter extends RecyclerView.Adapter<ReviewItemAdapter.ViewHolder> {
    private List<ReviewItem> mReviewList;
    private static final String TAG = "ReviewItemAdapter";
    private Context context;
    private Activity mActivity;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_author;
        ImageView img_cover;
        Button btn_like;
        TextView tv_like;
        View reviewView;

        public ViewHolder(View view) {
            super(view);
            reviewView = view;
            tv_title = view.findViewById(R.id.review_item_title);
            tv_author = view.findViewById(R.id.review_item_author);
            img_cover = view.findViewById(R.id.review_item_cover);
            btn_like = view.findViewById(R.id.btn_review_like);
            tv_like = view.findViewById(R.id.tv_review_like);
        }
    }

    public ReviewItemAdapter(List<ReviewItem> reviewItemList) {
        mReviewList = reviewItemList;
    }

    public ReviewItemAdapter(List<ReviewItem> reviewItemList, Context context, Activity mActivity) {
        this.context = context;
        this.mActivity = mActivity;
        mReviewList = reviewItemList;
    }

    @Override
    public ReviewItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext(); //这里传递进来parent
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        final ReviewItemAdapter.ViewHolder holder = new ReviewItemAdapter.ViewHolder(view);

        holder.reviewView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //item点击事件
                int position = holder.getAdapterPosition();
                ReviewItem reviewItem = mReviewList.get(position);
                Intent intent = new Intent(v.getContext(), ReviewsDetailActivity.class);
                intent.putExtra("articleId", reviewItem.getArticleId());
                intent.putExtra("cover", reviewItem.getCoverImgUrl());
                intent.putExtra("title", reviewItem.getTitle());
                intent.putExtra("author", reviewItem.getAuthor());
                intent.putExtra("likeAmount", reviewItem.getLikeAmount());
                view.getContext().startActivity(intent);
            }
        });

        holder.btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点赞点击事件
                int position = holder.getAdapterPosition();
                final ReviewItem reviewItem = mReviewList.get(position);
                final View view = v;
                //后台相应
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //发送请求
                        try {
                            OkHttpClient client = new OkHttpClient();
                            //获取articleId和userId
                            int articleId = reviewItem.getArticleId();
                            SharedPreferences pref = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
                            int userId = pref.getInt("userid", 0);

                            RequestBody requestBody = new FormBody.Builder()
                                    .add("articleId", articleId + "")
                                    .add("userId", userId + "")
                                    .build();
                            Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/likeQuery").post(requestBody).build();
                            Response response = client.newCall(request).execute();
                            Log.i("test_like_query111111", response.toString());
                            //返回数据

                            if (response.isSuccessful()) {
                                //获取点赞成功与否标志
                                String responseData = response.body().string();
                                boolean sign = handleResponseData(responseData);
                                if (sign) {
                                    mActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            //更改界面
                                            Drawable drawable = context.getResources().getDrawable(R.drawable.btn_review_like_tap);
                                            view.setBackground(drawable);
                                            int newAmount = reviewItem.getLikeAmount() + 1;
                                            holder.tv_like.setText(newAmount + "");
                                        }
                                    });
                                } else {
                                    mActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(mActivity, "点赞失败！", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(mActivity, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                }).start();

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ReviewItemAdapter.ViewHolder holder, int position) {
        ReviewItem reviewItem = mReviewList.get(position);
        holder.tv_author.setText("by " + reviewItem.getAuthor());
        holder.tv_title.setText(reviewItem.getTitle());
        holder.tv_like.setText(reviewItem.getLikeAmount() + "");
        Glide.with(context).load("http://120.24.217.191/Book/img/reviewImg/" + reviewItem.getCoverImgUrl()).into(holder.img_cover);
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }

    //处理返回的函数
    public boolean handleResponseData(final String responseData) {
        try {
            JSONArray jsonArray = new JSONArray(responseData);
            if (jsonArray.length() > 0) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                return jsonObject.getBoolean("sign");
            } else return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}