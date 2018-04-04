package com.example.admnistrator.bookcrossingapp;

/**
 * Created by Administrator on 2018/4/4.
 */

public class Search {
    private int searchPic;
    private String searchContent;

    public Search(int searchPic, String searchContent) {
        this.searchPic = searchPic;
        this.searchContent = searchContent;
    }

    public int getSearchPic() {
        return searchPic;
    }

    public String getSearchContent() {
        return searchContent;
    }


}
