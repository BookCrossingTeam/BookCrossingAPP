package com.example.admnistrator.bookcrossingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ForgetActivity extends AppCompatActivity {
    private static final String TAG = "ForgetActivity";

    private EditText passowrd1;
    private EditText passowrd2;
    private EditText telephone;
    private EditText identicode;
    private ImageView btn_resetpass;
    private ImageView confirm;
    private String passwordValue1, passwordValue2, telephoneValue, identicodeValue, code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        passowrd1 = (EditText) findViewById(R.id.editText14);
        passowrd2 = (EditText) findViewById(R.id.editText13);
        telephone = (EditText) findViewById(R.id.editText11);
        identicode = (EditText) findViewById(R.id.editText12);
        btn_resetpass = (ImageView) findViewById(R.id.imageView15);
        confirm = (ImageView) findViewById(R.id.imageView16);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telephoneValue = telephone.getText().toString();
                if (telephoneValue.equals("")) {
                    Toast.makeText(ForgetActivity.this, "请填写手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                sentmagess();
            }
        });
        btn_resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordValue1 = passowrd1.getText().toString();
                passwordValue2 = passowrd2.getText().toString();
                telephoneValue = telephone.getText().toString();
                identicodeValue = identicode.getText().toString();
                if (passwordValue1.equals(passwordValue2) == false) {
                    Toast.makeText(ForgetActivity.this, "请两次密码输入一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (identicodeValue.equals(code)) {
                    sent_info();
                    Toast.makeText(ForgetActivity.this, "密码重置成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ForgetActivity.this, "验证码不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    public void sent_info() { /**不知道怎么更新
     new Thread(new Runnable() {
    @Override
    public void run() {
    try {
    OkHttpClient client = new OkHttpClient();

    RequestBody requestBody = new FormBody.Builder().add("telephone", telephoneValue) .add("password", passwordValue1).build();
    Request request = new Request.Builder().url("http://120.24.217.191/sign_up.php").post(requestBody).build();
    Response response = client.newCall(request).execute();
    String responseData = response.body().string();
    } catch (Exception e) {
    e.printStackTrace();
    }
    }
    }).start();*/
    }
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
                Toast.makeText(ForgetActivity.this, responseData, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void dis_sentmagess_btn() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ForgetActivity.this, "短信发送成功", Toast.LENGTH_SHORT).show();
                //identicode.setText(code);
                confirm.setVisibility(View.GONE);
            }
        });
    }

}

