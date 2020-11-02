package com.bw.user.register.presenter;

import android.util.Log;

import com.bw.common.util.EncryptUtil;
import com.bw.net.NetFunction;
import com.bw.net.manager.RetrofitManager;
import com.bw.net.BaseBean;
import com.bw.net.mode.RegisterBean;
import com.bw.net.observer.FinanCilalObserver;
import com.bw.user.register.contract.RegisterContract;

import java.util.HashMap;
import java.util.TreeMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class RegisterPresenterImpl extends RegisterContract.RegisterPresenter {





    @Override
    public void register(String name, String password) {

        //第一步生成签名,保证数据的完整性
        //先对参数进行排序，按照key的升序拼接成一个字符串(例如"name=666666&password=666"),利用TreeMap数据结构，TreeMap在遍历数据时，默认是按照key的升序遍历的。我们也可以配置TreeMap让它按照key
        //的降序遍历
        TreeMap<String, String> params = new TreeMap<>();
        params.put("name", name);
        params.put("password", password);
        StringBuilder sb = new StringBuilder();
        for (String key : params.keySet()) {
            String value = params.get(key);
            sb.append(key + "=" + value + "&");
        }
        sb.append("encrypt=md5");
        Log.d("LQS", sb.toString());
        //用md5生成签名
        String sign = EncryptUtil.enrypByMd5(sb.toString());
        params.put("sign", sign);

        //第二步对参数的value进行编码加密，防止明文传输
        TreeMap<String, String> encryptedMap = EncryptUtil.encryptParamsValueByBase64(params);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", name);
        hashMap.put("password", password);
        RetrofitManager.getFinancialService().register(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                })
                .subscribe(new FinanCilalObserver<RegisterBean>() {
                    @Override
                    public void onNext(RegisterBean registerBean) {
                        ihttpView.onregister(registerBean);
                    }


                    @Override
                    public void onRequestError(String errorCode, String errorMessage) {
                        ihttpView.showError(errorCode, errorMessage);
                    }
                });
    }
}
