package com.example.administrator.bookcrossingapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.bookcrossingapp.GlideImageLoader;
import com.example.administrator.bookcrossingapp.R;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;

public class ReviewsEditActivity extends AppCompatActivity {

    private EditText edit_title;
    private EditText edit_content;
    private ImageView img_cover;
    private Button btn_write;
    private String titleValue, contentValue;
    private Drawable.ConstantState coverValue;

    private int IMAGE_PICKER = 10010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews_edit);
        initReviewsEdit();

        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setMultiMode(false);
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(720);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(960);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(720);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(960);//保存文件的高度。单位像素


    }


    private void initReviewsEdit() {
        edit_title = findViewById(R.id.reviews_edit_title);
        edit_content = findViewById(R.id.reviews_edit_content);
        img_cover = findViewById(R.id.reviews_edit_cover);
        btn_write = findViewById(R.id.reviews_edit_write);

        img_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取照片
                //Toast.makeText(ReviewsEditActivity.this, "这里用来上传图片", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ReviewsEditActivity.this, ImageGridActivity.class);
                startActivityForResult(intent, IMAGE_PICKER);
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
                if (coverValue.equals(getResources().getDrawable(R.drawable.add_cover).getConstantState()))
                {
                    Toast.makeText(ReviewsEditActivity.this, "请上传封面", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ReviewsEditActivity.this, "上传成功，后台尚未实现", Toast.LENGTH_SHORT).show();
            }

        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                //Log.d(TAG, "onActivityResult: " + images.get(0).path + images.get(0).width);
                Bitmap bm = BitmapFactory.decodeFile(images.get(0).path);
                img_cover.setImageBitmap(bm);
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
