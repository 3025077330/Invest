package com.bw.common.cache;

public class BannerBean {
    private String ID;
    private String IMAPAURL;
    private String IMAURL;

    public BannerBean(String ID, String IMAPAURL, String IMAURL) {
        this.ID = ID;
        this.IMAPAURL = IMAPAURL;
        this.IMAURL = IMAURL;
    }

    public BannerBean() {
    }

    @Override
    public String toString() {
        return "BannerBean{" +
                "ID='" + ID + '\'' +
                ", IMAPAURL='" + IMAPAURL + '\'' +
                ", IMAURL='" + IMAURL + '\'' +
                '}';
    }

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
}
