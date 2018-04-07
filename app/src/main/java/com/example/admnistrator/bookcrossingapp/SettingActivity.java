package com.example.admnistrator.bookcrossingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SettingActivity extends AppCompatActivity {

    private Button btnAccount;
    private Button btnFeedback;
    private Button btnSoftware;
    private Button btnHelp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        btnAccount = (Button) findViewById(R.id.btn_setting_account);
        btnFeedback = (Button) findViewById(R.id.btn_setting_feedback);
        btnSoftware = (Button) findViewById(R.id.btn_setting_software);
        btnHelp = (Button) findViewById(R.id.btn_setting_help);
        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, SettingAcountActivity.class);
                startActivity(intent);
            }
        });
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, UserDetailActivity.class);
                startActivity(intent);
            }
        });
    }





}
