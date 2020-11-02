package com.bw.net.Interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import com.bw.common.FinanCilalConstant;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.bw.net.NetModule.context;

public class CustomInter implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Context contex = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences(FinanCilalConstant.spName, Context.MODE_PRIVATE);
        Request request = chain.request();
        Request newRequest = request.newBuilder().addHeader("token", sharedPreferences.getString(FinanCilalConstant.tokenName, "")).build();
        return chain.proceed(newRequest);
    }
}
