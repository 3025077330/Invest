package com.bw.invest.allinvest;

import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.framwork.base.BaseMVPFragment;
import com.bw.framwork.view.LoadingView;
import com.bw.invest.R;
import com.bw.invest.adapter.AllInvestAdapter;
import com.bw.invest.allinvest.contract.AllInvestContract;
import com.bw.invest.allinvest.presenter.AllInvestPresenterImpl;
import com.bw.net.mode.InvestBean;


public class AllInvestFragment extends BaseMVPFragment<AllInvestPresenterImpl, AllInvestContract.IAllInsetView> implements AllInvestContract.IAllInsetView, AllInvestAdapter.IRecyclerViewItemClickListener {
    private AllInvestAdapter allInvestAdapter;
    private RecyclerView recyView;
    private LoadingView loadview;
    private LinearLayoutManager mLinearLayoutManager;
    private int beforepostion;

    @Override
    protected void initPresenter() {
        ihttpView = new AllInvestPresenterImpl();
    }

    @Override
    protected void initPresenterData() {
        ihttpView.getIvset();
    }

    @Override
    public void onconnect() {
        findViewById(R.id.errimage).setVisibility(View.GONE);
    }

    @Override
    public void noconnect() {
        findViewById(R.id.errimage).setVisibility(View.VISIBLE);
    }

    @Override
    protected int bandlayout() {
        return R.layout.all_invest;
    }

    @Override
    protected void initview() {
        recyView = findViewById(R.id.recy_view);
        loadview = (LoadingView) findViewById(R.id.loadview);
        allInvestAdapter = new AllInvestAdapter();
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        recyView.setLayoutManager(mLinearLayoutManager);


        recyView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyView.setAdapter(allInvestAdapter);
        allInvestAdapter.setiRecyclerViewItemClickListener(this);
    }

    @Override
    protected void initdata() {

    }


    @Override
    public void onIvset(InvestBean investBean) {
        if (investBean != null) {
            findViewById(R.id.title).setVisibility(View.VISIBLE);
            allInvestAdapter.updataData(investBean.getResult());
        }
    }

    @Override
    public void showLoading() {
        loadview.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadview.setVisibility(View.GONE);
        loadview.stop();
    }

    @Override
    public void showError(String code, String msg) {

    }


    @Override
    public void onItemClick(int position) {

        if (position == 0) {
            allInvestAdapter.notifyItemMoved(0,beforepostion);
            recyView.scrollToPosition(0);
            return;
        }
        recyView.scrollToPosition(0);
        allInvestAdapter.notifyItemMoved(position, 0);
        beforepostion = position;
    }
}
