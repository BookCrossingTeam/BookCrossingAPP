package com.example.admnistrator.bookcrossingapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import okhttp3.OkHttpClient;

public class UpdatePasswordActivity extends AppCompatActivity {
    private EditText editUsername;
    private EditText editNewPassword;
    private EditText editConfirmPassword;
    private ImageView imageUpdate;
    private String username, newpassword, confirmpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        //初始化控件
        editUsername = (EditText) findViewById(R.id.edit_username_update);
        editNewPassword = (EditText) findViewById(R.id.edit_newPassword_update);
        editConfirmPassword = (EditText) findViewById(R.id.edit_confirmPassord_update);
        imageUpdate = (ImageView) findViewById(R.id.image_updatePassword_summit);


        //editUsername不可点击不可编辑，系统将用户名传值给editUsername
        SharedPreferences pref = this.getSharedPreferences("my_user_info", MODE_PRIVATE);
        username = pref.getString("username", "");
        editUsername.setText(username);



        //提交修改
        imageUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editUsername.getText().toString();
                newpassword = editNewPassword.getText().toString();
                confirmpassword = editConfirmPassword.getText().toString();
                if (username.equals("")) {
                    Toast.makeText(UpdatePasswordActivity.this, "用户名获取失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (newpassword.equals(confirmpassword) == false) {
                    Toast.makeText(UpdatePasswordActivity.this, "请两次密码输入一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                sent_info();
                //感觉需要一个返回的判断？
                Toast.makeText(UpdatePasswordActivity.this, "密码重置成功", Toast.LENGTH_SHORT).show();
                finish();
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
                    OkHttpClient client = new OkHttpClient();
                    /*
                    待实现，将添加改成更新
                    RequestBody requestBody = new FormBody.Builder().add("telephone", telephoneValue).add("password", passwordValue1).build();
                    Request request = new Request.Builder().url("http://120.24.217.191/sign_up.php").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    */
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
