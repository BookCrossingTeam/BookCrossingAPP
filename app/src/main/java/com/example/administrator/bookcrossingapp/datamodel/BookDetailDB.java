package com.example.administrator.bookcrossingapp.datamodel;

import org.litepal.crud.DataSupport;

public class BookDetailDB extends DataSupport {
    private String username;
    private String bookName;
    private String author;
    private String press;
    private String recommendedReason;
    private String bookImageUrl;
    private String posetime;
    private int userid;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
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

    public String getPosetime() {
        return posetime;
    }

    public void setPosetime(String posetime) {
        this.posetime = posetime;
    }
}
