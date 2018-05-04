package com.example.administrator.bookcrossingapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.bookcrossingapp.CalcMD5;
import com.example.administrator.bookcrossingapp.R;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ScheduleActivity extends AppCompatActivity {

    private static final String TAG = "ScheduleActivity";

    private String username, password, code;
    private ImageView codeImg;
    private Button login;

    private WebView schedule_view;

    private String cookie;

    private int userid;

    private ProgressDialog pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_layout);
        codeImg = (ImageView) findViewById(R.id.schedule_code_img);
        login = (Button) findViewById(R.id.schedule_button);
        schedule_view = (WebView) findViewById(R.id.schedule_webView);
        SharedPreferences sp = getSharedPreferences("schedule_info", MODE_PRIVATE);
        String message = sp.getString("table", "");
        if (!message.equals(""))
            schedule_view.loadData(message, "text/html; charset=UTF-8", null);

        Intent intent = getIntent();
        userid = intent.getIntExtra("userid", 0);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setEnabled(false);
                pBar = new ProgressDialog(ScheduleActivity.this);
                pBar.setCancelable(false);
                pBar.setCanceledOnTouchOutside(false);
                pBar.setMessage("请稍候");
                pBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pBar.show();
                doLogin();
            }
        });

        codeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCodeImg();
            }
        });
    }

    private void getCodeImg() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cookie = getCookie();
                    String date = getDate();

                    final String filename = CalcMD5.getMD5(cookie);
                    downloadImg(date, getFilesDir() + "/" + filename, cookie);

                    Log.i(TAG, "run: " + cookie);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(ScheduleActivity.this).load(getFilesDir() + "/" + filename).into(codeImg);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ScheduleActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    private void doLogin() {

        username = ((TextView) findViewById(R.id.schedule_username)).getText().toString();
        password = ((TextView) findViewById(R.id.schedule_password)).getText().toString();
        code = ((TextView) findViewById(R.id.schedule_code)).getText().toString();

        if (username.equals("") || password.equals("") || code.equals("")) {
            Toast.makeText(ScheduleActivity.this, "填写完整", Toast.LENGTH_SHORT).show();
            login.setEnabled(true);
            pBar.dismiss();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                password = CalcMD5.getMD5(CalcMD5.getMD5(password) + CalcMD5.getMD5(code.toLowerCase()));
                try {

                    String _sessionid = getSessionid(cookie);

                    String new_userName = Base64.encodeToString((username + ";;" + _sessionid).getBytes("UTF-8"), Base64.DEFAULT);


                    String loginData = doLogin(new_userName, password, code, cookie);

                    {
                        Log.i(TAG, "run loginData: " + loginData);
                        JSONObject jsonObject = new JSONObject(loginData);
                        if (!jsonObject.getString("status").equals("200")) {
                            getCodeImg();
                            final String message = jsonObject.getString("message");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    login.setEnabled(true);
                                    pBar.dismiss();
                                    ((TextView) findViewById(R.id.schedule_code)).setText("");
                                    Toast.makeText(ScheduleActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            });
                            return;
                        }
                    }


                    final String tableHtml = getTable(username, cookie);

                    String tableJson = dealHtml(tableHtml);

                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("tableJson", tableJson).add("userid", userid + "").build();
                    Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/ScheduleUpdate").post(requestBody).build();
                    Response response = client.newCall(request).execute();

                    String responseData = response.body().string();

                    Thread.sleep(2000);

                    if (responseData.equals("OK"))
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String message = tableHtml;
                                message = message.replaceFirst("width:100%", "width:480px");
                                message = message.replaceFirst("height:100%", "height:1000px");

                                SharedPreferences.Editor editor = getSharedPreferences("schedule_info", MODE_PRIVATE).edit();
                                editor.putString("table", message);
                                editor.apply();


                                schedule_view.loadData(message, "text/html; charset=UTF-8", null);

                                ((TextView) findViewById(R.id.schedule_username)).setText("");
                                ((TextView) findViewById(R.id.schedule_password)).setText("");
                                ((TextView) findViewById(R.id.schedule_code)).setText("");

                                pBar.dismiss();
                                login.setEnabled(true);
                            }
                        });
                    else
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ScheduleActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                                pBar.dismiss();
                                login.setEnabled(true);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            login.setEnabled(true);
                            pBar.dismiss();
                            Toast.makeText(ScheduleActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            login.setEnabled(true);
                        }
                    });
                }
            }
        }).start();
    }


    private String dealHtml(String html) throws Exception {
        JSONObject jsonObj = new JSONObject();
        Document doc = Jsoup.parse(html);
        Elements tds = doc.select("td[class=\"td\"]");
        for (Element td : tds) {
            Elements divs = td.select("div[class]");
            for (Element div : divs) {
                jsonObj.put(div.attr("id"), div.text());
            }
        }
        return jsonObj.toString();
    }

    private void downloadImg(String date, String filename, String cookie) throws Exception {
        String urlString = "http://jwgl.ouc.edu.cn/cas/genValidateCode?dateTime=" + date;

        URL realUrl = new URL(urlString);
        // 打开和URL之间的连接
        URLConnection connection = realUrl.openConnection();
        // 设置通用的请求属性
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("Host", "jwgl.ouc.edu.cn");
        connection.setRequestProperty("Referer", "http://jwgl.ouc.edu.cn/cas/login.action");
        connection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 Edge/16.16299");
        connection.setRequestProperty("Accept-Encoding", "gzip,deflate");
        connection.setRequestProperty("Cookie", cookie);

        // 输入流
        InputStream is = connection.getInputStream();

        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        OutputStream os = new FileOutputStream(filename);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }

        // 完毕，关闭所有链接
        os.close();
        is.close();
    }

    private String getDate() {
        // Thu May 03 2018 12:10:15 GMT+0800
        String dateStr = new SimpleDateFormat("EEE'%20'MMM'%20'dd'%20'yyyy'%20'HH:mm:ss'%20''GMT'Z'%20''(中国标准时间)'",
                Locale.ENGLISH).format(new Date());
        //System.out.println("date=[" + dateStr + "]");
        return dateStr;
    }

    private String getSessionid(String cookie) {
        String sessionid = cookie.substring(cookie.indexOf("=") + 1);
        //System.out.println("sessionid=[" + sessionid + "]");
        return sessionid;
    }

    private String getCookie() throws Exception {
        String urlString = "http://jwgl.ouc.edu.cn/cas/login.action";
        URL realUrl = new URL(urlString);
        // 打开和URL之间的连接
        URLConnection connection = realUrl.openConnection();
        // 设置通用的请求属性
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("Host", "jwgl.ouc.edu.cn");
        connection.setRequestProperty("Referer", "http://web.ouc.edu.cn/jwc/");
        connection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 Edge/16.16299");
        connection.setRequestProperty("Accept-Encoding", "gzip,deflate");
        // 建立实际的连接
        connection.connect();
        // 获取所有响应头字段
        Map<String, List<String>> map = connection.getHeaderFields();

        String Cookie = map.get("Set-Cookie").get(0);
        Cookie = Cookie.substring(0, Cookie.indexOf(";"));
        //System.out.println("Cookie=[" + Cookie + "]");

        return Cookie;
    }

    private String doLogin(String userName, String password, String verCode, String cookie) throws Exception {
        String result = "";

        String urlString = "http://jwgl.ouc.edu.cn/cas/logon.action";
        String param = "_u" + verCode + "=" + userName + "&_p" + verCode + "=" + password + "&randnumber=" + verCode
                + "&isPasswordPolicy=1";
        //System.out.println("loginParam=[" + param + "]");

        URL realUrl = new URL(urlString);
        // 打开和URL之间的连接
        URLConnection connection = realUrl.openConnection();
        // 设置通用的请求属性
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("Origin", "http://jwgl.ouc.edu.cn");
        connection.setRequestProperty("Host", "jwgl.ouc.edu.cn");
        connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Referer", "http://jwgl.ouc.edu.cn/cas/login.action");
        connection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 Edge/16.16299");
        connection.setRequestProperty("Accept-Encoding", "gzip,deflate");
        connection.setRequestProperty("Cookie", cookie);

        // 发送POST请求必须设置如下两行
        connection.setDoOutput(true);
        connection.setDoInput(true);

        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;

        // 通过连接对象获取一个输出流
        os = connection.getOutputStream();
        // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
        os.write(param.getBytes());

        // 通过连接对象获取一个输入流，向远程读取
        is = connection.getInputStream();
        // 对输入流对象进行包装:charset根据工作项目组的要求来设置
        br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        StringBuffer sbf = new StringBuffer();
        String temp = null;
        // 循环遍历一行一行读取数据
        while ((temp = br.readLine()) != null) {
            sbf.append(temp);
        }
        result = sbf.toString();

        os.close();
        is.close();
        br.close();

        return result;
    }

    private String getTable(String userName, String cookie) throws Exception {
        String result = "";

        String urlString = "http://jwgl.ouc.edu.cn/student/wsxk.xskcb.jsp?params=";
        String xn = "2015";
        String xq = "2";
        String xh = userName;
        // params = "xn="+j$("#xn").val() + "&xq="+j$("#xq").val() + "&xh=" +
        // j$("#xh").val();
        String param = "xn=" + xn + "&xq=" + xq + "&xh=" + xh;
        param = Base64.encodeToString(param.getBytes("UTF-8"), Base64.DEFAULT);
        //System.out.println("tableParam=[" + param + "]");

        urlString = urlString + param;

        URL realUrl = new URL(urlString);
        // 打开和URL之间的连接
        URLConnection connection = realUrl.openConnection();
        // 设置通用的请求属性
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("Host", "jwgl.ouc.edu.cn");
        connection.setRequestProperty("Referer", "http://jwgl.ouc.edu.cn/student/xkjg.wdkb.jsp?menucode=JW130416");
        connection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 Edge/16.16299");
        connection.setRequestProperty("Accept-Encoding", "gzip,deflate");
        connection.setRequestProperty("Cookie", cookie);
        connection.setRequestProperty("Charset", "utf-8");

        connection.connect();

        /*
         * // 获取所有响应头字段 Map<String, List<String>> map =
         * connection.getHeaderFields(); // 遍历所有的响应头字段 for (String key :
         * map.keySet()) { System.out.println(key + "--->" + map.get(key)); }
         */

        InputStream is = null;
        BufferedReader br = null;

        // 通过连接对象获取一个输入流，向远程读取
        is = connection.getInputStream();
        is = new GZIPInputStream(connection.getInputStream());
        // 对输入流对象进行包装:charset根据工作项目组的要求来设置
        br = new BufferedReader(new InputStreamReader(is, "GBK"));

        StringBuffer sbf = new StringBuffer();
        String temp = null;
        // 循环遍历一行一行读取数据
        while ((temp = br.readLine()) != null) {
            sbf.append(temp);
        }
        result = sbf.toString();

        // result = new String(result.getBytes("gb2312"), "UTF-8");

        return result;
    }


}
