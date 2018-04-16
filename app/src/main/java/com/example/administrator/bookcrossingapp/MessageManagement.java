package com.example.administrator.bookcrossingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.util.Log;

import com.example.administrator.bookcrossingapp.datamodel.Friend;
import com.example.administrator.bookcrossingapp.datamodel.Msg;
import com.example.administrator.bookcrossingapp.datamodel.MsgJson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.UpdateOrDeleteCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class MessageManagement {
    private static final String TAG = "MessageManagement";

    //创建 SingleObject 的一个对象
    private static MessageManagement instance = null;
    private static Context context;

    private int myuserid;
    private String myusername, myheadImgPath;
    private long lastTime;


    //让构造函数为 private，这样该类就不会被实例化
    private MessageManagement() {
        SharedPreferences pref = context.getSharedPreferences("user_info", MODE_PRIVATE);
        myuserid = pref.getInt("userid", 0);
        myusername = pref.getString("username", "");

    }


    //获取唯一可用的对象
    public static MessageManagement getInstance(Context _context) {
        context = _context;
        if (instance == null) {
            synchronized (MessageManagement.class) {
                if (instance == null) {
                    instance = new MessageManagement();
                }
            }
        }
        return instance;
    }

    public int getMyuserid() {
        return myuserid;
    }

    public String getMyusername() {
        return myusername;
    }

    public String getMyheadImgPath() {
        return myheadImgPath;
    }

    public void getMsgFromRemote() {
        Log.i(TAG, "getMsgFromRemote: ");
        try {
            List<Msg> msgList = DataSupport.order("time desc").limit(1).find(Msg.class);
            if (!msgList.isEmpty())
                lastTime = msgList.get(0).getTime();
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder().add("userid", myuserid + "").add("lastTime", lastTime + "").build();
            Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/queryMsg").post(requestBody).build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            //handleResponseData(responseData);
            Type type = new TypeToken<ArrayList<MsgJson>>() {
            }.getType();
            List<MsgJson> msgJsonList = new Gson().fromJson(responseData, type);
            boolean flag = false;
            for (MsgJson msgJson : msgJsonList) {
                Msg msg = new Msg();
                msg.setUserid(msgJson.getUserid());
                msg.setUsername(msgJson.getUsername());
                msg.setUserheadImgPath(msgJson.getHeadImgPath());
                msg.setContent(msgJson.getMessage());
                msg.setTime(msgJson.getMsgTime());
                msg.setIsRead(0);
                if (msgJson.getFromUserId() == myuserid) {
                    msg.setType(Msg.TYPE_SENT);
                    msg.setIsRead(1);
                } else {
                    msg.setType(Msg.TYPE_RECEIVED);
                    if (!flag) {
                        Vibrator vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
                        vibrator.vibrate(1000);
                        flag = true;
                    }
                }
                msg.saveThrows();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initFrinedList(List<Friend> friendList) {
        friendList.clear();
        List<Msg> msgList = DataSupport.order("time desc").find(Msg.class);
        HashSet hs = new HashSet<>();
        for (Msg msg : msgList) {
            int id = msg.getUserid();
            if (hs.contains(id))
                continue;
            hs.add(id);
            Log.i(TAG, "initFrinedList: " + msg.getUsername() + msg.getIsRead());
            Friend friend = new Friend();
            friend.setFriendName(msg.getUsername());
            friend.setUserid(msg.getUserid());
            friend.setTime(msg.getTime());
            friend.setIsread(msg.getIsRead());
            friendList.add(friend);
        }
        hs = null;
    }

    public void initMsglist(int userid, List<Msg> _msgList) {
        Log.i(TAG, "initMsglist: " + userid);
        _msgList.clear();
        List<Msg> msgList = DataSupport.where("userid = ? ", userid + "").order("time asc").find(Msg.class);
        _msgList.addAll(msgList);
        Msg msg = new Msg();
        msg.setIsRead(1);
        msg.updateAllAsync("userid = ? ", userid + "").listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int rowsAffected) {
                Log.i(TAG, "initMsglist onFinish: " + rowsAffected);
            }
        });
        Log.i(TAG, "initMsglist: " + _msgList.size());
    }

    public List<Msg> getFriendUnread(int userid) {
        List<Msg> msgList = DataSupport.where("userid = ? and isRead = ?", userid + "", "0").order("time asc").find(Msg.class);
        return msgList;
    }

    public boolean sendMsg(int touserid, String content) {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder().add("userid", myuserid + "").add("touserid", touserid + "").add("content", content).build();
            Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/sendMsg").post(requestBody).build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            if (responseData.equals("OK"))
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

}
