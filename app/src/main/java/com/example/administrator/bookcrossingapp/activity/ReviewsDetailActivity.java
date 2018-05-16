package com.example.administrator.bookcrossingapp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.bookcrossingapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReviewsDetailActivity extends AppCompatActivity {

    private ImageView img_cover;
    private TextView tv_title;
    private TextView tv_author;
    private TextView tv_content;

    private int articleId;
    private String cover;
    private String title;
    private String author;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews_detail);
        Intent intent = getIntent();
        articleId = intent.getIntExtra("articleId",0);
        cover = intent.getStringExtra("cover");
        title = intent.getStringExtra("title");
        author = intent.getStringExtra("author");

        img_cover = findViewById(R.id.reviews_detail_cover);
        tv_title = findViewById(R.id.reviews_detail_title);
        tv_author = findViewById(R.id.reviews_detail_author);
        tv_content = findViewById(R.id.reviews_detail_content);
        //设置内容可以滚动
        tv_content.setMovementMethod(ScrollingMovementMethod.getInstance());

        //利用Glide来将图片加载显示
        Glide.with(this).load("http://120.24.217.191/Book/img/reviewImg/" + cover).into(img_cover);
        tv_title.setText(title);
        tv_author.setText(author);
        initContent();




    }

    public void initContent(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //发送请求
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("articleId", articleId + "")
                            .build();
                    Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/detailContent").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    Log.i("contentTest",response.toString());
                    if (response.isSuccessful()) {

                        String responseData = response.body().string();
                        JSONArray jsonArray = new JSONArray(responseData);
                        if(jsonArray.length() > 0){
                            //因为只有唯一一个返回值
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            content = jsonObject.getString("content");
                        }
                        ReviewsDetailActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Toast.makeText(ReviewsDetailActivity.this,"成功连接上详情页面",Toast.LENGTH_SHORT).show();
                            }
                        });

                        ReviewsDetailActivity.this.runOnUiThread((new Runnable() {
                            @Override
                            public void run() {
                                tv_content.setText(content);
                            }
                        }));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(ReviewsDetailActivity.this).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ReviewsDetailActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        }).start();
    }


}
