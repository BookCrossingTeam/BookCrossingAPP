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
    private int recordsPic;
    private String recordsBookname;
    private int recordState;
    private String recordsExchangeUser;
    private String recordsTime;

    public Records(String recordsBookname) {
        this.recordsBookname = recordsBookname;
    }

    public Records(int recordsPic, String recordsBookname, int recordState, String recordsExchangeUser, String recordsTime) {
        this.recordsPic = recordsPic;
        this.recordsBookname = recordsBookname;
        this.recordState = recordState;
        this.recordsExchangeUser = recordsExchangeUser;
        this.recordsTime = recordsTime;
    }

    public int getRecordsPic() {
        return recordsPic;
    }

    public void setRecordsPic(int RecordsPic) {
        this.recordsPic = RecordsPic;
    }

    public String getRecordsBookname() {
        return recordsBookname;
    }

    public void setRecordsBookname(String recordsBookname) {
        this.recordsBookname = recordsBookname;
    }

    public int getRecordState() {
        return recordState;
    }

    public void setRecordState(int recordState) {
        this.recordState = recordState;
    }

    public String getRecordsExchangeUser() {
        return recordsExchangeUser;
    }

    public void setRecordsExchangeUser(String recordsExchangeUser) {
        this.recordsExchangeUser = recordsExchangeUser;
    }

    public String getRecordsTime() {
        return recordsTime;
    }

    public void setRecordsTime(String recordsTime) {
        this.recordsTime = recordsTime;
    }
}
