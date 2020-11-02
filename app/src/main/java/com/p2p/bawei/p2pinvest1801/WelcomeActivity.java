package com.p2p.bawei.p2pinvest1801;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;


import com.alibaba.android.arouter.launcher.ARouter;
import com.bw.common.FinanCilalConstant;
import com.bw.common.cache.BannerBean;
import com.bw.common.cache.CachManager;
import com.bw.framwork.base.BaseMVPActivity;
import com.bw.net.mode.HomeBean;
import com.bw.net.mode.VersionBean;
import com.bw.net.service.DownloadService;
import com.p2p.bawei.p2pinvest1801.app.FinanCilalApp;
import com.p2p.bawei.p2pinvest1801.home.contract.HomeContract;
import com.p2p.bawei.p2pinvest1801.home.presenter.HomePresenterImpl;
import com.p2p.bawei.p2pinvest1801.view.DoloadDialog;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class WelcomeActivity extends BaseMVPActivity<HomePresenterImpl, HomeContract.IHomeView> implements HomeContract.IHomeView, CachManager.AddCallBack, CachManager.DelCallBack {
    private TextView tvCount;
    private ArrayList<BannerBean> bannerlist = new ArrayList<>();
    private int handlerCount = 0;
    private Timer timer;
    private int count = 5;
    private VersionBean versionBean;
    private final static int DataRequest = 1001;
    private final MyHandler myHandler = new MyHandler(this);
    private ImageView wecommimage;

    private void ShowDialog() {
        //判断更新方式   -》强制，非强制
        if (versionBean.getResult().isForce()) {
            //需要强制更新
            //展示Dialog
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("下载最新版本");
            alertDialog.setMessage(versionBean.getResult().getDesc());
            alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showDownDialog();
                }
            });
            alertDialog.setCancelable(false);
            alertDialog.create().show();
            return;
        }
        //展示Dialog
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("下载最新版本");
        alertDialog.setMessage(versionBean.getResult().getDesc());
        alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showDownDialog();
            }
        });
        alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timer.cancel();
                        launchActivity(MainActivity.class, null);
                        finish();
                    }
                });
            }
        });
        alertDialog.setCancelable(false);
        alertDialog.create().show();
    }

    private ProgressBar downloadProgress;
    private TextView planCount;
    private TextView tvNowcount;
    private TextView tvFilesize;

    private void showDownDialog() {
        DoloadDialog doloadDialog = new DoloadDialog(this);
        doloadDialog.setCancelable(false);
        doloadDialog.show();
        WindowManager.LayoutParams params = doloadDialog.getWindow().getAttributes();
        Point point = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(point);
        params.width = (point.x);
        params.height = (int) (point.y * 0.15);
        doloadDialog.getWindow().setAttributes(params);
        downloadProgress = doloadDialog.findViewById(R.id.download_progress);
        planCount = doloadDialog.findViewById(R.id.plan_count);
        tvNowcount = doloadDialog.findViewById(R.id.tv_nowcount);
        tvFilesize = doloadDialog.findViewById(R.id.tv_filesize);
        downloadapk();//下载APK
    }


    private void downloadapk() {
        //初始化数据要保持的位置
        final File filesDir;
        filesDir = new File(FinanCilalApp.instance.getExternalCacheDir() + "/files/");
        if (!filesDir.exists()) {
            filesDir.mkdir();
        }
        Intent intent = new Intent();
        intent.setClass(FinanCilalApp.instance, DownloadService.class);
        FinanCilalApp.instance.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(final ComponentName name, IBinder service) {
                DownloadService.DownloadBinder investUserBinder = (DownloadService.DownloadBinder) service;
                investUserBinder.getSerice().download(filesDir.getPath(), new DownloadService.OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess(final File file) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                installApk(file);
                            }
                        });
                    }

                    @Override
                    public void onDownloading(final int progress) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                downloadProgress.setProgress(progress);
                                planCount.setText(progress + "%");
                            }
                        });


                    }

                    @Override
                    public void onDownloadingnum(final long num) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvNowcount.setText(num + "");
                            }
                        });


                    }

                    @Override
                    public void onFileLength(final long length) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvFilesize.setText("/" + length);
                            }
                        });

                    }

                    @Override
                    public void onDownloadFailed() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(WelcomeActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);


    }

    //卸载APK
    private void deleteApl() {
        //通过程序的报名创建URI
        Uri packageURI = Uri.parse("com.p2p.bawei.p2pinvest1801");
        //创建Intent意图
        Intent intent = new Intent(Intent.ACTION_DELETE);
        //执行卸载程序
        startActivity(intent);
    }


    @Override
    protected void initPresenter() {
        ihttpView = new HomePresenterImpl();
    }

    @Override
    protected void initPresenterData() {
        ihttpView.getvision();
        ihttpView.gethome();
    }

    @Override
    protected int bandlayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initview() {
        wecommimage = (ImageView) findViewById(R.id.wecommimage);
        //开启动画
        startAlpha();
        initright();
        CachManager.getInstance().deleteBannerlist(this);
        ARouter.getInstance().inject(this);
        timer = new Timer();
        tvCount = findViewById(R.id.tv_count);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (count >= 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvCount.setText("倒计时" + count + "秒");
                            count--;
                        }
                    });

                } else {
                    myHandler.sendEmptyMessage(DataRequest);
                }
            }
        }, 0, 1000);

    }

    //渐变动画
    private void startAlpha() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(wecommimage, "alpha", 0, 1);
        animator.setDuration(1500);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }


    @Override
    protected void initdata() {

    }

    //获取当前版本
    private String getCurrVersion() {
        String version = "未知版本";
        PackageManager manager = getPackageManager();
        try {
            PackageInfo packageInfo = manager.getPackageInfo(getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(); //如果找不到对应的应用包信息, 就返回"未知版本"
        }
        return version;
    }

    //是否已经有新的APK存在
    private void newapkexist() {
        File file = new File(FinanCilalApp.instance.getExternalCacheDir() + "/files/", FinanCilalConstant.FineName);
        if (file.exists()) {
            showSureDialog(file);
        } else {
            ShowDialog();
        }
    }

    //如果已存在新的APK直接让用户选择是否安装
    private void showSureDialog(final File file) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("选择:");
        alert.setMessage("您已下载完成最新版APP，是否立即安装");
        alert.setPositiveButton("立即安装", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                installApk(file);
            }
        });
        alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showMsg("您正在使用旧版本");
                launchActivity(MainActivity.class, null);
            }
        });
        alert.setCancelable(false);
        alert.create().show();

    }

    //安装APK,考虑系统版本
    private void installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= 24) {
            Uri apkUri = FileProvider.getUriForFile(this, "com.p2p.bawei.p2pinvest1801", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        startActivity(intent);
        System.exit(0);//强制退出该程序
    }

    //进行缓存home页面
    @Override
    public void IHomeView(HomeBean homeBean) {
        printLog(homeBean.getResult().toString());
        if (homeBean != null) {
            if (homeBean.getCode() == 200) {
                List<HomeBean.ResultBean.ImageArrBean> imageArrBeans = homeBean.getResult().getImageArr();
                for (int i = 0; i < imageArrBeans.size(); i++) {
                    BannerBean bannerBean = new BannerBean();
                    bannerBean.setID(imageArrBeans.get(i).getID());
                    bannerBean.setIMAPAURL(imageArrBeans.get(i).getIMAPAURL());
                    bannerBean.setIMAURL(imageArrBeans.get(i).getIMAURL());
                    bannerlist.add(bannerBean);
                }
                CachManager.getInstance().addBannerList(bannerlist, this);
            }
        }

    }


    @Override
    public void IVersion(VersionBean versionBean) {
        if (versionBean != null) {
            if (versionBean.getCode() == 200) {
                this.versionBean = versionBean;
                myHandler.sendEmptyMessage(DataRequest);
            }
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String code, String msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandler.removeCallbacksAndMessages(null);

    }

    @Override
    public void onconnect() {

    }

    @Override
    public void noconnect() {

    }

    //数据库添加home数据缓存结果回调
    @Override
    public void onAdd() {
        myHandler.sendEmptyMessage(DataRequest);
    }

    //数据库删除home数据缓存结果回调
    @Override
    public void onDel() {
        myHandler.sendEmptyMessage(DataRequest);
    }

    private void initright() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{
                    "android.permission.ACCESS_COARSE_LOCATION",
                    "android.permission.INTERNET",
                    "android.permission.CHANGE_WIFI_STATE",
                    "android.permission.WRITE_EXTERNAL_STORAGE",
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.ACCESS_NETWORK_STATE",
                    "android.permission.SYSTEM_ALERT_WINDOW",
                    "android.permission.CAMERA",
                    "android.permission.MOUNT_UNMOUNT_FILESYSTEMS",
                    "android.permission.CALL_PHONE",
                    "android.permission.RECORD_AUDIO",
                    "android.permission.ACCESS_WIFI_STATE",
            }, 101);
        }
    }


    public static class MyHandler extends Handler {
        WeakReference<WelcomeActivity> welcomeActivityWeakReference;

        public MyHandler(WelcomeActivity activity) {
            welcomeActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            WelcomeActivity welcomeActivity = welcomeActivityWeakReference.get();
            if (welcomeActivity == null) {
                super.handleMessage(msg);
                return;
            }
            switch (msg.what) {
                case DataRequest:
                    welcomeActivity.handlerCount++;
                    if (welcomeActivity.handlerCount == 4) {
                        if (welcomeActivity.versionBean.getResult().getVersion() == null) {
                            welcomeActivity.newapkexist();
                            return;
                        }
                        if (welcomeActivity.getCurrVersion().equals(welcomeActivity.versionBean.getResult().getVersion())) {
                            welcomeActivity.launchActivity(MainActivity.class, null);
                            welcomeActivity.finish();
                        }
                        welcomeActivity.newapkexist();
                    }
                    break;
            }
        }
    }


}
