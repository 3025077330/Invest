package com.p2p.bawei.p2pinvest1801.home.contract;

import com.bw.framwork.base.BasePresenter;
import com.bw.framwork.base.IView;
import com.bw.net.mode.HomeBean;
import com.bw.net.mode.VersionBean;

import java.io.File;

public class HomeContract {
    public interface IHomeView extends IView {
        void IHomeView(HomeBean homeBean);
        void IVersion(VersionBean versionBean);
    }

    public static abstract class HomePresenter extends BasePresenter<IHomeView> {
        public abstract void gethome();
        public abstract void getvision();
    }
}
