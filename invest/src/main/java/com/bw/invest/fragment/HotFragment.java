package com.bw.invest.fragment;


import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.framwork.base.BaseFragment;
import com.bw.invest.R;
import com.bw.invest.view.FlowLayoutView;


import java.util.ArrayList;

public class HotFragment extends BaseFragment {


    private FlowLayoutView flowlayview;
    private int[] color = {0xFFFF34B3, 0xFF9ACD32, 0xFF9400D3, 0xFFEE9A00, 0xFF9C54FF, 0xFF3B7BA, 0xFFFFF598, 0xFFFAFFF5, 0xFFD7EBFA};
    //  private int[] color = {0xFFFFF598, 0xFF5672FF, 0xFFFF34B3, 0xFFD7EBFA, 0xFFEE9A00, 0xFF9314FF,0xFF2C2CEE,0xFF383838,0xFFFAFFF5,0xFF008080,0xFF9C54FF};
    private static ArrayList<String> strlist;

    static {
        strlist = new ArrayList<>();
        strlist.add("新手福利计划");
        strlist.add("财神道90天计划");
        strlist.add("铁路局汇款计划");
        strlist.add("屌丝迎娶白富美计划");
        strlist.add("硅谷计划");
        strlist.add("30天理财计划");
        strlist.add("180天理财计划");
        strlist.add("月月升");
        strlist.add("中情局投资商业经营");
        strlist.add("大学老师购买车辆");
        strlist.add("JAVA天下第一");
        strlist.add("美人鱼影视拍摄投资");
        strlist.add("旅游公司扩大规模");
        strlist.add("上班摸鱼");
        strlist.add("摩托罗拉洗钱计划");

    }

    @Override
    protected int bandlayout() {
        return R.layout.hot_fragment;
    }

    @Override
    protected void initview() {
        flowlayview = (FlowLayoutView) findViewById(R.id.flowlayview);
    }


    @SuppressLint("WrongConstant")
    @Override
    protected void initdata() {
        //往容器内添加TextView数据
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 5, 10, 5);
        if (flowlayview != null) {
            flowlayview.removeAllViews();
        }
        for (int i = 0; i < strlist.size(); i++) {
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setGradientType(GradientDrawable.RECTANGLE);
            drawable.setCornerRadius(60);
            int nowcolor = color[(int) (color.length * Math.random())];
            drawable.setColor(nowcolor);
            TextView tv = new TextView(getContext());
            tv.setPadding(28, 10, 28, 10);
            tv.setText(strlist.get(i));
            tv.setMaxEms(10);
            tv.setTextSize(25);
            tv.setSingleLine();
            tv.setLayoutParams(layoutParams);
            tv.setBackground(drawable);
            flowlayview.addView(tv, layoutParams);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.gc();
    }
}
