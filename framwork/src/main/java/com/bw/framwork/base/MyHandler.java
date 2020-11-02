package com.bw.framwork.base;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;

public class MyHandler<T> extends Handler {
    private WeakReference<T>  tWeakReference;



    @Override

    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
    }
}
