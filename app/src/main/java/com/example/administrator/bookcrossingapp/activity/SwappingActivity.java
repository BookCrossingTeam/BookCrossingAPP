package com.example.administrator.bookcrossingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.adapter.SwappingAdapter;
import com.example.administrator.bookcrossingapp.datamodel.Swapping;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SwappingActivity extends AppCompatActivity {

    private List<Swapping> swappinglist = new ArrayList<>();
    private SwappingAdapter adapter;
    private int userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swapping);
        RecyclerView recyclerView = findViewById(R.id.swapping_list_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SwappingAdapter(swappinglist,SwappingActivity.this);
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        userid = intent.getIntExtra("userid", 0);

        initData();
    }

    public void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 获取数据
                try {
                    swappinglist.clear();
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("userid", userid + "").build();
                    Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/exchange_query").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {

                        String responseData = response.body().string();
                        JSONArray jsonArray = new JSONArray(responseData);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            //exchangeid, userAid,bookAName,bookAURL, bookBName, bookBURL,exchangeTime,exchangeState
                            int exchangeid = Integer.parseInt(jsonObject.getString("exchangeid"));

                            int userAid = Integer.parseInt(jsonObject.getString("userAid"));
                            int userBid = Integer.parseInt(jsonObject.getString("userBid"));
                            String userAname = jsonObject.getString("userAname");
                            String userBname = jsonObject.getString("userBname");

                            String bookAURL = jsonObject.getString("bookAURL");
                            String bookBURL = jsonObject.getString("bookBURL");
                            String bookAName = jsonObject.getString("bookAName");
                            String bookBName = jsonObject.getString("bookBName");
                            long exchangeTime = Long.parseLong(jsonObject.getString("exchangeTime"));
                            int exchangeState = Integer.parseInt(jsonObject.getString("exchangeState"));

                            int bookAid = Integer.parseInt(jsonObject.getString("bookAid"));
                            int bookBid = Integer.parseInt(jsonObject.getString("bookBid"));

                            Swapping swapping = new Swapping();
                            swapping.setExchangeid(exchangeid);
                            swapping.setExchangeState(exchangeState);
                            swapping.setSwappingBookURL1(bookAURL);

                            swapping.setBookAid(bookAid);
                            swapping.setBookBid(bookBid);

                            swapping.setSwappingBookURL2(bookBURL);
                            if(userAid == userid)
                            {
                                swapping.setMyuserid(userAid);
                                swapping.setTouserid(userBid);
                                swapping.setMyusername(userAname);
                                swapping.setTousername(userBname);
                                swapping.setSwappingBookname1(bookAName+"(*)");
                                swapping.setSwappingBookname2(bookBName);
                                if(exchangeState==0)
                                {
                                    swapping.setNow_state(1);
                                    swapping.setConfirm(false);
                                }
                                if(exchangeState==1)
                                {
                                    swapping.setNow_state(2);
                                    swapping.setConfirm(true);
                                }
                                if(exchangeState==2)
                                {
                                    swapping.setNow_state(3);
                                    swapping.setConfirm(false);
                                }
                                if(exchangeState==3)
                                {
                                    swapping.setNow_state(4);
                                    swapping.setConfirm(true);
                                }
                                if(exchangeState==4)
                                {
                                    swapping.setNow_state(5);
                                    swapping.setConfirm(false);
                                }
                                if(exchangeState==5)
                                {
                                    swapping.setNow_state(6);
                                    swapping.setConfirm(true);
                                }
                            }
                            else
                            {
                                swapping.setMyuserid(userBid);
                                swapping.setTouserid(userAid);
                                swapping.setMyusername(userBname);
                                swapping.setTousername(userAname);
                                swapping.setSwappingBookname1(bookAName);
                                swapping.setSwappingBookname2(bookBName+"(*)");
                                if(exchangeState==0)
                                {
                                    swapping.setNow_state(7);
                                    swapping.setConfirm(true);
                                }
                                if(exchangeState==1)
                                {
                                    swapping.setNow_state(8);
                                    swapping.setConfirm(false);
                                }
                                if(exchangeState==2)
                                {
                                    swapping.setNow_state(9);
                                    swapping.setConfirm(true);
                                }
                                if(exchangeState==3)
                                {
                                    swapping.setNow_state(10);
                                    swapping.setConfirm(true);
                                }
                                if(exchangeState==4)
                                {
                                    swapping.setNow_state(11);
                                    swapping.setConfirm(true);
                                }
                                if(exchangeState==5)
                                {
                                    swapping.setNow_state(12);
                                    swapping.setConfirm(true);
                                }
                            }

                            swappinglist.add(swapping);
                        }

                        runOnUiThread((new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                findViewById(R.id.swapping_loading).setVisibility(View.GONE);
                            }
                        }));
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SwappingActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        }).start();
    }
}
