package com.example.admnistrator.bookcrossingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ShareListActivity extends AppCompatActivity {

    private TextView tv_title;
    private TextView tv_username;
    private String username;

    private List<BookDetail> sharelist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        tv_title = findViewById(R.id.list_title);
        tv_username = findViewById(R.id.list_username);
        tv_title.setText("Share List");

        Intent  intent = getIntent();
        username = intent.getStringExtra("username");
        tv_username.setText(username);

        initData();
        RecyclerView recyclerView = findViewById(R.id.booklist_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        BookDetailAdapter adapter = new BookDetailAdapter(sharelist);
        recyclerView.setAdapter(adapter);

    }

    private void initData() {
        for (int i = 0; i < 3; i++) {
            BookDetail a = new BookDetail("Yvettemuki", "《The Great Gatsby》", "下拉刷新页面", "", "");
            sharelist.add(a);
        }
    }
}
