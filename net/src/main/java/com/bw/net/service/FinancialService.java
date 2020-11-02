package com.bw.net.service;


import com.bw.net.BaseBean;
import com.bw.net.mode.HomeBean;
import com.bw.net.mode.InvestBean;
import com.bw.net.mode.LoginBean;
import com.bw.net.mode.MoneyBean;
import com.bw.net.mode.RegisterBean;
import com.bw.net.mode.UpLoadBean;
import com.bw.net.mode.VersionBean;

import java.util.HashMap;


import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface FinancialService {
    //注册
    @POST("register")
    @FormUrlEncoded
    Observable<RegisterBean> register(@FieldMap HashMap<String, String> hashMap);

    //登录
    @POST("login")
    @FormUrlEncoded
    Observable<LoginBean> login(@FieldMap HashMap<String, String> hashMap);

    //自动登录
    @POST("autoLogin")
    @FormUrlEncoded
    Observable<LoginBean> autoLogin(@FieldMap HashMap<String, String> params);

    //获取产品最新信息
    @GET("atguigu/json/P2PInvest/update.json")
    Observable<VersionBean> updateVersion();

    //首页数据
    @GET("atguigu/json/P2PInvest/index.json")
    Observable<HomeBean> gethomedata();


    @GET("atguigu/json/P2PInvest/product.json")
    Observable<InvestBean> getInvest();

    //下载APK文件
    @GET("atguigu/apk/P2PInvest/app_new.apk")
    Observable<ResponseBody> downloadAPK();


    //定义下载文件
    @GET
    @Streaming
    Observable<ResponseBody> downloadFile(@Url String url);

    //上传程序报错信息
    @POST("crash")
    @FormUrlEncoded
    Observable<String> crashReport(@FieldMap HashMap<String, String> params);


    @POST("updateMoney")
    @FormUrlEncoded
    Observable<MoneyBean> updateMoney(@FieldMap HashMap<String, String> hashMap);


    //上传文件
    @Multipart
    @POST("upload")
    Observable<UpLoadBean> uploadOneFile(@Part("file\"; filename=\"avatar.jpg") RequestBody file);


}
