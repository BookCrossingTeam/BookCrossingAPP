package com.example.administrator.bookcrossingapp.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.bookcrossingapp.CalcMD5;
import com.example.administrator.bookcrossingapp.R;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdatePasswordActivity extends AppCompatActivity {
    private EditText editUsername;
    private EditText editNewPassword;
    private EditText editOldPassword;
    private ImageView imageUpdate;
    private String username, newpassword, oldpassword;
    private int userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        //初始化控件
        editUsername = (EditText) findViewById(R.id.edit_username_update);
        editNewPassword = (EditText) findViewById(R.id.edit_newPassword_update);
        editOldPassword = (EditText) findViewById(R.id.edit_oldPassord_update);
        imageUpdate = (ImageView) findViewById(R.id.image_updatePassword_summit);


        //editUsername不可点击不可编辑，系统将用户名传值给editUsername
        SharedPreferences pref = this.getSharedPreferences("user_info", MODE_PRIVATE);
        username = pref.getString("username", "");
        userid = pref.getInt("userid", 0);
        editUsername.setText(username);


        //提交修改
        imageUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editUsername.getText().toString();
                newpassword = editNewPassword.getText().toString();
                oldpassword = editOldPassword.getText().toString();

                if (username.equals("") || newpassword.equals("") || oldpassword.equals("")) {
                    Toast.makeText(UpdatePasswordActivity.this, "填写完整", Toast.LENGTH_SHORT).show();
                    return;
                }

                sent_info();
            }
        });
    }


    ////////待实现
    //后台更新密码，同忘记密码界面
    //向服务器发送请求修改密码
    public void sent_info() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    newpassword = CalcMD5.getMD5(newpassword);
                    oldpassword = CalcMD5.getMD5(oldpassword);

                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("userid", userid + "").add("newpassword", newpassword).add("oldpassword", oldpassword).build();
                    Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/passwordUpdate").post(requestBody).build();
                    Response response = client.newCall(request).execute();

                    final String responseData = response.body().string();
                    if (responseData.equals("ok")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(UpdatePasswordActivity.this, "密码重置成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    } else if (!responseData.equals("")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(UpdatePasswordActivity.this, responseData, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(UpdatePasswordActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(UpdatePasswordActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

}
