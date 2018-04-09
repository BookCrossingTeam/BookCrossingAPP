package com.example.administrator.bookcrossingapp.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.bookcrossingapp.R;

public class PosingWantingActivity extends AppCompatActivity {

    private EditText bookName;
    private EditText author;
    private EditText press;
    private ImageView pose_btn;
    private String bookNameValue, authorValue, pressValue;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posing_wanting);
        SharedPreferences pref = this.getSharedPreferences("my_user_info", MODE_PRIVATE);
        username = pref.getString("username", "");
        init();//初始化组件

    }

    private void init() {
        bookName = (EditText) findViewById(R.id.edit_posing_wanting_name);
        author = (EditText) findViewById(R.id.edit_posing_wanting_author);
        press = (EditText) findViewById(R.id.edit_posing_wanting_press);
        pose_btn = (ImageView) findViewById(R.id.btn_posing_wanting);

        pose_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookNameValue = bookName.getText().toString();
                authorValue = author.getText().toString();
                pressValue = press.getText().toString();

                if (bookNameValue.equals("") || authorValue.equals("") || pressValue.equals("")) {
                    Toast.makeText(PosingWantingActivity.this, "请填写完整", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendPose();
            }
        });
    }

    ///后台数据库，已删除推荐
    public void sendPose() {
        Toast.makeText(PosingWantingActivity.this, "该功能尚未实现", Toast.LENGTH_SHORT).show();
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("username", username).add("bookName", bookNameValue).add("author", authorValue).add("press", pressValue).build();
                    Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/sendPose").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (responseData.equals("true")) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "发表成功", Toast.LENGTH_SHORT).show();
                                bookName.setText("");
                                author.setText("");
                                press.setText("");
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        */
    }

}
