package com.example.administrator.bookcrossingapp.datamodel;

/**
 * Created by Administrator on 2018/4/8.
 */

public class ReviewItem {
    private int articleId;
    private String title;
    private String author;
    private String coverImgUrl;
    private int likeAmount;

    public ReviewItem() {
    }

    public ReviewItem(int articleId, String title, String author, String coverImgUrl, int likeAmount) {
        this.articleId = articleId;
        this.title = title;
        this.author = author;
        this.coverImgUrl = coverImgUrl;
        this.likeAmount = likeAmount;
    }

    public  int getArticleId(){
        return articleId;
    }

    public void setArticleId(int articleId){
        this.articleId = articleId;
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

    public int getLikeAmount(){
        return this.likeAmount;
    }

    public void setLikeAmount(int likeAmount){
        this.likeAmount = likeAmount;
    }
}
