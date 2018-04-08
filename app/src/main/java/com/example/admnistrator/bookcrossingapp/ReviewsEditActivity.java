package com.example.admnistrator.bookcrossingapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ReviewsEditActivity extends AppCompatActivity {

    private EditText edit_title;
    private EditText edit_content;
    private ImageView img_cover;
    private Button btn_write;
    private String titleValue, contentValue;
    private Drawable.ConstantState coverValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews_edit);
        initReviewsEdit();


    }

    private void initReviewsEdit() {
        edit_title = findViewById(R.id.reviews_edit_title);
        edit_content = findViewById(R.id.reviews_edit_content);
        img_cover = findViewById(R.id.reviews_edit_cover);
        btn_write = findViewById(R.id.reviews_edit_write);
        img_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ReviewsEditActivity.this, "这里用来上传图片", Toast.LENGTH_SHORT).show();
            }
        });
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleValue = edit_title.getText().toString();
                contentValue = edit_content.getText().toString();
                coverValue = img_cover.getDrawable().getConstantState();
                if (titleValue.equals("")) {
                    Toast.makeText(ReviewsEditActivity.this, "请填写标题", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (contentValue.equals("")) {
                    Toast.makeText(ReviewsEditActivity.this, "请填写内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (coverValue.equals(getResources().getDrawable(R.drawable.plus_sign).getConstantState()))
                {
                    Toast.makeText(ReviewsEditActivity.this, "请上传封面", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ReviewsEditActivity.this, "上传成功，后台尚未实现", Toast.LENGTH_SHORT).show();
            }

        });


    }
}
