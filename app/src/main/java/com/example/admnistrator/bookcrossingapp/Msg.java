package com.example.admnistrator.bookcrossingapp;

/**
 * Created by yvemuki on 2018/3/11.
 */

public class Msg {
    public static final int TYPE_RECEIVED = 0;  //static可以进行类调用
    public static final int TYPE_SENT = 1;
    private String content;
    private int type;

    public Msg(String content, int type){
        this.content = content;
        this.type = type;
    }
    public String getContent(){

        return content;
    }
    public int getType(){
        return type;
    }
}
