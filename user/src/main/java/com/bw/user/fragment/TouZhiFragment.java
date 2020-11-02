package com.bw.user.fragment;

import android.view.View;
import android.widget.Toast;

import com.bw.framwork.base.BaseFragment;
import com.bw.user.R;
import com.bw.user.view.CircleMenuLayout;

public class TouZhiFragment extends BaseFragment {
    private CircleMenuLayout cirmenulayout;
    private String[] mItemTexts = new String[]{"安全中心 ", "特色服务", "投资理财",
            "转账汇款", "我的账户", "信用卡"};
    private int[] mItemImgs = new int[]{R.drawable.home_mbank_1_normal,
            R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
            R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
            R.drawable.home_mbank_6_normal};


    @Override
    protected int bandlayout() {
        return R.layout.touzifragment;
    }

    @Override
    protected void initview() {
        cirmenulayout = (CircleMenuLayout) findViewById(R.id.cirmenulayout);
        cirmenulayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
        cirmenulayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {
            @Override
            public void itemClick(View view, int pos) {
                Toast.makeText(getContext(), mItemTexts[pos],
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void itemCenterClick(View view) {
                Toast.makeText(getContext(),
                        "you can do something just like ccb  ",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void initdata() {

    }

    @Override
    public void onLeftClick() {
        super.onLeftClick();
        getActivity().finish();
    }
}
