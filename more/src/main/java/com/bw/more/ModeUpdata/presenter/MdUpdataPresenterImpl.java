package com.bw.more.ModeUpdata.presenter;

import com.bw.more.ModeUpdata.contract.MdUpdataContract;
import com.bw.net.BaseBean;
import com.bw.net.NetFunction;
import com.bw.net.manager.RetrofitManager;
import com.bw.net.mode.InvestBean;
import com.bw.net.mode.VersionBean;
import com.bw.net.observer.FinanCilalObserver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public
class MdUpdataPresenterImpl extends MdUpdataContract.HomePresenter {
    @Override
    public void getvision() {
        RetrofitManager.getFinancialService().updateVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                }).subscribe(new FinanCilalObserver<VersionBean>() {
            @Override
            public void onNext(VersionBean versionBean) {
                ihttpView.IVersion(versionBean);
            }

            @Override
            public void onRequestError(String errorCode, String errorMessage) {
                ihttpView.showError(errorCode, errorMessage);
            }
        });
    }
}
