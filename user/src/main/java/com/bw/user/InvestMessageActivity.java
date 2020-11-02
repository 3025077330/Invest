package com.bw.user;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bw.common.FinanCilalConstant;
import com.bw.framwork.base.BaseActivity;
import com.bw.framwork.base.FragmentAdapter;
import com.bw.user.Recharge.ChongzhiFragment;
import com.bw.user.fragment.CircularFragment;
import com.bw.user.fragment.CylindricalFragment;
import com.bw.user.Withdraw.TiXianFragment;
import com.bw.user.fragment.TouZhiFragment;
import com.bw.user.fragment.UserMsgFragment;

import java.util.ArrayList;

public class InvestMessageActivity extends BaseActivity {

    private ViewPager viewPager;
    private FragmentAdapter fragmentAdapter;


    private ArrayList<Fragment> fragmentlist = new ArrayList<>();

    @Override
    protected int bandlayout() {
        return R.layout.activity_invest_message;
    }

    @Override
    protected void initview() {
        fragmentlist.add(new TouZhiFragment());
        fragmentlist.add(new CylindricalFragment());
        fragmentlist.add(new CircularFragment());
        fragmentlist.add(new ChongzhiFragment());
        fragmentlist.add(new TiXianFragment());
        fragmentlist.add(new UserMsgFragment());
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragmentlist);
        viewPager.setAdapter(fragmentAdapter);
    }

    @Override
    protected void initdata() {
        Bundle bundle = getIntent().getBundleExtra(FinanCilalConstant.BUNDEL);
        int index = bundle.getInt("index");
        viewPager.setCurrentItem(index);
    }

}



