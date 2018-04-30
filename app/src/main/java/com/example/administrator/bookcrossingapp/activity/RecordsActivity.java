package com.example.administrator.bookcrossingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.adapter.RecordsAdapter;
import com.example.administrator.bookcrossingapp.datamodel.Records;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RecordsActivity extends AppCompatActivity {

    private List<Records> recordslist = new ArrayList<>();
    private RecordsAdapter adapter;
    private int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        RecyclerView recyclerView = findViewById(R.id.records_list_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecordsAdapter(recordslist);
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        userid = intent.getIntExtra("userid", 0);

        initData();
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 获取数据
                try {
                    recordslist.clear();
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("userid", userid + "").build();
                    Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/exchange_record_query").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {

                        String responseData = response.body().string();
                        JSONArray jsonArray = new JSONArray(responseData);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            //SELECT e.userAid,u1.username as userAname,e.userBid,u2.username as userBname,a.bookName as bookAName,a.imgUrl as bookAURL,b.bookName as bookBName,b.imgUrl as bookBURL,e.exchangeTime,e.startTime

                            int userAid = Integer.parseInt(jsonObject.getString("userAid"));
                            int userBid = Integer.parseInt(jsonObject.getString("userBid"));
                            String userAname = jsonObject.getString("userAname");
                            String userBname = jsonObject.getString("userBname");

                            String bookAURL = jsonObject.getString("bookAURL");
                            String bookBURL = jsonObject.getString("bookBURL");
                            String bookAName = jsonObject.getString("bookAName");
                            String bookBName = jsonObject.getString("bookBName");
                            long exchangeTime = Long.parseLong(jsonObject.getString("exchangeTime"));
                            long startTime = Long.parseLong(jsonObject.getString("startTime"));

                            Records records = new Records();
                            if (userAid == userid) {
                                records.setRecordsBookname(bookBName);
                                records.setRecordsBooknameURL(bookBURL);
                                records.setRecordsExchangeUser(userBname);
                                records.setRecordsTime(exchangeTime);
                                records.setStartTime(startTime);
                            } else {
                                records.setRecordsBookname(bookAName);
                                records.setRecordsBooknameURL(bookAURL);
                                records.setRecordsExchangeUser(userAname);
                                records.setRecordsTime(exchangeTime);
                                records.setStartTime(startTime);
                            }
                            recordslist.add(records);
                        }

                        runOnUiThread((new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        }));
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RecordsActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        }).start();
    }
}
