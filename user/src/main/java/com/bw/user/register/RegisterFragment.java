package com.bw.user.register;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.framwork.base.BaseMVPFragment;

import com.bw.framwork.view.ToolBar;
import com.bw.net.mode.RegisterBean;
import com.bw.user.R;
import com.bw.user.RegiLoginActivity;
import com.bw.user.register.contract.RegisterContract;
import com.bw.user.register.presenter.RegisterPresenterImpl;

public class RegisterFragment extends BaseMVPFragment<RegisterPresenterImpl, RegisterContract.IRegister> implements RegisterContract.IRegister {
    private ToolBar toolbar;
    private EditText edPhone;
    private EditText edUser;
    private EditText edPassword;
    private EditText edRepassword;
    private Button buRegister;
    private Button buLogin;


    @Override
    protected void initPresenter() {
        ihttpView = new RegisterPresenterImpl();
    }

    @Override
    protected void initPresenterData() {

    }

    @Override
    protected int bandlayout() {
        return R.layout.register_fragment;
    }

    @Override
    protected void initview() {
        toolbar = (ToolBar) findViewById(R.id.toolbar);
        toolbar.setToolBarClickListner(this);
        edPhone = (EditText) findViewById(R.id.ed_phone);
        edUser = (EditText) findViewById(R.id.ed_user);
        edPassword = (EditText) findViewById(R.id.ed_password);
        edRepassword = (EditText) findViewById(R.id.ed_repassword);
        buRegister = (Button) findViewById(R.id.bu_register);
        buLogin = (Button) findViewById(R.id.bu_login);
        buLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegiLoginActivity activity = (RegiLoginActivity) getActivity();
                activity.changePager(0);//跳转到登录页面
            }
        });
        buRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //验证注册提交的数据格式是否正确
                String username = edUser.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                ihttpView.register(username, password);
            }
        });
    }


    @Override
    protected void initdata() {

    }

    @Override
    public void onregister(RegisterBean registerBean) {
        if (registerBean != null) {
            //注册成功
            Toast.makeText(getContext(), "注册成功", Toast.LENGTH_SHORT).show();
            RegiLoginActivity activity = (RegiLoginActivity) getActivity();
            activity.changePager(0);//跳转到登录页面
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
        showMsg(msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ihttpView.detachView();
    }

    @Override
    public void onconnect() {

    }

    @Override
    public void noconnect() {

    }

    @Override
    public void onLeftClick() {
        super.onLeftClick();
    }
}
