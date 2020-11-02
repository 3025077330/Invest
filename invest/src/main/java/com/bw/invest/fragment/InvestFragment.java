package com.bw.invest.fragment;


import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bw.framwork.base.BaseFragment;
import com.bw.invest.R;
import com.bw.invest.adapter.FragmentAdapter;
import com.bw.invest.allinvest.AllInvestFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class InvestFragment extends BaseFragment {
    private TabLayout tabLayout;
    private ViewPager viewpager;
    private ArrayList<String> strTablist = new ArrayList<>();
    private FragmentAdapter fragmentAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected int bandlayout() {
        return R.layout.invest_fragment;
    }

    @Override
    protected void initview() {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
    }

    @Override
    protected void initdata() {
        strTablist.clear();
        fragments.clear();
        strTablist.add("全部理财");
        strTablist.add("推荐理财");
        strTablist.add("热门理财");
        fragments.add(new AllInvestFragment());
        fragments.add(new RecomFragment());
        fragments.add(new HotFragment());
        fragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), fragments, strTablist);
        viewpager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewpager);
        viewpager.setCurrentItem(0);
    }
}
