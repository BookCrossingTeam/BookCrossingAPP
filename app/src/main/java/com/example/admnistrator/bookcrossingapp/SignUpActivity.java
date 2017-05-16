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



public class SignUpActivity extends AppCompatActivity {
    private static final String TAG="SignUpActivity";
    private EditText userName;
    private EditText passowrd;
    private EditText telephone;
    private EditText identicode;
    private ImageView btn_signup;
    private ImageView confirm;
    private String userNameValue, passwordValue,telephoneValue,identicodeValue,code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userName = (EditText) findViewById(R.id.editText);
        passowrd = (EditText) findViewById(R.id.editText2);
        telephone = (EditText) findViewById(R.id.editText3);
        identicode = (EditText) findViewById(R.id.editText4);
        btn_signup = (ImageView) findViewById(R.id.imageView6);
        confirm = (ImageView) findViewById(R.id.imageView7);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telephoneValue = telephone.getText().toString();
                if(telephoneValue.equals(""))
                {
                    Toast.makeText(SignUpActivity.this,"请填写手机号",Toast.LENGTH_SHORT).show();
                    return ;
                }
                sentmagess();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameValue = userName.getText().toString();
                passwordValue = passowrd.getText().toString();
                telephoneValue = telephone.getText().toString();
                identicodeValue = identicode.getText().toString();
                if(userNameValue.equals("") || passwordValue.equals("") || telephoneValue.equals("") || identicodeValue.equals(""))
                {
                    Toast.makeText(SignUpActivity.this,"请填写完整",Toast.LENGTH_SHORT).show();
                    return ;
                }

                if(identicodeValue.equals(code))
                {
                    sent_info();
                    Toast.makeText(SignUpActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(SignUpActivity.this,"验证码不正确",Toast.LENGTH_SHORT).show();
                    return ;
                }
            }
        });
    }

    public void sent_info()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("telephone",telephoneValue).add("userName",userNameValue).add("password",passwordValue).build();
                    Request request = new Request.Builder().url("http://120.24.217.191/sign_up.php").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sentmagess()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    code = getcode();
                    RequestBody requestBody = new FormBody.Builder().add("number",telephoneValue).add("code",code).build();
                    Request request = new Request.Builder().url("http://120.24.217.191/sentmagess.php").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if(SentOk(responseData))
                        dis_sentmagess_btn();
                    else
                        showResponse(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public boolean SentOk(String jsonData)
    {
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            boolean success = jsonObject.getBoolean("success");
            if(success)
                return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public String getcode()
    {
        String num = "";
        for(int i=0;i<6;i++)
        {
            int n = (int)(Math.random() *10);
            num = num + n;
        }
        return num;
    }

    public void showResponse(final String responseData)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SignUpActivity.this,responseData,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void dis_sentmagess_btn()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SignUpActivity.this,"短信发送成功",Toast.LENGTH_SHORT).show();
                //identicode.setText(code);
                confirm.setVisibility(View.GONE);
            }
        });
    }
}
