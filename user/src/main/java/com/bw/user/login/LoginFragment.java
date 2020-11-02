package com.bw.user.login;

import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bw.framwork.view.BottomBar;
import com.bw.framwork.view.ToolBar;
import com.bw.user.manager.InvestUserManager;
import com.bw.framwork.base.BaseMVPFragment;

import com.bw.net.mode.LoginBean;
import com.bw.user.R;
import com.bw.user.RegiLoginActivity;
import com.bw.user.login.contract.LoginContract;
import com.bw.user.login.presenter.LoginPresenterImpl;


public class LoginFragment extends BaseMVPFragment<LoginPresenterImpl, LoginContract.IloginView> implements LoginContract.IloginView, View.OnClickListener {
    private ToolBar toolbar;
    private EditText edPhone;
    private EditText edPassword;


    @Override
    protected void initPresenter() {
        ihttpView = new LoginPresenterImpl();
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
        return R.layout.login_fragment;
    }

    @Override
    protected void initview() {
        toolbar = (ToolBar) findViewById(R.id.toolbar);
        toolbar.setToolBarClickListner(this);
        edPhone = (EditText) findViewById(R.id.ed_phone);
        edPassword = (EditText) findViewById(R.id.ed_password);
        findViewById(R.id.bu_register).setOnClickListener(this);
        findViewById(R.id.bu_login).setOnClickListener(this);
        findViewById(R.id.ib_weibo).setOnClickListener(this);
        findViewById(R.id.ib_qq).setOnClickListener(this);
        findViewById(R.id.ib_wechat).setOnClickListener(this);

    }

    @Override
    protected void initdata() {

    }

    @Override
    public void onlogin(LoginBean loginBean) {
        if (loginBean.getCode().equals("200")) {
            //登录成功 进行token存储
            InvestUserManager.getInstance().setLoginBean(loginBean);
            ARouter.getInstance().build("/main/MainActivity").withInt("index", BottomBar.HOME_INDEX).navigation();
            getActivity().finish();
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
    public void onLeftClick() {
        super.onLeftClick();
        ARouter.getInstance().build("/main/MainActivity").withInt("index", BottomBar.HOME_INDEX).navigation();
        getActivity().finish();//是不是一定能回到MainActivity，这个不一定，因为，MainActivity有可能被系统回收.
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bu_login) {
            //登录
            String username = edPhone.getText().toString().trim();
            String password = edPassword.getText().toString().trim();
            ihttpView.login(username, password);
        } else if (v.getId() == R.id.bu_register) {
            RegiLoginActivity activity = (RegiLoginActivity) getActivity();
            activity.changePager(1);//跳转到注册页面
        } else if (v.getId() == R.id.ib_weibo) {
            //微博登录
        } else if (v.getId() == R.id.ib_qq) {
            //qq登录
        } else if (v.getId() == R.id.ib_wechat) {
            //微信登录
        }
    }
}
