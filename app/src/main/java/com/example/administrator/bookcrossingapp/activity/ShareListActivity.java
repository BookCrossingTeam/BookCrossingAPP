package com.example.administrator.bookcrossingapp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.adapter.BookDetailAdapter;
import com.example.administrator.bookcrossingapp.datamodel.BookDetail;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ShareListActivity extends AppCompatActivity {

    private TextView tv_title;
    private TextView tv_username;
    private int userid;
    private BookDetailAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    private static final String TAG = "ShareListActivity";
    private List<BookDetail> sharelist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        tv_title = findViewById(R.id.list_title);
        tv_title.setText("ShareList");
        int flag;

        Intent intent = getIntent();
        userid = intent.getIntExtra("userid", 0);
        flag = intent.getIntExtra("flag", 0);

        RecyclerView recyclerView = findViewById(R.id.booklist_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ShareListActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        if (flag == 0)
            adapter = new BookDetailAdapter(sharelist);
        if (flag == 1)
        {
            adapter = new BookDetailAdapter(sharelist, ShareListActivity.this);
            adapter.setIntent(2);
        }
        recyclerView.setAdapter(adapter);


        //获取书单信息
        initShareList();
    }


    public void initShareList() {
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

                        ShareListActivity.this.runOnUiThread((new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        }));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(ShareListActivity.this).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ShareListActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
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
                String userheadpath = jsonObject.getString("headImgPath");

                BookDetail book = new BookDetail();
                book.setBookid(bookId);
                book.setUsername(username);
                book.setBookName(bookName);
                book.setAuthor(author);
                book.setPress(press);
                book.setRecommendedReason(recommendedReason);
                book.setBookImageUrl(imgUrl);
                book.setPosetime(posetime);
                book.setUserid(userId);
                book.setUserheadpath(userheadpath);
                sharelist.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
