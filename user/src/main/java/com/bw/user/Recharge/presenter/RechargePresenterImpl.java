package com.bw.user.Recharge.presenter;

import com.bw.net.manager.RetrofitManager;
import com.bw.net.mode.MoneyBean;
import com.bw.user.Recharge.contract.RechargeContract;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RechargePresenterImpl extends RechargeContract.RechargePresenter {

    @Override
    public void UpDateMoney(String money) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("money", money);
        RetrofitManager.getFinancialService().updateMoney(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MoneyBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MoneyBean moneyBean) {
                        ihttpView.OnMoney(moneyBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
