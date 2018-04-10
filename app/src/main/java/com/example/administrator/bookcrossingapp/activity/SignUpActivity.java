package com.example.administrator.bookcrossingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.bookcrossingapp.CalcMD5;
import com.example.administrator.bookcrossingapp.R;

import org.json.JSONObject;

import java.util.List;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";
    private EditText userName;
    private EditText passowrd;
    private EditText telephone;
    private EditText identicode;
    private ImageView btn_signup;
    private ImageView confirm;
    private String userNameValue, passwordValue, telephoneValue, identicodeValue, code;
    private TextView tv_agreement;
    private CheckBox checkbox;

    private static String sessionid;

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
        tv_agreement = ((TextView) findViewById(R.id.TextView_agreement));
        checkbox = (CheckBox) findViewById(R.id.checkBox);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telephoneValue = telephone.getText().toString();
                if (telephoneValue.equals("")) {
                    Toast.makeText(SignUpActivity.this, "请填写手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Pattern.matches("1[0-9]{10}", telephoneValue)) {
                    Toast.makeText(SignUpActivity.this, "请填写正确手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient();
                            RequestBody requestBody = new FormBody.Builder().add("number", telephoneValue).build();
                            Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/sendmessage").post(requestBody).build();
                            Response response = client.newCall(request).execute();
                            if (!response.isSuccessful()) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SignUpActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return;
                            }
                            String responseData = response.body().string();
                            JSONObject datajson = new JSONObject(responseData);
                            if (datajson.getInt("statecode") == 200) {
                                Headers headers = response.headers();
                                List<String> cookies = headers.values("Set-Cookie");
                                String session = cookies.get(0);
                                sessionid = session.substring(0, session.indexOf(";"));
                                Log.i("info_s", "session is :" + sessionid);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SignUpActivity.this, "短信发送成功", Toast.LENGTH_SHORT).show();
                                        confirm.setVisibility(View.GONE);
                                        telephone.setEnabled(false);
                                    }
                                });
                            } else if (datajson.getInt("statecode") == 100) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SignUpActivity.this, "手机号已注册", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else if (datajson.getInt("statecode") == 101) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SignUpActivity.this, "非法手机号", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else if (datajson.getInt("statecode") == 102) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SignUpActivity.this, "获取验证码已达上限，请稍后再试", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else if (datajson.getInt("statecode") == 103) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SignUpActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SignUpActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameValue = userName.getText().toString();
                passwordValue = passowrd.getText().toString();
                telephoneValue = telephone.getText().toString();
                identicodeValue = identicode.getText().toString();
                if (userNameValue.equals("") || passwordValue.equals("") || telephoneValue.equals("") || identicodeValue.equals("")) {
                    Toast.makeText(SignUpActivity.this, "请填写完整", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!checkbox.isChecked()) {
                    Toast.makeText(SignUpActivity.this, "请阅读协议", Toast.LENGTH_SHORT).show();
                    return;
                }

                passwordValue = CalcMD5.getMD5(passwordValue);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient();
                            RequestBody requestBody = new FormBody.Builder().add("telephone", telephoneValue).add("userName", userNameValue).add("password", passwordValue).add("code", identicodeValue).build();
                            Request request = new Request.Builder().addHeader("cookie", sessionid).url("http://120.24.217.191/Book/APP/sign_up").post(requestBody).build();
                            Response response = client.newCall(request).execute();
                            if (!response.isSuccessful()) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SignUpActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return;
                            }
                            String responseData = response.body().string();
                            JSONObject datajson = new JSONObject(responseData);
                            if (datajson.getInt("statecode") == 200) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SignUpActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                            } else if (datajson.getInt("statecode") == 100) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SignUpActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else if (datajson.getInt("statecode") == 101) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SignUpActivity.this, "用户名或手机号已存在", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SignUpActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });

        tv_agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, AgreementActivity.class);
                startActivity(intent);
            }
        });
    }

}
