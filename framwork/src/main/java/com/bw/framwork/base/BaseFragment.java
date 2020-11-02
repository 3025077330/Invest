package com.bw.framwork.base;


import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bw.framwork.R;
import com.bw.framwork.view.ToolBar;


public abstract class BaseFragment extends Fragment implements ToolBar.IToolBarClickListner {
    private String TAG;
    private View RootView;
    protected ToolBar toolbar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TAG = getClass().getSimpleName();
        RootView = inflater.inflate(bandlayout(), container,false);
        RootView.setFocusable(true);
        RootView.setFocusableInTouchMode(true);
        toolbar = findViewById(R.id.toolbar);//在这里实例化toolbar
        toolbar.setToolBarClickListner(this);
        return RootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initview();
        initdata();
    }

    protected abstract int bandlayout();

    protected abstract void initview();

    protected abstract void initdata();

    //注解。表示一个资源id，不能随便传递一个整型
    public <T extends View> T findViewById(@IdRes int id) {
        return RootView.findViewById(id);
    }

    protected void showMsg(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected void printLog(String message) {
        Log.i(TAG, message);
    }

    protected void launchActivity(Class launchActivity, Bundle bundle) {
        Intent intent = new Intent();
        if (bundle == null) {
            bundle = new Bundle();
        }
        if (getContext() != null) {
            intent.setClass(getContext(), launchActivity);
            intent.putExtra("BUNDEL", bundle);
            startActivity(intent);
        }

    }

    @Override
    public void onLeftClick() {

    }


    @Override
    public void onRightClick() {

    }






}
