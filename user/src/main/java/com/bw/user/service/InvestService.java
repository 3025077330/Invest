package com.bw.user.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.bw.net.BaseBean;
import com.bw.net.NetFunction;
import com.bw.net.observer.FinanCilalObserver;
import com.bw.user.manager.InvestUserManager;
import com.bw.net.manager.RetrofitManager;
import com.bw.net.mode.LoginBean;


import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class InvestService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new InvestUserBinder();
    }

    public class InvestUserBinder extends Binder {
        public InvestService getService() {
            return InvestService.this;
        }
    }

    public void autoLogin(String tokenValue) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token", tokenValue);
        RetrofitManager.getFinancialService().autoLogin(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                }).subscribe(new FinanCilalObserver<LoginBean>() {

            @Override
            public void onNext(LoginBean loginBean) {
                InvestUserManager.getInstance().setLoginBean(loginBean);
            }

            @Override
            public void onRequestError(String errorCode, String errorMessage) {

            }
        });

    }


}
