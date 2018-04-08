package com.example.admnistrator.bookcrossingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ReviewsDetailActivity extends AppCompatActivity {

    private ImageView img_cover;
    private TextView tv_title;
    private TextView tv_author;
    private TextView tv_content;

    private int cover;
    private String title;
    private String author;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews_detail);
        Intent intent = getIntent();
        cover = intent.getIntExtra("cover", R.drawable.ouc_background);
        title = intent.getStringExtra("title");
        author = intent.getStringExtra("author");

        img_cover = findViewById(R.id.reviews_detail_cover);
        tv_title = findViewById(R.id.reviews_detail_title);
        tv_author = findViewById(R.id.reviews_detail_author);
        tv_content = findViewById(R.id.reviews_detail_content);

        img_cover.setImageResource(cover);
        tv_title.setText(title);
        tv_author.setText(author);
        tv_content.setText("内容无");


    }
}
