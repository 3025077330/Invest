package com.bw.user.Recharge;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bw.user.manager.InvestUserManager;
import com.bw.framwork.base.BaseMVPFragment;
import com.bw.net.mode.MoneyBean;
import com.bw.user.R;
import com.bw.user.Recharge.contract.RechargeContract;
import com.bw.user.Recharge.presenter.RechargePresenterImpl;


public class ChongzhiFragment extends BaseMVPFragment<RechargePresenterImpl, RechargeContract.IRecharge> implements RechargeContract.IRecharge {
    private TextView f3PopYu;
    private EditText f3PopEd;

    @Override
    protected void initPresenter() {
        ihttpView = new RechargePresenterImpl();
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
        return R.layout.chongzhi_fragm;
    }

    @Override
    protected void initview() {
        f3PopYu = (TextView) findViewById(R.id.f3_pop_yu);
        f3PopEd = (EditText) findViewById(R.id.f3_pop_ed);
        findViewById(R.id.f3_pop_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ihttpView.UpDateMoney(f3PopEd.getText().toString().trim());
            }
        });
        f3PopYu.setText(InvestUserManager.getInstance().getUserMoney() + "");
    }

    @Override
    protected void initdata() {

    }

    @Override
    public void OnMoney(MoneyBean moneyBean) {
        if (moneyBean.getCode().equals(200)) {
            f3PopYu.setText(moneyBean.getResult());
            f3PopEd.setText("");
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
        getActivity().finish();
    }
}
