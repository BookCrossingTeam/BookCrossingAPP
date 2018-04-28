package com.example.administrator.bookcrossingapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.bookcrossingapp.datamodel.BookDetail;
import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.adapter.BookDetailAdapter;

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

public class WantListActivity extends AppCompatActivity {

    private TextView tv_title;
    private TextView tv_username;
    private String username;
    private static final String TAG = "WantListActivity";
    private BookDetailAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    private List<BookDetail> wantlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        tv_title = findViewById(R.id.list_title);
        //tv_username = findViewById(R.id.list_username);
        tv_title.setText("WantList");
        //获取书单信息
        initWantList();
//        initData();
//        RecyclerView recyclerView = findViewById(R.id.booklist_recyclerView);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(WantListActivity.this);
//        recyclerView.setLayoutManager(layoutManager);
//        adapter = new BookDetailAdapter(wantlist);
//        recyclerView.setAdapter(adapter);

    }


    private void initData() {
        for (int i = 0; i < 3; i++) {
            BookDetail a = new BookDetail("Yvettemuki", "《The Great Gatsby》", "下拉刷新页面", "", "");
            a.setPosetime(00000);
            SharedPreferences sp =  getSharedPreferences("user_info",MODE_PRIVATE);
            int userId = sp.getInt("userid",0);
            a.setUserid(userId);
            wantlist.add(a);
        }
    }

    public void initWantList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 获取数据
                try {
                    OkHttpClient client = new OkHttpClient();
                    client.retryOnConnectionFailure();
                    SharedPreferences sp =  getSharedPreferences("user_info",MODE_PRIVATE);
                    int id = sp.getInt("userid", 0);
                    String userid = Integer.toString(id);
                    RequestBody requestBody = new FormBody.Builder().add("userid", userid).build();
                    Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/queryWant").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    Log.i(TAG, "response is :"+response.isSuccessful());
                    if (response.isSuccessful()) {
                        WantListActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(WantListActivity.this,"刷新成功！！！！！", Toast.LENGTH_SHORT).show();
                            }}
                        );
                        //获取response回来的数据
                        String responseData = response.body().string();
                        handleResponseData(responseData);

                        WantListActivity.this.runOnUiThread((new Runnable() {
                            @Override
                            public void run() {
                                //获取页面
                                RecyclerView recyclerView = findViewById(R.id.booklist_recyclerView);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(WantListActivity.this);
                                recyclerView.setLayoutManager(layoutManager);
                                adapter = new BookDetailAdapter(wantlist);
                                recyclerView.setAdapter(adapter);
//                                adapter.notifyDataSetChanged();
//                                swipeRefresh.setRefreshing(false);
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
                SharedPreferences sp = getSharedPreferences("user_info",MODE_PRIVATE);
                String username = sp.getString("username","");
//                BookDetail a = new BookDetail(username, bookName, author, press, recommendedReason);
                System.out.println("test2222222222222222");
                BookDetail a = new BookDetail(username, bookName, author, press, "" ,imgUrl);
                a.setPosetime(posetime);
                a.setUserid(userId);
//                System.out.println(TAG+": "+username);
                System.out.println(TAG+": "+bookName);
//                System.out.println(TAG+": "+author);
//                System.out.println(TAG+": "+press);
//                System.out.println(TAG+": "+recommendedReason);
//                System.out.println(TAG+": "+imgUrl);
                System.out.println(a.toString());
                wantlist.add(a);

//                BookDetailDB db = new BookDetailDB();
//                db.setUsername(username);
//                db.setBookName(bookName);
//                db.setAuthor(author);
//                db.setPress(press);
//                db.setRecommendedReason(recommendedReason);
//                db.setBookImageUrl(imgUrl);
//                db.setPosetime(posetime);
//                db.setUserid(userId);
//                db.saveThrows();
            }
//            BookDetailList.clear();
//            offset = 0;
//            initBookDetailData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
