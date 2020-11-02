package com.bw.user.login.contract;

import com.bw.framwork.base.BasePresenter;
import com.bw.framwork.base.IView;
import com.bw.net.mode.LoginBean;

public class LoginContract {
    public interface IloginView extends IView {
        void onlogin(LoginBean loginBean);
    }

    public abstract static class LoginPresenter extends BasePresenter<IloginView> {
        public abstract void login(String name,String password);
    }
}
