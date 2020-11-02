package com.bw.user.Recharge.contract;

import com.bw.framwork.base.BasePresenter;
import com.bw.framwork.base.IView;
import com.bw.net.mode.MoneyBean;

public class RechargeContract {
    public interface IRecharge extends IView {
        void OnMoney(MoneyBean moneyBean);
    }

    public static abstract class RechargePresenter extends BasePresenter<IRecharge> {
        public abstract  void UpDateMoney(String money);
    }


}
