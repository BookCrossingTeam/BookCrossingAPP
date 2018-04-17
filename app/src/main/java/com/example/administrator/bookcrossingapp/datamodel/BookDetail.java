package com.example.administrator.bookcrossingapp.datamodel;

import com.example.administrator.bookcrossingapp.R;

/**
 * Created by 57010 on 2017/5/17.
 */

public class BookDetail {
    private String username;
    private int usernameImage;
    private String bookName;
    private String author;
    private String press;
    private String recommendedReason;
    private int bookImage;
    private String bookImageUrl;
    private long posetime;
    private int userid;
    private String userheadpath;

    public String getUserheadpath() {
        return userheadpath;
    }

    public void setUserheadpath(String userheadpath) {
        this.userheadpath = userheadpath;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getBookImageUrl() {
        return bookImageUrl;
    }

    public void setBookImageUrl(String bookImageUrl) {
        this.bookImageUrl = bookImageUrl;
    }

    public BookDetail(String username, String bookName, String author, String press,
                      String recommendedReason, String bookImageUrl) {
        this.username = username;
        this.bookName = bookName;
        this.author = author;
        this.press = press;
        this.recommendedReason = recommendedReason;
        this.usernameImage = R.drawable.user_image;
        this.bookImage = R.drawable.book_image;
        this.bookImageUrl = bookImageUrl;

    }

    public BookDetail(String username, String bookName, String author, String press,
                      String recommendedReason) {
        this.username = username;
        this.bookName = bookName;
        this.author = author;
        this.press = press;
        this.recommendedReason = recommendedReason;
        this.usernameImage = R.drawable.user_image;
        this.bookImage = R.drawable.book_image;
        //this.bookImageUrl = bookImageUrl;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUsernameImage() {
        return usernameImage;
    }

    public void setUsernameImage(int usernameImage) {
        this.usernameImage = usernameImage;
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

    public int getBookImage() {
        return bookImage;

    }

    public void setBookImage(int bookImage) {
        this.bookImage = bookImage;
    }

    public long getPosetime() {
        return posetime;
    }

    public void setPosetime(long posetime) {
        this.posetime = posetime;
    }
}
