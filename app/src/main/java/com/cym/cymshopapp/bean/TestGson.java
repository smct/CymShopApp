package com.cym.cymshopapp.bean;

import java.util.List;

/**
 * ========================
 * CYM
 */
public class TestGson  {
    /**
     * code : 0
     * msg : 轮播会议获取成功
     * records : [{"joinNumber":3,"id":10,"startDateStr":"2016-10-26 09:00 星期三","theme":"2016年度海外高层次人群聚会"},{"joinNumber":3,"id":1,"startDateStr":"2016-10-24 08:00 星期一","theme":"2016年度苏州医疗会议"}]
     */

    private int code;
    private String msg;
    /**
     * joinNumber : 3
     * id : 10
     * startDateStr : 2016-10-26 09:00 星期三
     * theme : 2016年度海外高层次人群聚会
     */

    private List<RecordsBean> records;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean {
        private int joinNumber;
        private int id;
        private String startDateStr;
        private String theme;

        public int getJoinNumber() {
            return joinNumber;
        }

        public void setJoinNumber(int joinNumber) {
            this.joinNumber = joinNumber;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStartDateStr() {
            return startDateStr;
        }

        public void setStartDateStr(String startDateStr) {
            this.startDateStr = startDateStr;
        }

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }
    }
}
