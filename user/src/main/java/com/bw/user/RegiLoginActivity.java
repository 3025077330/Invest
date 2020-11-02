package com.bw.user;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bw.framwork.base.BaseActivity;
import com.bw.framwork.base.FragmentAdapter;
import com.bw.user.login.LoginFragment;
import com.bw.user.register.RegisterFragment;

import java.util.ArrayList;

public class RegiLoginActivity extends BaseActivity {
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private FragmentAdapter fragmentAdapter;


    @Override
    protected int bandlayout() {
        return R.layout.activity_regi_login;
    }

    @Override
    protected void initview() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
    }

    @Override
    protected void initdata() {
        fragments.add(new LoginFragment());
        fragments.add(new RegisterFragment());
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);
    }

    public void changePager(int postion) {
        viewPager.setCurrentItem(postion);
    }
}
