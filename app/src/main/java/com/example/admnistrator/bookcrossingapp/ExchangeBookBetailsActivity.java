package com.example.admnistrator.bookcrossingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ExchangeBookBetailsActivity extends AppCompatActivity {

    private static final String TAG = "ExchangeBookBetailsActi";

    private String usernameValue;
    private String bookNameValue;
    private String authorValue;
    private String pressValue;
    private String recommendedReasonValue;

    private TextView username, bookName, author, press, recommendedReason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_book_betails);

        Intent intent = getIntent();
        usernameValue = intent.getStringExtra("Username");
        bookNameValue = intent.getStringExtra("BookName");
        authorValue = intent.getStringExtra("Author");
        pressValue = intent.getStringExtra("Press");
        recommendedReasonValue = intent.getStringExtra("RecommendedReason");

        username = (TextView) findViewById(R.id.textView12);
        bookName = (TextView) findViewById(R.id.textView22);
        author = (TextView) findViewById(R.id.textView20);
        press = (TextView) findViewById(R.id.textView18);
        recommendedReason = (TextView) findViewById(R.id.textView16);

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
    }
}
