package com.bw.net.mode;

import java.util.List;

public class InvestBean {

    /**
     * code : 200
     * msg : 请求成功
     * result : [{"id":"1","name":"新手福利计划","money":"10","yearRate":"8.00","suodingDays":"30","minTouMoney":"100","memberNum":"100","progress":"50"},{"id":"2","name":"财神道90天计划","money":"20","yearRate":"8.00","suodingDays":"40","minTouMoney":"1000","memberNum":"100","progress":"60"}]
     */

    private int code;
    private String msg;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 1
         * name : 新手福利计划
         * money : 10
         * yearRate : 8.00
         * suodingDays : 30
         * minTouMoney : 100
         * memberNum : 100
         * progress : 50
         */

        private String id;
        private String name;
        private String money;
        private String yearRate;
        private String suodingDays;
        private String minTouMoney;
        private String memberNum;
        private String progress;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getYearRate() {
            return yearRate;
        }

        public void setYearRate(String yearRate) {
            this.yearRate = yearRate;
        }

        public String getSuodingDays() {
            return suodingDays;
        }

        public void setSuodingDays(String suodingDays) {
            this.suodingDays = suodingDays;
        }

        public String getMinTouMoney() {
            return minTouMoney;
        }

        public void setMinTouMoney(String minTouMoney) {
            this.minTouMoney = minTouMoney;
        }

        public String getMemberNum() {
            return memberNum;
        }

        public void setMemberNum(String memberNum) {
            this.memberNum = memberNum;
        }

        public String getProgress() {
            return progress;
        }

        public void setProgress(String progress) {
            this.progress = progress;
        }
    }
}
