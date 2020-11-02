package com.bw.user.register.contract;

import com.bw.framwork.base.BasePresenter;
import com.bw.framwork.base.IView;
import com.bw.net.mode.RegisterBean;

public class RegisterContract {
    public interface IRegister extends IView {
        void onregister(RegisterBean registerBean);
    }

    public abstract static class RegisterPresenter extends BasePresenter<IRegister> {
        public abstract void register(String name, String password);
    }
}
