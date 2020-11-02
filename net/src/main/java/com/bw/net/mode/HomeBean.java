package com.bw.net.mode;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class HomeBean  implements Parcelable {


    /**
     * code : 200
     * msg : 请求成功
     * result : {"imageArr":[{"ID":"1","IMAPAURL":"http://fund.eastmoney.com/f10/jjjl_002939.html","IMAURL":"http://49.233.93.155:8080/atguigu/img/P2PInvest/index01.png"},{"ID":"2","IMAPAURL":"http://fund.eastmoney.com/519983.html","IMAURL":"http://49.233.93.155:8080/atguigu/img/P2PInvest/index02.png"},{"ID":"3","IMAPAURL":"http://www.gffunds.com.cn/funds/?fundcode=004997","IMAURL":"http://49.233.93.155:8080/atguigu/img/P2PInvest/index03.png"},{"ID":"5","IMAPAURL":"http://fund.eastmoney.com/002939.html","IMAURL":"http://49.233.93.155:8080/atguigu/img/P2PInvest/index04.png"}],"proInfo":{"id":"1","memberNum":"100","minTouMoney":"100","money":"10","name":"硅谷彩虹新手计划","progress":"90","suodingDays":"30","yearRate":"8.00"}}
     */

    private int code;
    private String msg;
    private ResultBean result;

    protected HomeBean(Parcel in) {
        code = in.readInt();
        msg = in.readString();
        result = in.readParcelable(ResultBean.class.getClassLoader());
    }

    public static final Creator<HomeBean> CREATOR = new Creator<HomeBean>() {
        @Override
        public HomeBean createFromParcel(Parcel in) {
            return new HomeBean(in);
        }

        @Override
        public HomeBean[] newArray(int size) {
            return new HomeBean[size];
        }
    };

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeParcelable(result, flags);
    }

    public static class ResultBean implements Parcelable{
        /**
         * imageArr : [{"ID":"1","IMAPAURL":"http://fund.eastmoney.com/f10/jjjl_002939.html","IMAURL":"http://49.233.93.155:8080/atguigu/img/P2PInvest/index01.png"},{"ID":"2","IMAPAURL":"http://fund.eastmoney.com/519983.html","IMAURL":"http://49.233.93.155:8080/atguigu/img/P2PInvest/index02.png"},{"ID":"3","IMAPAURL":"http://www.gffunds.com.cn/funds/?fundcode=004997","IMAURL":"http://49.233.93.155:8080/atguigu/img/P2PInvest/index03.png"},{"ID":"5","IMAPAURL":"http://fund.eastmoney.com/002939.html","IMAURL":"http://49.233.93.155:8080/atguigu/img/P2PInvest/index04.png"}]
         * proInfo : {"id":"1","memberNum":"100","minTouMoney":"100","money":"10","name":"硅谷彩虹新手计划","progress":"90","suodingDays":"30","yearRate":"8.00"}
         */

        private ProInfoBean proInfo;
        private List<ImageArrBean> imageArr;

        protected ResultBean(Parcel in) {
        }

        public static final Creator<ResultBean> CREATOR = new Creator<ResultBean>() {
            @Override
            public ResultBean createFromParcel(Parcel in) {
                return new ResultBean(in);
            }

            @Override
            public ResultBean[] newArray(int size) {
                return new ResultBean[size];
            }
        };

        public ProInfoBean getProInfo() {
            return proInfo;
        }

        public void setProInfo(ProInfoBean proInfo) {
            this.proInfo = proInfo;
        }

        public List<ImageArrBean> getImageArr() {
            return imageArr;
        }

        public void setImageArr(List<ImageArrBean> imageArr) {
            this.imageArr = imageArr;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }

        public static class ProInfoBean implements  Parcelable {
            /**
             * id : 1
             * memberNum : 100
             * minTouMoney : 100
             * money : 10
             * name : 硅谷彩虹新手计划
             * progress : 90
             * suodingDays : 30
             * yearRate : 8.00
             */

            private String id;
            private String memberNum;
            private String minTouMoney;
            private String money;
            private String name;
            private String progress;
            private String suodingDays;
            private String yearRate;

            protected ProInfoBean(Parcel in) {
                id = in.readString();
                memberNum = in.readString();
                minTouMoney = in.readString();
                money = in.readString();
                name = in.readString();
                progress = in.readString();
                suodingDays = in.readString();
                yearRate = in.readString();
            }

            public static final Creator<ProInfoBean> CREATOR = new Creator<ProInfoBean>() {
                @Override
                public ProInfoBean createFromParcel(Parcel in) {
                    return new ProInfoBean(in);
                }

                @Override
                public ProInfoBean[] newArray(int size) {
                    return new ProInfoBean[size];
                }
            };

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMemberNum() {
                return memberNum;
            }

            public void setMemberNum(String memberNum) {
                this.memberNum = memberNum;
            }

            public String getMinTouMoney() {
                return minTouMoney;
            }

            public void setMinTouMoney(String minTouMoney) {
                this.minTouMoney = minTouMoney;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getProgress() {
                return progress;
            }

            public void setProgress(String progress) {
                this.progress = progress;
            }

            public String getSuodingDays() {
                return suodingDays;
            }

            public void setSuodingDays(String suodingDays) {
                this.suodingDays = suodingDays;
            }

            public String getYearRate() {
                return yearRate;
            }

            public void setYearRate(String yearRate) {
                this.yearRate = yearRate;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(id);
                dest.writeString(memberNum);
                dest.writeString(minTouMoney);
                dest.writeString(money);
                dest.writeString(name);
                dest.writeString(progress);
                dest.writeString(suodingDays);
                dest.writeString(yearRate);
            }
        }

        public static class ImageArrBean implements  Parcelable {
            /**
             * ID : 1
             * IMAPAURL : http://fund.eastmoney.com/f10/jjjl_002939.html
             * IMAURL : http://49.233.93.155:8080/atguigu/img/P2PInvest/index01.png
             */

            private String ID;
            private String IMAPAURL;
            private String IMAURL;

            protected ImageArrBean(Parcel in) {
                ID = in.readString();
                IMAPAURL = in.readString();
                IMAURL = in.readString();
            }

            public static final Creator<ImageArrBean> CREATOR = new Creator<ImageArrBean>() {
                @Override
                public ImageArrBean createFromParcel(Parcel in) {
                    return new ImageArrBean(in);
                }

                @Override
                public ImageArrBean[] newArray(int size) {
                    return new ImageArrBean[size];
                }
            };

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getIMAPAURL() {
                return IMAPAURL;
            }

            public void setIMAPAURL(String IMAPAURL) {
                this.IMAPAURL = IMAPAURL;
            }

            public String getIMAURL() {
                return IMAURL;
            }

            public void setIMAURL(String IMAURL) {
                this.IMAURL = IMAURL;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(ID);
                dest.writeString(IMAPAURL);
                dest.writeString(IMAURL);
            }
        }
    }
}
