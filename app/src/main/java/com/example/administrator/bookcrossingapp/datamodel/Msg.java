package com.example.administrator.bookcrossingapp.datamodel;

import org.litepal.crud.DataSupport;

/**
 * Created by yvemuki on 2018/3/11.
 */

public class Msg  extends DataSupport {
    public static final int TYPE_RECEIVED = 0;  //static可以进行类调用
    public static final int TYPE_SENT = 1;
    public static final int TYPE_SYS = 2;
    private String content;
    private int type;
    private int isRead;
    private String username;
    private int userid;
    private String userheadImgPath;
    private long time;

    public Msg(String content, int type){
        this.content = content;
        this.type = type;
    }
    public Msg(){
    }
    public String getContent(){

        return content;
    }
    public int getType(){
        return type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserheadImgPath() {
        return userheadImgPath;
    }

    public void setUserheadImgPath(String userheadImgPath) {
        this.userheadImgPath = userheadImgPath;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
