package com.bw.net.observer;


import com.bw.common.error.FinanCilalError;
import com.bw.net.NetBusinessException;

import org.json.JSONException;

import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

//自定义ObServer  只强制回显错误和成功
public abstract class FinanCilalObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public abstract void onNext(T t);

    @Override
    public void onError(Throwable e) {
        //错误类型判断
        if (e instanceof JSONException) {
            onRequestError(FinanCilalError.JSCON_ERROR_CODE, FinanCilalError.JSON_ERROR_MESSAGE);
        } else if (e instanceof HttpException) {
            onRequestError(FinanCilalError.HTTP_ERROR_CODE, FinanCilalError.HTTP_ERROR_MESSAGE);
        } else if (e instanceof SocketTimeoutException) {
            onRequestError(FinanCilalError.SOCKET_TIMEOUT_ERROR_CODE, FinanCilalError.SOCKET_TIMEOUT_ERROR_MESSAGE);
        } else if (e instanceof NetBusinessException) {
            NetBusinessException netBusinessException = (NetBusinessException) e;
            onRequestError(netBusinessException.getErrorCode(), netBusinessException.getErrorMessage());
        } else if (e instanceof SecurityException) {

        }
    }

    @Override
    public void onComplete() {
    }

    public abstract void onRequestError(String errorCode, String errorMessage);
}
