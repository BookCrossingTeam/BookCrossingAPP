package com.example.administrator.bookcrossingapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.bookcrossingapp.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        toNextPage();
    }

    public void toNextPage() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences pref = getSharedPreferences("my_user_info", MODE_PRIVATE);
                String username = pref.getString("username", "");
                String pass = pref.getString("pass", "");
                if (username.equals("") && pass.equals("")) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1500);  //时间控制
    }


}
