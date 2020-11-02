package com.bw.framwork.base;


import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bw.net.manager.NetStatusManager;


public abstract class BaseMVPActivity<T extends IPresenter, V extends IView> extends BaseActivity implements NetStatusManager.NetStatusListener {
    protected T ihttpView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
        ihttpView.attachView((IView) this);
        NetStatusManager.getInstance().addListener(this);
        initPresenterData();
    }


    protected abstract void initPresenter();

    protected abstract void initPresenterData();

    @Override
    protected void onDestroy() {
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
