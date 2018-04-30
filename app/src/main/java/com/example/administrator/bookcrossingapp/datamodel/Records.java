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
public class Records {
    private String recordsBooknameURL;
    private String recordsBookname;

    private String recordsExchangeUser;
    private long startTime;
    private long recordsTime;

    public String getRecordsBooknameURL() {
        return recordsBooknameURL;
    }

    public void setRecordsBooknameURL(String recordsBooknameURL) {
        this.recordsBooknameURL = recordsBooknameURL;
    }

    public String getRecordsBookname() {
        return recordsBookname;
    }

    public void setRecordsBookname(String recordsBookname) {
        this.recordsBookname = recordsBookname;
    }

    public String getRecordsExchangeUser() {
        return recordsExchangeUser;
    }

    public void setRecordsExchangeUser(String recordsExchangeUser) {
        this.recordsExchangeUser = recordsExchangeUser;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getRecordsTime() {
        return recordsTime;
    }

    public void setRecordsTime(long recordsTime) {
        this.recordsTime = recordsTime;
    }
}
