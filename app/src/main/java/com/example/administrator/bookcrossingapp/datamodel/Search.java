package com.example.administrator.bookcrossingapp.datamodel;

/**
 * Created by Administrator on 2018/4/4.
 */

public class Search {
    private int searchPic;
    private String searchContent;
    private String disPlay;
    static public final int DOUBAN = 1;
    static public final int LOCAL = 2;
    static public final int REMOTE = 3;
    private int type;

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

    public void setSearchPic(int searchPic) {
        this.searchPic = searchPic;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDisPlay() {
        return disPlay;
    }

    public void setDisPlay(String disPlay) {
        this.disPlay = disPlay;
    }
}
