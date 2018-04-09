package com.example.administrator.bookcrossingapp.datamodel;

/**
 * Created by Administrator on 2018/4/8.
 */

public class ReviewItem {
    private String title;
    private String author;
    private int cover;

    public ReviewItem() {
    }

    public ReviewItem(String title, String author, int cover) {
        this.title = title;
        this.author = author;
        this.cover = cover;
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

    public int getCover() {
        return cover;
    }

    public void setCover(int cover) {
        this.cover = cover;
    }
}
