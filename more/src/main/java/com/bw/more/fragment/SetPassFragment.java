package com.bw.more.fragment;

import android.widget.TextView;

import com.bw.framwork.base.BaseFragment;
import com.bw.more.R;
import com.bw.more.view.LockPatternView;

public class SetPassFragment extends BaseFragment implements LockPatternView.OnPatternChangerListener {
    private TextView title;
    private LockPatternView lockpatview;


    @Override
    protected int bandlayout() {
        return R.layout.setpasfragment;
    }

    @Override
    protected void initview() {
        title = (TextView) findViewById(R.id.title);
        lockpatview = (LockPatternView) findViewById(R.id.lockpatview);
        lockpatview.setMonPatternChangerListener(this);
    }

    @Override
    protected void initdata() {

    }


    @Override
    public void onPatternChange(String password) {
        if (password == null) {
            title.setText("至少5个点");
        } else {
            title.setText(password);
        }
    }

    @Override
    public void onPatternStarted(boolean isStarted) {
        if (isStarted) {
            title.setText("请绘制图案");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().finish();
    }
}
