package com.bw.user.fragment;

import com.bw.framwork.base.BaseFragment;
import com.bw.user.R;
import com.bw.user.view.ColumnarView;

import java.util.ArrayList;

public class CylindricalFragment extends BaseFragment {

    private ColumnarView columnar;
    private ArrayList<Long> datas = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();


    @Override
    protected int bandlayout() {
        return R.layout.cylincfragm;
    }

    @Override
    protected void initview() {
        columnar = (ColumnarView) findViewById(R.id.columnar);
    }

    @Override
    protected void initdata() {
        datas.add(75l);
        datas.add(34l);
        datas.add(65l);
        datas.add(32l);
        datas.add(55l);
        names.add("苹果");
        names.add("华为");
        names.add("小米");
        names.add("VIVO");
        names.add("OPPO");
        columnar.updateThisData(datas,names);
    }
}
