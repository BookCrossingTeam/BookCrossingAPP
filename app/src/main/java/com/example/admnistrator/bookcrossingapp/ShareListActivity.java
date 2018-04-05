package com.example.admnistrator.bookcrossingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ShareListActivity extends AppCompatActivity {

    private TextView tv_title;
    private TextView tv_username;
    private String username;

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


    }
}
