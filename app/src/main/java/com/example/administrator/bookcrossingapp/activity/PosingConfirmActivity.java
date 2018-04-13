package com.example.administrator.bookcrossingapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.datamodel.DoubanIsbn;
import com.google.gson.Gson;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PosingConfirmActivity extends AppCompatActivity {

    private static final String TAG = "PosingConfirmActivity";

    private EditText bookName;
    private EditText author;
    private EditText press;
    private EditText recommendedReason;
    private Spinner classify;
    private ImageView pose_btn;
    private ImageView bookImg;
    private String bookNameValue, authorValue, pressValue, recommendedReasonValue, classifyValue;
    private String userid;
    private String bookImgAbsolutePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posing_share);
        SharedPreferences pref = this.getSharedPreferences("user_info", MODE_PRIVATE);
        userid = pref.getString("userid", "");
        init();//初始化组件

    }

    private void init() {
        bookName = (EditText) findViewById(R.id.edit_posing_shared_name);
        author = (EditText) findViewById(R.id.edit_posing_shared_author);
        press = (EditText) findViewById(R.id.edit_posing_shared_press);
        recommendedReason = (EditText) findViewById(R.id.edit_posing_shared_recommend);
        pose_btn = (ImageView) findViewById(R.id.btn_posing_shared);
        bookImg = (ImageView) findViewById(R.id.img_posing_shared_pic);
        classify = findViewById(R.id.spinner_posing_shared);
        //不可编辑，通过扫码传值进来
        //bookName.setFocusable(false);
        //bookName.setFocusableInTouchMode(false);
        //author.setFocusable(false);
        //author.setFocusableInTouchMode(false);
        //press.setFocusable(false);
        //press.setFocusableInTouchMode(false);
        //传值
        Intent intent = getIntent();
        final String content = intent.getStringExtra("code_content");
        recommendedReason.setText(content);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ;// do somethings
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("https://api.douban.com/v2/book/isbn/" + content).build();
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PosingConfirmActivity.this, "该书本未收录", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                        return;
                    }
                    String responseData = response.body().string();
                    Log.d(TAG, "run: " + responseData);
                    final DoubanIsbn resultJson = new Gson().fromJson(responseData, DoubanIsbn.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (resultJson.getSubtitle().equals(""))
                                bookName.setText(resultJson.getTitle());
                            else
                                bookName.setText(resultJson.getSubtitle());
                            if (!resultJson.getAuthor().isEmpty())
                                author.setText(resultJson.getAuthor().get(0));
                            press.setText(resultJson.getPublisher());
                            Glide.with(PosingConfirmActivity.this).load(resultJson.getImage()).into(bookImg);
                        }
                    });
                    bookImgAbsolutePath = Glide.with(PosingConfirmActivity.this).load(resultJson.getImage()).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get().getAbsolutePath();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        pose_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookNameValue = bookName.getText().toString();
                authorValue = author.getText().toString();
                pressValue = press.getText().toString();
                recommendedReasonValue = recommendedReason.getText().toString();
                classifyValue = classify.getSelectedItem().toString();
                if (bookNameValue.equals("") || authorValue.equals("") || pressValue.equals("") || recommendedReasonValue.equals("")) {
                    Toast.makeText(PosingConfirmActivity.this, "请填写完整", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (classifyValue.equals("请选择分类")) {
                    Toast.makeText(PosingConfirmActivity.this, "请选择分类", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendPose();
            }
        });
    }

    public void sendPose() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File file = new File(bookImgAbsolutePath);
                    OkHttpClient client = new OkHttpClient();
                    RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
                    RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", "book_image.jpg", fileBody)
                            .addFormDataPart("userid", userid).addFormDataPart("bookName", bookNameValue)
                            .addFormDataPart("author", authorValue).addFormDataPart("press", pressValue).addFormDataPart("recommendedReason", recommendedReasonValue).build();
                    Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/sendPose").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (responseData.equals("true")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PosingConfirmActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    } else
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PosingConfirmActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                            }
                        });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PosingConfirmActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

}
