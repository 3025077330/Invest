package com.bw.framwork.base;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.bw.framwork.R;
import com.bw.framwork.view.ToolBar;


public abstract class BaseActivity extends AppCompatActivity implements ToolBar.IToolBarClickListner {
    private String TAG;
    protected ToolBar toolbar;


    /**
     * 网络类型
     */
    private int netMobile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = "WYF" + getClass().getSimpleName();
        setContentView(bandlayout());
        toolbar = findViewById(R.id.toolbar);//在这里实例化toolbar
        toolbar.setToolBarClickListner(this);
        initview();
        initdata();
    }


    protected abstract int bandlayout();

    protected abstract void initview();

    protected abstract void initdata();



    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netMobile == 1) {
            return true;
        } else if (netMobile == 0) {
            return true;
        } else if (netMobile == -1) {
            return false;
        }
        return false;

    }


    protected void showMsg(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void printLog(String message) {
        if (TAG == null) {
            TAG = "WYF" + getClass().getSimpleName();
        }
        Log.i(TAG, message);
    }

    protected void launchActivity(Class launchActivity, Bundle bundle) {
        Intent intent = new Intent();
        if (bundle == null) {
            bundle = new Bundle();
        }
        intent.setClass(this, launchActivity);
        intent.putExtra("BUNDEL", bundle);
        startActivity(intent);
    }

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    public void onRightClick() {

    }



}
