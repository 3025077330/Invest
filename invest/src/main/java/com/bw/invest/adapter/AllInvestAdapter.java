package com.bw.invest.adapter;

import android.widget.TextView;

import com.bw.framwork.base.BaseRVAdapter;

import com.bw.invest.R;
import com.bw.invest.view.MyProgress;
import com.bw.net.mode.InvestBean;

public class AllInvestAdapter extends BaseRVAdapter<InvestBean.ResultBean> {
    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.allinvest_item;
    }

    @Override
    protected void convert(InvestBean.ResultBean itemData, BaseViewHolder baseViewHolder, int position) {

        TextView tvmoneycount = baseViewHolder.getView(R.id.money_count);
        TextView title = baseViewHolder.getView(R.id.title);
        title.setText("   " + itemData.getName());
        tvmoneycount.setText(itemData.getMoney());
        TextView tvonecount = baseViewHolder.getView(R.id.one_imagecount);
        tvonecount.setText(itemData.getMinTouMoney());
        TextView yqcount = baseViewHolder.getView(R.id.yq_count);
        yqcount.setText(itemData.getYearRate());
        TextView tv_three_money = baseViewHolder.getView(R.id.three_money);
        tv_three_money.setText(itemData.getSuodingDays());
        TextView textthree_imagecount = baseViewHolder.getView(R.id.three_imagecount);
        textthree_imagecount.setText(itemData.getMemberNum());

        MyProgress myProgress = baseViewHolder.getView(R.id.myprogress);
        int parseInt = Integer.parseInt(itemData.getProgress());
        myProgress.setProgress(parseInt);
    }

    @Override
    protected int getViewType(int postion) {
        return 0;
    }
}
