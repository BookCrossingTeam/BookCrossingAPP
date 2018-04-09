package com.example.administrator.bookcrossingapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.bookcrossingapp.R;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText userName;
    private EditText passowrd;
    private ImageView btn_login;
    private String userNameValue, passwordValue;
    private TextView btn_signup;
    private TextView btn_forget;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = (EditText) findViewById(R.id.editText);
        passowrd = (EditText) findViewById(R.id.editText2);
        btn_login = (ImageView) findViewById(R.id.imageButton);

        btn_signup = (TextView) findViewById(R.id.textVIew3);
        btn_forget = (TextView) findViewById(R.id.textView23);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameValue = userName.getText().toString();
                passwordValue = passowrd.getText().toString();

                if (userNameValue.equals("") || passwordValue.equals("")) {
                    Toast.makeText(LoginActivity.this, "请填写用户名和密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //userNameValue = CalcMD5.getMD5(userNameValue);

                            OkHttpClient client = new OkHttpClient();
                            RequestBody requestBody = new FormBody.Builder().add("userName", userNameValue).add("password", passwordValue).build();
                            Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/sign_in").post(requestBody).build();
                            Response response = client.newCall(request).execute();
                            if (!response.isSuccessful()) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return;
                            }
                            String responseData = response.body().string();
                            final JSONObject datajson = new JSONObject(responseData);
                            if (datajson.getInt("statecode") == 200) {
                                SharedPreferences.Editor editor = getSharedPreferences("user_info", MODE_PRIVATE).edit();
                                editor.putString("userid", datajson.getString("userid"));
                                editor.putString("token", datajson.getString("token"));
                                editor.apply();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            } else if (datajson.getInt("statecode") == 100) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else if (datajson.getInt("statecode") == 101) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "用户名或手机号不存在", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        btn_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

    }

}
