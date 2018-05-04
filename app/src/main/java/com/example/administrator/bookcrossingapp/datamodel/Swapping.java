package com.example.administrator.bookcrossingapp.datamodel;

/**
 * Created by Administrator on 2018/4/7.
 */


/* 关于状态
    0 线上未确认状态 //不存在
    1 线上确认状态
    2 线下确认状态
    3 归还状态
    4 超期失效状态
*/
public class Swapping {
    private int swappingPic1;
    private String swappingBookname1;
    private int swappingPic2;
    private String swappingBookname2;
    private int now_state;
    private boolean confirm;
    private String swappingBookURL1;
    private String SwappingBookURL2;
    private int exchangeid;
    private int myuserid;
    private int touserid;
    private int bookAid;
    private int bookBid;
    private int exchangeState;


    public Swapping() {
    }

    public Swapping(int swappingPic1, String swappingBookname1, int swappingPic2, String swappingBookname2, int now_state, boolean confirm) {

        this.swappingPic1 = swappingPic1;
        this.swappingBookname1 = swappingBookname1;
        this.swappingPic2 = swappingPic2;
        this.swappingBookname2 = swappingBookname2;
        this.now_state = now_state;
        this.confirm = confirm;
    }

    public int getExchangeState() {
        return exchangeState;
    }

    public void setExchangeState(int exchangeState) {
        this.exchangeState = exchangeState;
    }

    public int getMyuserid() {
        return myuserid;
    }

    public void setMyuserid(int myuserid) {
        this.myuserid = myuserid;
    }

    public int getTouserid() {
        return touserid;
    }

    public void setTouserid(int touserid) {
        this.touserid = touserid;
    }

    public int getSwappingPic1() {
        return swappingPic1;
    }

    public void setSwappingPic1(int swappingPic1) {
        this.swappingPic1 = swappingPic1;
    }

    public String getSwappingBookname1() {
        return swappingBookname1;
    }

    public void setSwappingBookname1(String swappingBookname1) {
        this.swappingBookname1 = swappingBookname1;
    }

    public int getSwappingPic2() {
        return swappingPic2;
    }

    public void setSwappingPic2(int swappingPic2) {
        this.swappingPic2 = swappingPic2;
    }

    public String getSwappingBookname2() {
        return swappingBookname2;
    }

    public void setSwappingBookname2(String swappingBookname2) {
        this.swappingBookname2 = swappingBookname2;
    }

    public int getNow_state() {
        return now_state;
    }

    public void setNow_state(int now_state) {
        this.now_state = now_state;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public String getSwappingBookURL1() {
        return swappingBookURL1;
    }

    public void setSwappingBookURL1(String swappingBookURL1) {
        this.swappingBookURL1 = swappingBookURL1;
    }

    public String getSwappingBookURL2() {
        return SwappingBookURL2;
    }

    public void setSwappingBookURL2(String swappingBookURL2) {
        SwappingBookURL2 = swappingBookURL2;
    }

    public int getExchangeid() {
        return exchangeid;
    }

    public void setExchangeid(int exchangeid) {
        this.exchangeid = exchangeid;
    }

    public int getBookAid() {
        return bookAid;
    }

    public void setBookAid(int bookAid) {
        this.bookAid = bookAid;
    }

    public int getBookBid() {
        return bookBid;
    }

    public void setBookBid(int bookBid) {
        this.bookBid = bookBid;
    }
}
