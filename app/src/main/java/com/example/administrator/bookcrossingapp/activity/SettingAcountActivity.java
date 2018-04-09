package com.example.administrator.bookcrossingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.bookcrossingapp.R;

public class SettingAcountActivity extends AppCompatActivity {

    private Button btnIcon;
    private Button btnPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_acount);
        btnIcon = findViewById(R.id.btn_update_icon);
        btnPassword = findViewById(R.id.btn_update_password);
        btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingAcountActivity.this,UpdatePasswordActivity.class);
                startActivity(intent);
            }
        });

    }
}
