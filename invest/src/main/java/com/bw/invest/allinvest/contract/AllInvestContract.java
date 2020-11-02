package com.bw.invest.allinvest.contract;

import com.bw.framwork.base.BasePresenter;
import com.bw.framwork.base.IView;
import com.bw.net.mode.InvestBean;

public class AllInvestContract {
    public interface IAllInsetView extends IView {
        void onIvset(InvestBean investBean);
    }

    public static abstract class AllInVestPresenter extends BasePresenter<IAllInsetView> {
        public abstract void getIvset();
    }
}
