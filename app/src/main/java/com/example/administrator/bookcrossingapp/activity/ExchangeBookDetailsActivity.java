package com.example.administrator.bookcrossingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.bookcrossingapp.R;

public class ExchangeBookDetailsActivity extends AppCompatActivity {

    private static final String TAG = "ExchangeBookBetailsActi";

    private TextView tv_sharelist;
    private TextView tv_wantlist;
    private TextView tv_chat;
    private TextView tv_want;
    private ImageView img_icon;
    private TextView username, bookName, author, press, recommendedReason;
    private ImageView bookImg;
    private ImageView headImg;

    private String usernameValue;
    private String bookNameValue;
    private String authorValue;
    private String pressValue;
    private String classifyValue;
    private String recommendedReasonValue;
    private String bookImgURL;
    private String headImgURL;
    private int userid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_book_details);

        //从上一个页面传过来的值
        Intent intent = getIntent();
        usernameValue = intent.getStringExtra("Username");
        bookNameValue = intent.getStringExtra("BookName");
        authorValue = intent.getStringExtra("Author");
        pressValue = intent.getStringExtra("Press");
        recommendedReasonValue = intent.getStringExtra("RecommendedReason");
        bookImgURL = intent.getStringExtra("BookImageUrl");
        userid = intent.getIntExtra("userid",0);
        headImgURL = intent.getStringExtra("nameheadUrl");

        username = (TextView) findViewById(R.id.tv_username);
        bookName = (TextView) findViewById(R.id.tv_bookname);
        author = (TextView) findViewById(R.id.tv_author);
        press = (TextView) findViewById(R.id.tv_press);
        recommendedReason = (TextView) findViewById(R.id.tv_recommend);
        bookImg = (ImageView) findViewById(R.id.img_pic);
        headImg = (ImageView) findViewById(R.id.img_icon);
        tv_sharelist = findViewById(R.id.tv_sharelist);
        tv_wantlist = findViewById(R.id.tv_wantlist);
        tv_chat = findViewById(R.id.tv_bookdetail_chat);
        tv_want = findViewById(R.id.tv_bookdetail_want);
        img_icon = findViewById(R.id.img_icon);

        Log.d(TAG, "onClick: " + usernameValue);
        Log.d(TAG, "onClick: " + bookNameValue);
        Log.d(TAG, "onClick: " + authorValue);
        Log.d(TAG, "onClick: " + pressValue);
        Log.d(TAG, "onClick: " + recommendedReasonValue);
        username.setText(usernameValue);
        bookName.setText(bookNameValue);
        author.setText(authorValue);
        press.setText(pressValue);
        recommendedReason.setText(recommendedReasonValue);
        Glide.with(ExchangeBookDetailsActivity.this).load("http://120.24.217.191/Book/img/bookImg/"+bookImgURL).into(bookImg);
        Glide.with(ExchangeBookDetailsActivity.this).load("http://120.24.217.191/Book/img/headImg/" + headImgURL).error(R.drawable.icon).into(headImg);

        tv_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExchangeBookDetailsActivity.this, FriendChatActivity.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
            }
        });

        tv_sharelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExchangeBookDetailsActivity.this, ShareListActivity.class);
                intent.putExtra("username",usernameValue);
                startActivity(intent);
            }
        });

        tv_wantlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExchangeBookDetailsActivity.this, WantListActivity.class);
                intent.putExtra("username",usernameValue);
                startActivity(intent);
            }
        });

        img_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExchangeBookDetailsActivity.this, UserDetailActivity.class);
                intent.putExtra("username",usernameValue);
                startActivity(intent);
            }
        });
    }
}
