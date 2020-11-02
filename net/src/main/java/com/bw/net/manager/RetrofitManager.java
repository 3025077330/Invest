package com.bw.net.manager;


import com.bw.common.FinanCilalConstant;
import com.bw.net.Interceptor.CustomInter;
import com.bw.net.service.FinancialService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private RetrofitManager() {
    }

    private static FinancialService financialService;

    public static FinancialService getFinancialService() {
        if (financialService == null) {
            synchronized (RetrofitManager.class) {
                if (financialService == null) {
                    financialService = create();
                }
            }
        }
        return financialService;
    }

    private static FinancialService create() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES)
                .addInterceptor(new CustomInter())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(FinanCilalConstant.ENCRYBaseUr)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(FinancialService.class);
    }

}
