package com.bw.more.ModeUpdata.contract;

import com.bw.framwork.base.BasePresenter;
import com.bw.framwork.base.IView;
import com.bw.net.mode.VersionBean;

public class MdUpdataContract {

    public interface MdUpdataView extends IView {
        void IVersion(VersionBean versionBean);
    }

    public static abstract class HomePresenter extends BasePresenter<MdUpdataView> {
        public abstract void getvision();
    }
}
