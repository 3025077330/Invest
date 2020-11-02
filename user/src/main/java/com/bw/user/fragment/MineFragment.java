package com.bw.user.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bw.common.FinanCilalConstant;
import com.bw.user.manager.InvestUserManager;
import com.bw.framwork.base.BaseFragment;
import com.bw.user.InvestMessageActivity;
import com.bw.user.R;
import com.bw.user.RegiLoginActivity;


public class MineFragment extends BaseFragment implements View.OnClickListener {

    private TextView tvUsername;

    @Override
    protected int bandlayout() {
        return R.layout.minefragment;
    }

    @Override
    protected void initview() {
        if (!InvestUserManager.getInstance().isUserLogin()) {
            launchActivity(RegiLoginActivity.class, null);
            return;
        }
        tvUsername = (TextView) findViewById(R.id.tv_username);
        toolbar.setToolBarClickListner(this);
        findViewById(R.id.tvtzglone).setOnClickListener(this);
        findViewById(R.id.tvtzgl_zg).setOnClickListener(this);
        findViewById(R.id.tvgzglthree).setOnClickListener(this);
        findViewById(R.id.tv_chongzhi).setOnClickListener(this);
        findViewById(R.id.tv_tixian).setOnClickListener(this);
        Glide.with(getContext()).load(FinanCilalConstant.BaseUrl + InvestUserManager.getInstance().getUserImage()).transform(new CircleCrop())
                .into((ImageView) findViewById(R.id.user_head));
        tvUsername.setText("Hi." + InvestUserManager.getInstance().getName());
    }

    @Override
    protected void initdata() {

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvtzglone) {
            Bundle bundle = new Bundle();
            bundle.putInt("index", 0);
            launchActivity(InvestMessageActivity.class, bundle);
        } else if (v.getId() == R.id.tvtzgl_zg) {
            //进行柱形数据分布图页面
            Bundle bundle = new Bundle();
            bundle.putInt("index", 1);
            launchActivity(InvestMessageActivity.class, bundle);
        } else if (v.getId() == R.id.tvgzglthree) {
            //进入饼形数据分布图页面
            Bundle bundle = new Bundle();
            bundle.putInt("index", 2);
            launchActivity(InvestMessageActivity.class, bundle);
        } else if (v.getId() == R.id.tv_chongzhi) {
            //进入充值页面
            Bundle bundle = new Bundle();
            bundle.putInt("index", 3);
            launchActivity(InvestMessageActivity.class, bundle);
        } else if (v.getId() == R.id.tv_tixian) {
            //进入提现页面
            Bundle bundle = new Bundle();
            bundle.putInt("index", 4);
            launchActivity(InvestMessageActivity.class, bundle);
        }
    }

    @Override
    public void onRightClick() {
        super.onRightClick();
        //进入提现页面
        Bundle bundle = new Bundle();
        bundle.putInt("index", 5);
        launchActivity(InvestMessageActivity.class, bundle);
        launchActivity(InvestMessageActivity.class, bundle);
    }
}
