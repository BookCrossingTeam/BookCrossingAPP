package com.example.administrator.bookcrossingapp.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.administrator.bookcrossingapp.MessageManagement;
import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.activity.MainActivity;

import static android.app.NotificationManager.IMPORTANCE_HIGH;

public class PollingService extends Service {

    private static final String TAG = "PollingService";

    private Handler handler;
    private Runnable runnable;
    static private int  delaytime;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        delaytime = 10000;

        sendNotification("正在运行中");

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "PollingService ping..... ");
                //Toast.makeText(PollingService.this, "PollingService ping.....", Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (MessageManagement.getInstance(PollingService.this).getMsgFromRemote()>0)
                            sendNotification("有新消息");
                        handler.postDelayed(runnable, delaytime);
                    }
                }).start();
            }
        };
        handler.postDelayed(runnable, delaytime);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "PollingService onDestroy: ");
        //Toast.makeText(PollingService.this, "PollingService onDestroy.....", Toast.LENGTH_SHORT).show();
        handler.removeCallbacks(runnable);
        stopForeground(true);
        super.onDestroy();
    }

    static public void setDelaytime(int time)
    {
        delaytime = time;
    }

    public void sendNotification(String msg) {
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder;

        if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT) {
            NotificationChannel channel = new NotificationChannel("1", "channel1", IMPORTANCE_HIGH);
// 开启指示灯，如果设备有的话
            channel.enableLights(true);
// 是否在久按桌面图标时显示此渠道的通知
            channel.setShowBadge(false);
// 设置是否应在锁定屏幕上显示此频道的通知
            channel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PRIVATE);
// 设置绕过免打扰模式
            channel.setBypassDnd(true);

            mNotifyMgr.createNotificationChannel(channel);

            builder = new NotificationCompat.Builder(PollingService.this, "1");
        } else
            builder = new NotificationCompat.Builder(PollingService.this);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(PollingService.this, 0,
                notificationIntent, 0);
        builder.setContentIntent(pendingIntent) // 设置PendingIntent
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.logo_launcher)) // 设置下拉列表中的图标(大图标)
                .setContentTitle("Bcrossing") // 设置下拉列表里的标题
                .setSmallIcon(R.mipmap.logo_launcher) // 设置状态栏内的小图标
                .setContentText(msg) // 设置上下文内容
                .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间


        if(msg.equals("正在运行中"))
        {
            builder.setOngoing(true);
            mNotifyMgr.notify(1100, builder.build());
            return;
        }

        builder.setAutoCancel(true);
        // Builds the notification and issues it.
        mNotifyMgr.notify(1101, builder.build());

    }
}
