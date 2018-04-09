package com.example.administrator.bookcrossingapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.bookcrossingapp.R;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ForgetPasswordActivity extends AppCompatActivity {
    private static final String TAG = "ForgetPasswordActivity";

    private EditText editNewpassowrd;
    private EditText editConfirmpassowrd;
    private EditText editTelephone;
    private EditText editIdenticode;
    private ImageView imageSummit;
    private ImageView imageConfirm;
    private String newPasswordValue, confirmPasswordValue, telephoneValue, identicodeValue, code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        //初始化控件
        editNewpassowrd = (EditText) findViewById(R.id.edit_newPassword_forget);
        editConfirmpassowrd = (EditText) findViewById(R.id.edit_confirmPassord_forget);
        editTelephone = (EditText) findViewById(R.id.edit_telephone_forget);
        editIdenticode = (EditText) findViewById(R.id.edit_code_forget);
        imageSummit = (ImageView) findViewById(R.id.image_forgetPassword_summit);
        imageConfirm = (ImageView) findViewById(R.id.image_getCode);

        //提交修改
        imageConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telephoneValue = editTelephone.getText().toString();
                if (telephoneValue.equals("")) {
                    Toast.makeText(ForgetPasswordActivity.this, "请填写手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                sentmagess();
            }
        });
        imageSummit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPasswordValue = editNewpassowrd.getText().toString();
                confirmPasswordValue = editConfirmpassowrd.getText().toString();
                telephoneValue = editTelephone.getText().toString();
                identicodeValue = editIdenticode.getText().toString();
                if (newPasswordValue.equals(confirmPasswordValue) == false) {
                    Toast.makeText(ForgetPasswordActivity.this, "请两次密码输入一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (identicodeValue.equals(code)) {
                    sent_info();
                    Toast.makeText(ForgetPasswordActivity.this, "密码重置成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ForgetPasswordActivity.this, "验证码不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    ////////待实现
    //后台更新密码，同修改密码界面
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

    //发送手机验证码
    public void sentmagess() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    code = getcode();
                    RequestBody requestBody = new FormBody.Builder().add("number", telephoneValue).add("code", code).build();
                    Request request = new Request.Builder().url("http://120.24.217.191/sentmagess.php").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (SentOk(responseData))
                        dis_sentmagess_btn();
                    else
                        showResponse(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public String getcode() {
        String num = "";
        for (int i = 0; i < 6; i++) {
            int n = (int) (Math.random() * 10);
            num = num + n;
        }
        return num;
    }

    public boolean SentOk(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            boolean success = jsonObject.getBoolean("success");
            if (success)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void showResponse(final String responseData) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ForgetPasswordActivity.this, responseData, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void dis_sentmagess_btn() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ForgetPasswordActivity.this, "短信发送成功", Toast.LENGTH_SHORT).show();
                //identicode.setText(code);
                imageConfirm.setVisibility(View.GONE);
            }
        });
    }

}

