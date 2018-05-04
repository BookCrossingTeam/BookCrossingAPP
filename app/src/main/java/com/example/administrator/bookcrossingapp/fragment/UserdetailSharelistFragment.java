package com.example.administrator.bookcrossingapp.fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.adapter.BookDetailAdapter;
import com.example.administrator.bookcrossingapp.datamodel.BookDetail;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * @author Lyh
 */
public class UserdetailSharelistFragment extends Fragment {

    private static final String TAG = "UserdetailSharelistFrag";

    private View view;
    private BookDetailAdapter adapter;
    private List<BookDetail> sharelist = new ArrayList<>();
    private LinearLayout list_title_layout;
    private ProgressBar progressBar;
    private int userid;

    public UserdetailSharelistFragment(int id) {
        super();
        userid = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.w(TAG, "onCreateView: ");
        view = inflater.inflate(R.layout.activity_list, container, false);
        list_title_layout = view.findViewById(R.id.list_title_layout);
        list_title_layout.setVisibility(View.GONE);//隐藏标题，为了代码复用
        progressBar = view.findViewById(R.id.bookLoading);
        if (!sharelist.isEmpty())
            progressBar.setVisibility(View.GONE);
        initShareListRecyclerView();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.w(TAG, "setUserVisibleHint: true");
            if (sharelist.isEmpty()) {
                initShareList();
            }
        }
    }

    private void initShareListRecyclerView() {
        RecyclerView recyclerView = view.findViewById(R.id.booklist_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BookDetailAdapter(sharelist);
        recyclerView.setAdapter(adapter);
    }

    private void initShareList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 获取数据
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("userid", userid + "").build();
                    Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/queryGet").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {

                        String responseData = response.body().string();
                        handleResponseData(responseData);

                        getActivity().runOnUiThread((new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            }
                        }));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "服务器开小差啦", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        }).start();
    }

    public void handleResponseData(final String responseData) {
        try {
            JSONArray jsonArray = new JSONArray(responseData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String bookName = jsonObject.getString("bookName");
                String author = jsonObject.getString("author");
                String press = jsonObject.getString("publish");
                String recommendedReason = jsonObject.getString("reason");
                String imgUrl = jsonObject.getString("imgUrl");
                long posetime = Long.parseLong(jsonObject.getString("poseTime"));
                int userId = Integer.parseInt(jsonObject.getString("userId"));
                String username = jsonObject.getString("username");
                int bookId = Integer.parseInt(jsonObject.getString("id"));
                int bookType = Integer.parseInt(jsonObject.getString("bookType"));
                String userheadpath = jsonObject.getString("headImgPath");
                int exchangeState = Integer.parseInt(jsonObject.getString("exchangeState"));

                BookDetail book = new BookDetail();
                book.setBookid(bookId);
                book.setExchangeState(exchangeState);
                book.setUsername(username);
                book.setBookName(bookName);
                book.setAuthor(author);
                book.setPress(press);
                book.setRecommendedReason(recommendedReason);
                book.setBookImageUrl(imgUrl);
                book.setPosetime(posetime);
                book.setUserid(userId);
                book.setUserheadpath(userheadpath);
                book.setBookType(bookType);
                sharelist.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
