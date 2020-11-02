package com.bw.user.Withdraw.contract;

import com.bw.framwork.base.BasePresenter;
import com.bw.framwork.base.IView;
import com.bw.net.mode.MoneyBean;
import com.bw.user.Recharge.contract.RechargeContract;

public class WithdrawContract {
    public interface IRecharge extends IView {
        void OnMoney(MoneyBean moneyBean);
    }

    public static abstract class RechargePresenter extends BasePresenter<RechargeContract.IRecharge> {
        public abstract  void UpDateMoney(String money);
    }



}
