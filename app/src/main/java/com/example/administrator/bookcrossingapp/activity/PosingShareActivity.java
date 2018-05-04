package com.example.administrator.bookcrossingapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.administrator.bookcrossingapp.GlideImageLoader;
import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.datamodel.BookDetail;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PosingShareActivity extends AppCompatActivity {
    private static final String TAG = "PosingShareActivity";

    private EditText bookName;
    private EditText author;
    private EditText press;
    private EditText recommendedReason;
    private Spinner classify;
    private ImageView pose_btn;
    private String bookNameValue, authorValue, pressValue, recommendedReasonValue, classifyValue;
    private int userid;
    private ImageView bookImg;
    private String bookImgAbsolutePath = null;
    private int bookType;

    private int IMAGE_PICKER = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posing_share);
        SharedPreferences pref = this.getSharedPreferences("user_info", MODE_PRIVATE);
        userid = pref.getInt("userid", 0);
        init();//初始化组件
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(720);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(960);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(720);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(960);//保存文件的高度。单位像素
    }

    private void init() {
        bookName = (EditText) findViewById(R.id.edit_posing_shared_name);
        author = (EditText) findViewById(R.id.edit_posing_shared_author);
        press = (EditText) findViewById(R.id.edit_posing_shared_press);
        recommendedReason = (EditText) findViewById(R.id.edit_posing_shared_recommend);
        pose_btn = (ImageView) findViewById(R.id.btn_posing_shared);
        classify = findViewById(R.id.spinner_posing_shared);
        bookImg = (ImageView) findViewById(R.id.img_posing_shared_pic);

        bookImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PosingShareActivity.this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
                startActivityForResult(intent, IMAGE_PICKER);
            }
        });

        pose_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pose_btn.setEnabled(false);
                bookNameValue = bookName.getText().toString();
                authorValue = author.getText().toString();
                pressValue = press.getText().toString();
                recommendedReasonValue = recommendedReason.getText().toString();
                classifyValue = classify.getSelectedItem().toString();
                if (bookNameValue.equals("") || authorValue.equals("") || pressValue.equals("") || recommendedReasonValue.equals("") || bookImgAbsolutePath == null) {
                    Toast.makeText(PosingShareActivity.this, "请填写完整", Toast.LENGTH_SHORT).show();
                    pose_btn.setEnabled(true);
                    return;
                }
                if (classifyValue.equals("请选择分类")) {
                    Toast.makeText(PosingShareActivity.this, "请选择分类", Toast.LENGTH_SHORT).show();
                    pose_btn.setEnabled(true);
                    return;
                }
                if(classifyValue.equals(BookDetail.bookTypeName[1]))
                    bookType = 1;
                if(classifyValue.equals(BookDetail.bookTypeName[2]))
                    bookType = 2;
                if(classifyValue.equals(BookDetail.bookTypeName[3]))
                    bookType = 3;
                if(classifyValue.equals(BookDetail.bookTypeName[4]))
                    bookType = 4;
                if(classifyValue.equals(BookDetail.bookTypeName[5]))
                    bookType = 5;
                if(classifyValue.equals(BookDetail.bookTypeName[6]))
                    bookType = 6;
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
                            .addFormDataPart("userid", userid+"").addFormDataPart("bookName", bookNameValue)
                            .addFormDataPart("bookType",bookType+"")
                            .addFormDataPart("author", authorValue).addFormDataPart("press", pressValue).addFormDataPart("recommendedReason", recommendedReasonValue).build();
                    Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/sendPose").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (responseData.equals("true")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PosingShareActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    } else
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PosingShareActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                            }
                        });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PosingShareActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pose_btn.setEnabled(true);
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                Log.d(TAG, "onActivityResult: " + images.get(0).path + images.get(0).width);
                final String srcPath = images.get(0).path;
                Bitmap bm = BitmapFactory.decodeFile(srcPath);
                bookImg.setImageBitmap(bm);
                //openFileInput("tmp.jpg");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File file = new File(getFilesDir(), "temp.jpg");//将要保存图片的路径
                        try {
                            Bitmap bm = getimage(srcPath);
                            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                            bos.flush();
                            bos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        bookImgAbsolutePath = file.getAbsolutePath();
                    }
                }).start();
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 图片按比例大小压缩方法
     *
     * @param srcPath （根据路径获取图片并压缩）
     * @return
     */
    public static Bitmap getimage(String srcPath) {

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空
        newOpts.inSampleSize = calculateInSampleSize(newOpts, 540, 720);
        newOpts.inJustDecodeBounds = false;
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;

        while (baos.toByteArray().length / 1024 > 50) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            Log.i(TAG, "compressImage: "+baos.toByteArray().length);
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        Log.i(TAG, "压缩后图片的大小" + (image.getByteCount() / 1024)
                + "kb宽度为" + image.getWidth() + "高度为" + image.getHeight());
        return bitmap;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }

        return inSampleSize;
    }

}
