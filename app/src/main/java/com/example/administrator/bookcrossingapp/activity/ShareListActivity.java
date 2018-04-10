package com.example.administrator.bookcrossingapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.administrator.bookcrossingapp.datamodel.BookDetail;
import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.adapter.BookDetailAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShareListActivity extends AppCompatActivity {

    private TextView tv_title;
    private TextView tv_username;
    //private String username;

    private List<BookDetail> sharelist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        tv_title = findViewById(R.id.list_title);
        //tv_username = findViewById(R.id.list_username);
        SharedPreferences userInfo = getSharedPreferences("user_info", MODE_PRIVATE);
        String userId = userInfo.getString("userid","");
        tv_title.setText("ShareList");
//        tv_username.setText("of XXXX");

//        Intent  intent = getIntent();
//        username = intent.getStringExtra("username");
//        tv_username.setText(username);

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
