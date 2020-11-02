package com.bw.user.Withdraw;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bw.user.manager.InvestUserManager;
import com.bw.framwork.base.BaseMVPFragment;
import com.bw.net.mode.MoneyBean;
import com.bw.user.R;
import com.bw.user.Withdraw.contract.WithdrawContract;
import com.bw.user.Withdraw.presenter.WithdrawPresenterImpl;


public class TiXianFragment extends BaseMVPFragment<WithdrawPresenterImpl, WithdrawContract.IRecharge> implements WithdrawContract.IRecharge {
    private EditText f3PopEd;
    private TextView f3PopYu;
    private Button f3PopBtn;


    @Override
    protected void initPresenter() {
        ihttpView = new WithdrawPresenterImpl();
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
        return R.layout.tixian_fragm;
    }

    @Override
    protected void initview() {
        f3PopEd = (EditText) findViewById(R.id.f3_pop_ed);
        f3PopYu = (TextView) findViewById(R.id.f3_pop_yu);
        f3PopBtn = (Button) findViewById(R.id.f3_pop_btn);
        f3PopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ihttpView.UpDateMoney(f3PopEd.getText().toString().trim());
            }
        });
    }

    @Override
    protected void initdata() {
        f3PopYu.setText(InvestUserManager.getInstance().getUserMoney() + "");
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
