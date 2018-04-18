package com.example.administrator.bookcrossingapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.bookcrossingapp.GlideImageLoader;
import com.example.administrator.bookcrossingapp.R;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SettingAcountActivity extends AppCompatActivity {

    private static final String TAG = "SettingAcountActivity";

    private Button btnIcon;
    private Button btnPassword;
    private ImageView headImg;

    private int userid;
    private  int IMAGE_PICKER = 10010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_acount);
        SharedPreferences pref = this.getSharedPreferences("user_info", MODE_PRIVATE);
        userid = pref.getInt("userid", 0);

        btnIcon = findViewById(R.id.btn_update_icon);
        btnPassword = findViewById(R.id.btn_update_password);
        headImg = (ImageView)findViewById(R.id.me_icon_personIcon);

        btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingAcountActivity.this,UpdatePasswordActivity.class);
                startActivity(intent);
            }
        });

        btnIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingAcountActivity.this, ImageGridActivity.class);
                startActivityForResult(intent, IMAGE_PICKER);
            }
        });

        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setMultiMode(false);
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(false); //是否按矩形区域保存
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
        imagePicker.setFocusWidth(720);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(720);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(200);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(200);//保存文件的高度。单位像素
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                //Log.d(TAG, "onActivityResult: " + images.get(0).path + images.get(0).width);
                sendPose(images.get(0).path);
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void sendPose(final String srcPath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File file = new File(srcPath);
                    OkHttpClient client = new OkHttpClient();
                    RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
                    RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", "headImg.png", fileBody)
                            .addFormDataPart("userid", userid + "").build();
                    Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/sendHeadImg").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    if (jsonObject.getInt("statuscode")==200) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Bitmap bm = BitmapFactory.decodeFile(srcPath);
                                headImg.setImageBitmap(bm);
                            }
                        });
                        SharedPreferences.Editor editor = getSharedPreferences("user_info", MODE_PRIVATE).edit();
                        editor.putString("headImgPath",jsonObject.getString("headImgPath"));
                        editor.apply();
                    } else
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SettingAcountActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                            }
                        });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SettingAcountActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

}
