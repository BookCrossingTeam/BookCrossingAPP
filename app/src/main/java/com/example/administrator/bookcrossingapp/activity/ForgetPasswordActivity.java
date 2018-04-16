package com.example.administrator.bookcrossingapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

public class ForgetPasswordActivity extends AppCompatActivity {
    private static final String TAG = "ForgetPasswordActivity";

    private EditText editNewpassowrd;
    private EditText editTelephone;
    private EditText editIdenticode;
    private ImageView imageSummit;

    private ImageView imageConfirm;
    private String newPasswordValue, telephoneValue, identicodeValue;

    private static String sessionid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        //初始化控件
        editNewpassowrd = (EditText) findViewById(R.id.edit_newPassword_forget);
        editTelephone = (EditText) findViewById(R.id.edit_telephone_forget);
        editIdenticode = (EditText) findViewById(R.id.edit_code_forget);
        imageSummit = (ImageView) findViewById(R.id.image_forgetPassword_summit);
        imageConfirm = (ImageView) findViewById(R.id.image_getCode);

        //获取验证码
        imageConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telephoneValue = editTelephone.getText().toString();
                if (telephoneValue.equals("")) {
                    Toast.makeText(ForgetPasswordActivity.this, "请填写手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Pattern.matches("1[0-9]{10}", telephoneValue)) {
                    Toast.makeText(ForgetPasswordActivity.this, "请填写正确手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient();
                            RequestBody requestBody = new FormBody.Builder().add("number", telephoneValue).build();
                            Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/forgetSendmessage").post(requestBody).build();
                            Response response = client.newCall(request).execute();
                            if (!response.isSuccessful()) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ForgetPasswordActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(ForgetPasswordActivity.this, "短信发送成功", Toast.LENGTH_SHORT).show();
                                        imageConfirm.setVisibility(View.GONE);
                                        editTelephone.setEnabled(false);
                                    }
                                });
                            } else if (datajson.getInt("statecode") == 100) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ForgetPasswordActivity.this, "手机号未注册", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else if (datajson.getInt("statecode") == 101) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ForgetPasswordActivity.this, "非法手机号", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else if (datajson.getInt("statecode") == 102) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ForgetPasswordActivity.this, "获取验证码已达上限，请稍后再试", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else if (datajson.getInt("statecode") == 103) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ForgetPasswordActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ForgetPasswordActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });

        imageSummit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPasswordValue = editNewpassowrd.getText().toString();
                telephoneValue = editTelephone.getText().toString();
                identicodeValue = editIdenticode.getText().toString();
                if (newPasswordValue.equals("") || telephoneValue.equals("") || telephoneValue.equals("") || identicodeValue.equals("")) {
                    Toast.makeText(ForgetPasswordActivity.this, "请填写完整", Toast.LENGTH_SHORT).show();
                    return;
                }

                newPasswordValue = CalcMD5.getMD5(newPasswordValue);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient();
                            RequestBody requestBody = new FormBody.Builder().add("telephone", telephoneValue).add("password", newPasswordValue).add("code", identicodeValue).build();
                            Request request = new Request.Builder().addHeader("cookie", sessionid).url("http://120.24.217.191/Book/APP/forgetpassword").post(requestBody).build();
                            Response response = client.newCall(request).execute();
                            if (!response.isSuccessful()) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ForgetPasswordActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(ForgetPasswordActivity.this, "重置成功", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                            } else if (datajson.getInt("statecode") == 100) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ForgetPasswordActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ForgetPasswordActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });
    }

}

