package com.example.administrator.bookcrossingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
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

public class WantListActivity extends AppCompatActivity {

    private TextView tv_title;
    private TextView tv_username;
    private int userid;
    private static final String TAG = "WantListActivity";
    private BookDetailAdapter adapter;
    private List<BookDetail> wantlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        tv_title = findViewById(R.id.list_title);
        tv_title.setText("WantList");

        Intent intent = getIntent();
        userid = intent.getIntExtra("userid",0);


        RecyclerView recyclerView = findViewById(R.id.booklist_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(WantListActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BookDetailAdapter(wantlist);
        adapter.setIntent(0);
        recyclerView.setAdapter(adapter);

        //获取书单信息
        initWantList();
    }

    public void initWantList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 获取数据
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("userid", userid+"").build();
                    Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/queryWant").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    Log.i(TAG, "response is :" + response.isSuccessful());
                    if (response.isSuccessful()) {
                        //获取response回来的数据
                        String responseData = response.body().string();
                        handleResponseData(responseData);

                        WantListActivity.this.runOnUiThread((new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        }));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    WantListActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(WantListActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                        }
                    });

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
                book.setRecommendedReason("");
                book.setBookImageUrl(imgUrl);
                book.setPosetime(posetime);
                book.setUserid(userId);
                book.setUserheadpath(userheadpath);
                wantlist.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
