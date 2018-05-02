package com.example.administrator.bookcrossingapp.datamodel;

import org.litepal.crud.DataSupport;

/**
 * Created by 57010 on 2017/5/17.
 */

public class BookDetail extends DataSupport {
    static public final int bookType_ALL = 0;
    static public final int bookType_LA = 1;
    static public final int bookType_HS = 2;
    static public final int bookType_EM = 3;
    static public final int bookType_EL = 4;
    static public final int bookType_ST = 5;
    static public final int bookType_Others = 6;
    static public final String[] bookTypeName ={"ALL","Literature & Art","Humanities & Social Sciences","Economic & Management","Enducation & Life","Science & Technology","Others"};
    private String username;
    private int userid;
    private String userheadpath;

    private String bookName;
    private String author;
    private String press;
    private String recommendedReason;
    private String bookImageUrl;
    private int bookid;
    private int bookType;
    private int exchangeState;

    private long posetime;

    public int getExchangeState() {
        return exchangeState;
    }

    public void setExchangeState(int exchangeState) {
        this.exchangeState = exchangeState;
    }

    public int getBookType() {
        return bookType;
    }

    public void setBookType(int bookType) {
        this.bookType = bookType;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getRecommendedReason() {
        return recommendedReason;
    }

    public void setRecommendedReason(String recommendedReason) {
        this.recommendedReason = recommendedReason;
    }

    public String getBookImageUrl() {
        return bookImageUrl;
    }

    public void setBookImageUrl(String bookImageUrl) {
        this.bookImageUrl = bookImageUrl;
    }

    public long getPosetime() {
        return posetime;
    }

    public void setPosetime(long posetime) {
        this.posetime = posetime;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUserheadpath() {
        return userheadpath;
    }

    public void setUserheadpath(String userheadpath) {
        this.userheadpath = userheadpath;
    }
}
