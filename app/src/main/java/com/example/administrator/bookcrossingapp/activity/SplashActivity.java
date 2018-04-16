package com.example.administrator.bookcrossingapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.administrator.bookcrossingapp.R;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //延迟跳转
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "run: ");
                //用来存储用户基本信息
                SharedPreferences pref = getSharedPreferences("user_info", MODE_PRIVATE);
                int userid = pref.getInt("userid", 0);
                String token = pref.getString("token", "");
                if (userid == 0 || token.equals("")) {
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
