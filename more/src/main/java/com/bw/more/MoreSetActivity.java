package com.bw.more;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.bw.framwork.base.BaseActivity;
import com.bw.more.fragment.InvestMsgFragment;
import com.bw.more.fragment.ModeUpdataFragment;
import com.bw.more.fragment.SetPassFragment;
import com.bw.user.fragment.UserMsgFragment;

public class MoreSetActivity extends BaseActivity {
    private UserMsgFragment userMsgFragment;
    private SetPassFragment setPassFragment;
    private InvestMsgFragment investMsgFragment;
    private FragmentManager supportFragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ModeUpdataFragment modeUpdataFragment;


    @Override
    protected int bandlayout() {
        return R.layout.activity_more_set;
    }

    private void initfragment() {

        fragmentTransaction.add(R.id.basefragmengt, setPassFragment);
        fragmentTransaction.add(R.id.basefragmengt, investMsgFragment);
        fragmentTransaction.add(R.id.basefragmengt, userMsgFragment);
        fragmentTransaction.add(R.id.basefragmengt, modeUpdataFragment);
        Bundle bundle = getIntent().getBundleExtra("BUNDEL");
        String stringExtra = bundle.getString("instruct");
        if (stringExtra.equals("setpassword")) {
            //显示设置密码页面
            fragmentTransaction.hide(investMsgFragment);
            fragmentTransaction.hide(userMsgFragment);
            fragmentTransaction.hide(modeUpdataFragment);
            fragmentTransaction.show(setPassFragment);
            fragmentTransaction.commit();
        } else if (stringExtra.equals("investmsg")) {
            //显示设置密码页面
            fragmentTransaction.hide(setPassFragment);
            fragmentTransaction.hide(userMsgFragment);
            fragmentTransaction.show(investMsgFragment);
            fragmentTransaction.hide(modeUpdataFragment);
            fragmentTransaction.commit();
        } else if (stringExtra.equals("usermsg")) {
            fragmentTransaction.hide(setPassFragment);
            fragmentTransaction.hide(investMsgFragment);
            fragmentTransaction.hide(modeUpdataFragment);
            fragmentTransaction.show(userMsgFragment);
            fragmentTransaction.commit();
        } else if (stringExtra.equals("appmsg")) {
            fragmentTransaction.hide(setPassFragment);
            fragmentTransaction.hide(investMsgFragment);
            fragmentTransaction.hide(userMsgFragment);
            fragmentTransaction.show(modeUpdataFragment);
            fragmentTransaction.commit();
        }
    }

    protected void initview() {
        supportFragmentManager = getSupportFragmentManager();
        fragmentTransaction = supportFragmentManager.beginTransaction();
        userMsgFragment = new UserMsgFragment();
        setPassFragment = new SetPassFragment();
        investMsgFragment = new InvestMsgFragment();
        modeUpdataFragment = new ModeUpdataFragment();
        initfragment();
    }

    @Override
    protected void initdata() {

    }
}
