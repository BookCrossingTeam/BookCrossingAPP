package com.example.admnistrator.bookcrossingapp;

/**
 * Created by yvemuki on 2018/3/13.
 */

public class Friend {
    private int friendPic;
    private String friendName;

    public Friend(int friendPic, String friendName) {
        this.friendPic = friendPic;
        this.friendName = friendName;
    }

    public int getFriendPic() {
        return friendPic;
    }

    public String getFriendName() {
        return friendName;
    }

}
