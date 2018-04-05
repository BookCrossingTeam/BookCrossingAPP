package com.example.admnistrator.bookcrossingapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnAccount;
    private Button btnFeedback;
    private Button btnSoftware;
    private Button btnHelp;
    private Dialog dialog;//下弹窗
    private int REQUEST_CODE_SCAN = 111; //扫码


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        btnAccount = (Button) findViewById(R.id.btn_setting_account);
        btnFeedback = (Button) findViewById(R.id.btn_setting_feedback);
        btnSoftware = (Button) findViewById(R.id.btn_setting_software);
        btnHelp = (Button) findViewById(R.id.btn_setting_help);
        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, SettingAcountActivity.class);
                startActivity(intent);
            }
        });
        btnHelp.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_setting_help:
                //弹出对话框
                setDialog();
                break;
            case R.id.btn_scanning:
                //选择扫码按钮
                //Toast.makeText(this, "扫码", Toast.LENGTH_SHORT).show();
                //ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
                //也可以不传这个参数
                //不传的话  默认都为默认不震动  其他都为true
                ZxingConfig config = new ZxingConfig();
                config.setShowbottomLayout(true);//底部布局（包括闪光灯和相册）
                config.setPlayBeep(true);//是否播放提示音
                config.setShake(true);//是否震动
                config.setShowAlbum(true);//是否显示相册
                config.setShowFlashLight(true);//是否显示闪光灯
                //如果不传 ZxingConfig的话，两行代码就能搞定了
                Intent intent = new Intent(this, CaptureActivity.class);
                intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
                break;
            case R.id.btn_share_book:
                //手动上传已有按钮
                //Toast.makeText(this, "手动上传", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(SettingActivity.this, MainActivity.class);
                intent2.putExtra("fragmentid",3);
                startActivity(intent2);
                break;
            case R.id.btn_want_book:
                //手动上传想要按钮
                //Toast.makeText(this, "手动上传", Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(SettingActivity.this, PosingWantingActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn_cancel:
                //取消按钮
                //Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                break;

        }
    }

    private void setDialog() {
        dialog = new Dialog(this, R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.bottom_dialog, null);
        //初始化视图
        root.findViewById(R.id.btn_scanning).setOnClickListener(this);
        root.findViewById(R.id.btn_share_book).setOnClickListener(this);
        root.findViewById(R.id.btn_want_book).setOnClickListener(this);
        root.findViewById(R.id.btn_cancel).setOnClickListener(this);
        dialog.setContentView(root);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
//        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();

        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        dialog.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == -1) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                Toast.makeText(this, "扫描结果为：" + content, Toast.LENGTH_SHORT).show();
            }
        }
    }




}
