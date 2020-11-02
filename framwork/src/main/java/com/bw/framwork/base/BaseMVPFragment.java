package com.bw.framwork.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bw.net.manager.NetStatusManager;

public abstract class BaseMVPFragment<T extends IPresenter, V extends IView> extends BaseFragment implements NetStatusManager.NetStatusListener {
    protected T ihttpView;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        ihttpView.attachView((IView) this);
        NetStatusManager.getInstance().addListener(this);
        initPresenterData();
    }


    protected abstract void initPresenter();

    protected abstract void initPresenterData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        ihttpView.detachView();
        NetStatusManager.getInstance().removeListener(this);
    }

    @Override
    public void onNetChange(boolean isConnected) {
        if (isConnected) {
            onconnect();
        } else {
            noconnect();
        }
    }

    public abstract void onconnect();

    public abstract void noconnect();
}
