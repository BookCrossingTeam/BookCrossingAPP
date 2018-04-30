package com.example.administrator.bookcrossingapp.datamodel;

/**
 * Created by Administrator on 2018/4/8.
 */

public class ReviewItem {
    private String title;
    private String author;
    private String coverImgUrl;

    public ReviewItem() {
    }

    public ReviewItem(String title, String author, String coverImgUrl) {
        this.title = title;
        this.author = author;
        this.coverImgUrl = coverImgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }
}
