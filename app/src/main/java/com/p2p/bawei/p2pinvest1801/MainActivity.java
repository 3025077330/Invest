package com.p2p.bawei.p2pinvest1801;



import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bw.framwork.base.BaseActivity;
import com.bw.framwork.base.FragmentAdapter;

import com.bw.framwork.view.BottomBar;
import com.bw.invest.fragment.InvestFragment;
import com.bw.more.fragment.MoreFragment;
import com.bw.user.fragment.MineFragment;
import com.p2p.bawei.p2pinvest1801.home.fragment.HomeFragment;

import java.util.ArrayList;


@Route(path = "/main/MainActivity")
public class MainActivity extends BaseActivity implements BottomBar.IBottomBarSelectListener {
    private ViewPager viewPager;
    private BottomBar bottomBar;
    private FragmentAdapter fragmentAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] titles = new String[]{"首页", "投资", "我的资产", "更多"};

    @Override
    protected int bandlayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initview() {
        ARouter.getInstance().inject(this);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        bottomBar = (BottomBar) findViewById(R.id.bottom_bar);
        bottomBar.setTabTitle(titles);
        bottomBar.setBottomBarSelectListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                onBottomForPagerSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initdata() {
        fragments.add(new HomeFragment());
        fragments.add(new InvestFragment());
        fragments.add(new MineFragment());
        fragments.add(new MoreFragment());
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onBottomBarSelected(int selectIndex) {
        viewPager.setCurrentItem(selectIndex);
    }

    @Override
    public void onBottomForPagerSelected(int selectindex) {

        bottomBar.FindIndex(selectindex);
    }


}
