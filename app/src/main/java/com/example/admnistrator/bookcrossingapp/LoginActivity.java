package com.example.admnistrator.bookcrossingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText userName;
    private EditText passowrd;
    private ImageView btn_login;
    private String userNameValue, passwordValue;
    private TextView btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = (EditText) findViewById(R.id.editText);
        passowrd = (EditText) findViewById(R.id.editText2);
        btn_login = (ImageView) findViewById(R.id.imageButton);
        btn_signup = (TextView) findViewById(R.id.textVIew3);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameValue = userName.getText().toString();
                passwordValue = passowrd.getText().toString();
                if(userNameValue.equals("") || userNameValue.equals("") )
                {
                    Toast.makeText(LoginActivity.this,"请填写用户名和密码",Toast.LENGTH_SHORT).show();
                    return ;
                }
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }
}
