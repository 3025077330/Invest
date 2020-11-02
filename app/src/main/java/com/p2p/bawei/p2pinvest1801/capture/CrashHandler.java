package com.p2p.bawei.p2pinvest1801.capture;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bw.net.NetFunction;
import com.bw.net.manager.RetrofitManager;
import com.bw.net.BaseBean;
import com.p2p.bawei.p2pinvest1801.app.FinanCilalApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private String crashPath;
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    public static final String TAG = "CrashHandler";
    private static CrashHandler instance;

    /**
     * 保证只有一个CrashHandler实例
     */

    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        if (!handleException(e) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(t, e);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
                Log.e(TAG, "error : ", e1);
            }
//            Intent intent = new Intent();
//            intent.setClass(FinanCilalApp.instance, WelcomeActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            FinanCilalApp.instance.startActivity(intent);
            //退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    private CrashHandler() {
    }


    public static CrashHandler getInstance() {
        if (instance == null) {
            synchronized (CrashHandler.class) {
                if (instance == null) {
                    instance = new CrashHandler();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化
     */
    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        //存储异常报告的目录
        crashPath = FinanCilalApp.instance.getExternalCacheDir() + "/crash/";
        File file = new File(crashPath);
        if (!file.exists()) {
            file.mkdir();
        }

    }


    private void crashReportToServer(Throwable e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        HashMap<String, String> params = new HashMap<>();
        params.put("message", "王亚富" + collectDeviceInfo(mContext) + "错误信息" + stringWriter.toString());
        RetrofitManager.getFinancialService().crashReport(params)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        //收集设备参数信息
        collectDeviceInfo(mContext);
        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        //保存日志文件
        saveCatchInfo2File(ex);
        //   crashReportToServer(ex);
        return true;
    }


    /**
     * 保存错误信息到文件中
     *
     * @param e
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private void saveCatchInfo2File(Throwable e) {
        //将异常信息放到一个输出流里
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);

        //生成一个文件名
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = simpleDateFormat.format(new Date());
        File crashFile = new File(crashPath + timeStr + ".txt");

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(crashFile);
            String crashStr = stringWriter.toString();
            byte[] crashByteArray = crashStr.getBytes();
            int length = crashByteArray.length;
            try {
                String s = collectDeviceInfo(mContext);
                fileOutputStream.write(s.getBytes());
                fileOutputStream.write(crashByteArray, 0, length);
                fileOutputStream.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                try {
                    fileOutputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }


    }


    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public String collectDeviceInfo(Context ctx) {
        StringBuffer sb = new StringBuffer();
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                sb.append("设备名称" + field.getName() + "设备名称" + field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
                return sb.toString();
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
        return null;
    }


}
