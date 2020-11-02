package com.bw.net.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.List;

public class NetStatusManager extends BroadcastReceiver {

    private boolean isConnected = false;
    private List<NetStatusListener> netStatusListenerList = new ArrayList<>();
    private static NetStatusManager instance;

    private NetStatusManager() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            //获取网络连接
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            isConnected = networkInfo.isConnected();
            //网络发生变化
            for (NetStatusListener listener : netStatusListenerList) {
                listener.onNetChange(isConnected);
            }
        }
    }

    public static NetStatusManager getInstance() {
        if (instance == null) {
            synchronized (NetStatusManager.class) {
                if (instance == null) {
                    instance = new NetStatusManager();
                }
            }
        }
        return instance;
    }

    //直接获取状态
    public boolean isConnected() {
        return isConnected;
    }

    public void init(Context context) {
        //获取网络连接
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = networkInfo.isConnected();
        //一旦发生改变 就发送广播监听
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(this, intentFilter);
    }

    public void addListener(NetStatusListener netStatusListener) {
        if (!netStatusListenerList.contains(netStatusListener)) {
            netStatusListenerList.add(netStatusListener);
        }
    }

    public void removeListener(NetStatusListener netStatusListener) {
        if (netStatusListenerList.contains(netStatusListener)) {
            netStatusListenerList.remove(netStatusListener);
        }
    }


    public interface NetStatusListener {
        void onNetChange(boolean isConnected);
    }
}
