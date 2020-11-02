package com.p2p.bawei.p2pinvest1801.home.presenter;

import com.bw.net.BaseBean;
import com.bw.net.NetFunction;
import com.bw.net.manager.RetrofitManager;
import com.bw.net.mode.HomeBean;
import com.bw.net.mode.VersionBean;
import com.bw.net.observer.FinanCilalObserver;
import com.p2p.bawei.p2pinvest1801.home.contract.HomeContract;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomePresenterImpl extends HomeContract.HomePresenter {

    @Override
    public void gethome() {
        RetrofitManager.getFinancialService().gethomedata()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FinanCilalObserver<HomeBean>() {
                    @Override
                    public void onNext(HomeBean homeBean) {
                        ihttpView.IHomeView(homeBean);
                    }

                    @Override
                    public void onRequestError(String errorCode, String errorMessage) {
                        ihttpView.showError(errorCode, errorMessage);
                    }
                });


    }


    @Override
    public void getvision() {
        RetrofitManager.getFinancialService().updateVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FinanCilalObserver<VersionBean>() {
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
