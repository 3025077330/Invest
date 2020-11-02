package com.bw.invest.allinvest.presenter;

import com.bw.invest.allinvest.contract.AllInvestContract;
import com.bw.net.BaseBean;
import com.bw.net.NetFunction;
import com.bw.net.manager.RetrofitManager;
import com.bw.net.mode.InvestBean;
import com.bw.net.observer.FinanCilalObserver;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AllInvestPresenterImpl extends AllInvestContract.AllInVestPresenter {
    @Override
    public void getIvset() {
        RetrofitManager.getFinancialService()
                .getInvest()
                .delay(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        ihttpView.showLoading();
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        ihttpView.hideLoading();
                    }
                })
                .subscribe(new FinanCilalObserver<InvestBean>() {
                    @Override
                    public void onNext(InvestBean investBean) {
                        ihttpView.onIvset(investBean);
                    }

                    @Override
                    public void onRequestError(String errorCode, String errorMessage) {
                        ihttpView.showError(errorCode, errorMessage);
                    }
                });
    }
}
