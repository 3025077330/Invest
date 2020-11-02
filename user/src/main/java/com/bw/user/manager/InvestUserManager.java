package com.bw.user.manager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.text.TextUtils;

import com.bw.user.service.InvestService;
import com.bw.net.mode.LoginBean;


import java.util.LinkedList;
import java.util.List;

public class InvestUserManager {
    private static InvestUserManager investUserManager;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String investSp = "investSp";
    private String tokenName = "tokenSp";
    private LoginBean loginBean;
    private Context context;
    private List<ILoginStatusChangeListener> iLoginStatusChangeListeners = new LinkedList<>();

    private InvestUserManager() {
    }

    public Integer getUserMoney() {
        if (loginBean != null) {
            if (loginBean.getResult().getMoney() == null) {
                return 0;
            }

            String strmoney = (String) loginBean.getResult().getMoney();
            return Integer.parseInt(strmoney);
        }
        return 0;
    }

    public static InvestUserManager getInstance() {
        if (investUserManager == null) {
            synchronized (InvestUserManager.class) {
                if (investUserManager == null) {
                    investUserManager = new InvestUserManager();
                }
            }
        }
        return investUserManager;
    }

    public void init(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(investSp, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Intent intent = new Intent();
        intent.setClass(context, InvestService.class);
        context.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                InvestService.InvestUserBinder investUserBinder = (InvestService.InvestUserBinder) service;
                if (!TextUtils.isEmpty(getToken())) {
                    investUserBinder.getService().autoLogin(getToken());
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);
    }

    public String getToken() {
        return sharedPreferences.getString(tokenName, "");
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
        if (iLoginStatusChangeListeners.size() > 0) {
            for (ILoginStatusChangeListener listener : iLoginStatusChangeListeners) {
                listener.onLoginSuccess(loginBean);
            }
        }
        editor.putString(tokenName, loginBean.getResult().getToken());
        editor.commit();
    }

    public String getName() {
        if (loginBean != null) {
            return loginBean.getResult().getName();
        }
        return null;
    }

    public void setUserMoney(String money) {

    }


    public String getUserImage() {
        if (loginBean != null) {
            return (String) loginBean.getResult().getAvatar();
        }
        return null;
    }


    public boolean isUserLogin() {
        return loginBean != null;
    }

    public void processLogOut() {
        this.loginBean = null;
        editor.putString(tokenName, "");
        editor.commit();

        if (iLoginStatusChangeListeners.size() > 0) {
            for (ILoginStatusChangeListener listener : iLoginStatusChangeListeners) {
                listener.onLogoutSuccess();
            }
        }

    }



    public interface ILoginStatusChangeListener {
        void onLoginSuccess(LoginBean loginBean);

        void onLogoutSuccess();
    }
}
