package com.example.administrator.bookcrossingapp.datamodel;

/**
 * Created by yvemuki on 2018/3/13.
 */

public class Friend {
    private int friendPic;
    private String friendName;
    private int userid;
    private int isread;
    private long time;

    public Friend(int friendPic, String friendName) {
        this.friendPic = friendPic;
        this.friendName = friendName;
    }

    public Friend() {
    }

    public int getIsread() {
        return isread;
    }

    public void setIsread(int isread) {
        this.isread = isread;
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

    public int getFriendPic() {
        return friendPic;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendPic(int friendPic) {
        this.friendPic = friendPic;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }
}
