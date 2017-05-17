package com.example.admnistrator.bookcrossingapp;

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

    public BookDetail(String username,String bookName,String author,String press,
                      String recommendedReason)
    {
        this.username = username;
        this.bookName = bookName;
        this.author = author;
        this.press = press;
        this.recommendedReason = recommendedReason;
        this.usernameImage = R.drawable.user_image;
        this.bookImage = R.drawable.book_image;
    }

    public int getBookImage() {
        return bookImage;
    }

    public int getUsernameImage() {
        return usernameImage;
    }

    public String getAuthor() {
        return author;
    }

    public String getBookName() {
        return bookName;
    }

    public String getPress() {
        return press;
    }

    public String getRecommendedReason() {
        return recommendedReason;
    }

    public String getUsername() {
        return username;
    }
}
