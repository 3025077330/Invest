package com.bw.more.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import com.bw.framwork.base.BaseMVPFragment;
import com.bw.more.Dialog.FeedbackDiaLog;
import com.bw.more.ModeUpdata.contract.MdUpdataContract;
import com.bw.more.ModeUpdata.presenter.MdUpdataPresenterImpl;
import com.bw.more.R;
import com.bw.net.mode.VersionBean;

import java.io.File;

public class ModeUpdataFragment extends BaseMVPFragment<MdUpdataPresenterImpl, MdUpdataContract.MdUpdataView> implements MdUpdataContract.MdUpdataView, View.OnClickListener {

    private FeedbackDiaLog feedbackDiaLog;

    @Override
    protected void initPresenter() {
        ihttpView = new MdUpdataPresenterImpl();
    }

    @Override
    protected void initPresenterData() {

    }

    @Override
    public void onconnect() {

    }

    @Override
    public void noconnect() {

    }

    @Override
    protected int bandlayout() {
        return R.layout.mdupdafragment;
    }

    @Override
    protected void initview() {

        findViewById(R.id.examine_version).setOnClickListener(this);
        findViewById(R.id.fun_msg).setOnClickListener(this);
        findViewById(R.id.feek).setOnClickListener(this);
    }

    @Override
    protected void initdata() {

    }

    @Override
    public void IVersion(VersionBean versionBean) {
        if (versionBean.getCode() == 200) {
            float newversion = Float.parseFloat(versionBean.getResult().getVersion());
            if (newversion > 1.1f) {
                //用新版本可用  提示用户更新
                showDialog(versionBean);
            }
        }
    }

    private void showDialog(VersionBean versionBean) {
        final File file = new File("/storage/emulated/0/Android/data/com.p2p.bawei.p2pinvest1801/cache/files", "app-debug.apk");
        if (file.exists()) {
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
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
                }
            });
            alert.setCancelable(false);
            alert.create().show();
            return;
        }
        //展示Dialog
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("下载最新版本:" + versionBean.getResult().getVersion());
        alertDialog.setMessage(versionBean.getResult().getDesc());
        alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.setCancelable(false);
        alertDialog.create().show();
    }


    private void installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= 24) {
            Uri apkUri = FileProvider.getUriForFile(getContext(), "com.p2p.bawei.p2pinvest1801", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        startActivity(intent);
        System.exit(0);//强制退出该程序
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

    private void showFeekDialog() {
        if (getContext() != null && getActivity() != null) {
            feedbackDiaLog = new FeedbackDiaLog(getContext());
            feedbackDiaLog.create();
            feedbackDiaLog.show();
            if (feedbackDiaLog.getWindow() != null) {
                final WindowManager.LayoutParams params = feedbackDiaLog.getWindow().getAttributes();
                Point point = new Point();
                Display display = getActivity().getWindowManager().getDefaultDisplay();
                display.getSize(point);
                params.width = (point.x);
                params.height = (int) (point.y * 0.34);
                feedbackDiaLog.getWindow().setAttributes(params);
            }
            feedbackDiaLog.findViewById(R.id.feek_sure).setOnClickListener(this);
            feedbackDiaLog.findViewById(R.id.feek_off).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.examine_version) {
            //检查最新版本
            ihttpView.getvision();
        } else if (v.getId() == R.id.feek) {
            //开启投诉popwindow
            showFeekDialog();
        } else if (v.getId() == R.id.feek_sure) {
            showMsg("投诉成功");
            feedbackDiaLog.cancel();
        } else if (v.getId() == R.id.feek_off) {
            feedbackDiaLog.cancel();
        }
    }
}
