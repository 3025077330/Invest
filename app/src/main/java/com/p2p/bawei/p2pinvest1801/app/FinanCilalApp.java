package com.p2p.bawei.p2pinvest1801.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bw.common.cache.CachManager;


import com.bw.framwork.glide.GlideUntils;
import com.bw.invest.util.UIUtils;
import com.bw.net.manager.NetStatusManager;
import com.bw.user.manager.InvestUserManager;
import com.bw.net.NetModule;

public class FinanCilalApp extends Application {
    public static FinanCilalApp instance;
    //在整个应用执行过程中，需要提供的变量


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        NetModule.init(instance);
        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(instance);
        InvestUserManager.getInstance().init(instance);
        CachManager.getInstance().init(instance);
        GlideUntils.getInstance().init(instance);
        //CrashHandler.getInstance().init(instance);
        NetStatusManager.getInstance().init(instance);
//        //使用leakcannary来检测当前应用是否有页面内存泄漏.
////        if (!LeakCanary.isInAnalyzerProcess(instance)) {
////            //refWatcher = LeakCanary.install(this);
////        }
        UIUtils.mcontext = instance;
        UIUtils.handler = new Handler();
        UIUtils.mainThread = Thread.currentThread();

    }
}
