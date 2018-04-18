package com.example.administrator.bookcrossingapp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.bookcrossingapp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SettingActivity extends AppCompatActivity {
    private static final String TAG = "SettingActivity";

    private Button btnAccount;
    private Button btnFeedback;
    private Button btnSoftware;
    private Button btnHelp;
    private Button btnupdate;
    private Button btnLogoff;
    private ProgressDialog pBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        btnAccount = (Button) findViewById(R.id.btn_setting_account);
        btnFeedback = (Button) findViewById(R.id.btn_setting_feedback);
        btnSoftware = (Button) findViewById(R.id.btn_setting_software);
        btnupdate = (Button) findViewById(R.id.btn_setting_update);
        btnHelp = (Button) findViewById(R.id.btn_setting_help);
        btnLogoff = (Button)findViewById(R.id.btn_log_off);



        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, SettingAcountActivity.class);
                startActivity(intent);
            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SettingActivity.this)
                        .setTitle("版本更新")
                        .setMessage("我也不知道更新了什么")
                        .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                pBar = new ProgressDialog(SettingActivity.this);
                                pBar.setCancelable(true);
                                pBar.setTitle("正在下载");
                                pBar.setMessage("下载中");
                                pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                // downFile(URLData.DOWNLOAD_URL);
                                final DownloadTask downloadTask = new DownloadTask(
                                        SettingActivity.this);
                                downloadTask.execute("http://120.24.217.191/Book/apk/app-release.apk");
                                pBar.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        downloadTask.cancel(true);
                                    }
                                });
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        btnLogoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setTitle("提示");
                builder.setMessage("你确认注销账号吗？");
                builder.setCancelable(true);

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //确认退出登录
                        //清空sharedPreference数据
                        SharedPreferences sp = getSharedPreferences("user_info",MODE_PRIVATE);
                        if(sp!=null){
                            sp.edit().clear().commit();
                            Toast.makeText(SettingActivity.this,"test", Toast.LENGTH_LONG).show();
                        }
                        //
                        Intent intent = new Intent(SettingActivity.this, LoginActivity.class );
                        startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        SettingActivity.this.finish();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //取消操作
                    }
                });
                builder.create().show();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle("提示");
        builder.setMessage("你确认注销账号吗？");
        builder.setCancelable(true);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //确认退出登录
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class );
                startActivity(intent);

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //取消操作
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }



    /**
     * 下载应用
     *
     * @author Administrator
     */

    class DownloadTask extends AsyncTask<String, Integer, String> {
        private Context context;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String[] sUrl) {

            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;

            File file = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error
                // report
                // instead of the file
                Log.i(TAG, "doInBackground: "+"Server returned HTTP "
                        + connection.getResponseCode() + " "
                        + connection.getResponseMessage());
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return null;
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                file = new File(getFilesDir(), "app-release.apk");
                if (!file.exists()) {
                    // 判断父文件夹是否存在
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                }


                input = connection.getInputStream();
                output = new FileOutputStream(file);
                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }

            } catch (Exception e) {
                e.getStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SettingActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                    }
                });
                return null;
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {

                }
                if (connection != null)
                    connection.disconnect();
            }
            return "OK";
        }


        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            pBar.show();
        }

        @Override
        protected void onProgressUpdate(Integer[] progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            Log.i(TAG, "onProgressUpdate: " + progress[0].toString());
            pBar.setIndeterminate(false);
            pBar.setMax(100);
            pBar.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            pBar.dismiss();
            if (result != null && result.equals("OK")) {
                Log.i(TAG, "onPostExecute: ");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                if (Build.VERSION.SDK_INT  >= Build.VERSION_CODES.N) {
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    //参数1 上下文；参数2 Provider主机地址 authorities 和配置文件中保持一致 ；参数3  共享的文件
                    Uri apkUri = FileProvider.getUriForFile(SettingActivity.this, "com.example.administrator.bookcrossingapp.fileprovider",new File(getFilesDir(), "app-release.apk"));
                    intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                } else {
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setDataAndType(Uri.fromFile(new File(getFilesDir(), "app-release.apk")), "application/vnd.android.package-archive");
                }
                startActivity(intent);
            }
        }



    }


}
