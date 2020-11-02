package com.bw.more.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bw.framwork.base.BaseFragment;
import com.bw.framwork.base.BaseRVAdapter;
import com.bw.more.Dialog.FeedbackDiaLog;
import com.bw.more.MoreSetActivity;
import com.bw.more.R;
import com.bw.more.Dialog.CallPhoenDialog;
import com.bw.more.Dialog.PasswordDialog;
import com.bw.more.adapter.ShareAdapter;
import com.bw.more.bean.ShareBean;
import com.bw.user.RegiLoginActivity;
import com.bw.user.manager.InvestUserManager;

import java.util.ArrayList;

public class MoreFragment extends BaseFragment implements View.OnClickListener, BaseRVAdapter.IRecyclerViewItemClickListener {
    private Switch switchView;
    private TextView userSet;
    private PasswordDialog passwordDialog;
    private CallPhoenDialog callPhoenDialog;
    private FeedbackDiaLog feedbackDiaLog;

    @Override
    protected int bandlayout() {
        return R.layout.more_fragment;
    }

    @Override
    protected void initview() {
        findViewById(R.id.tv_phonecall).setOnClickListener(this);
        findViewById(R.id.phone_call).setOnClickListener(this);
        findViewById(R.id.msg_app).setOnClickListener(this);
        findViewById(R.id.tv_feek).setOnClickListener(this);
        findViewById(R.id.init_pass).setOnClickListener(this);
        findViewById(R.id.shar_app).setOnClickListener(this);
        findViewById(R.id.tv_upapp).setOnClickListener(this);
        switchView = findViewById(R.id.switch_view);
        userSet = findViewById(R.id.user_set);
        if (InvestUserManager.getInstance().isUserLogin()) {
            userSet.setText("注销用户");
        } else {
            userSet.setText("注册用户");
        }
        userSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = userSet.getText();
                if (text.equals("注销用户")) {
                    InvestUserManager.getInstance().processLogOut();
                    ARouter.getInstance().build("/main/MainActivity").navigation();
                } else if (text.equals("注册用户")) {
                    launchActivity(RegiLoginActivity.class, null);
                }
            }
        });
        switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ShowDoalog();
                }
            }
        });
    }

    @Override
    protected void initdata() {
    }

    private void ShowDoalog() {
        //代表开启手势密码
        if (getContext() != null && getActivity() != null) {
            passwordDialog = new PasswordDialog(getContext());
            passwordDialog.create();
            passwordDialog.show();
            if (passwordDialog.getWindow() != null) {
                final WindowManager.LayoutParams params = passwordDialog.getWindow().getAttributes();
                Point point = new Point();
                Display display = getActivity().getWindowManager().getDefaultDisplay();
                display.getSize(point);
                params.width = (point.x);
                params.height = (int) (point.y * 0.31);
                passwordDialog.getWindow().setAttributes(params);
            }
            passwordDialog.findViewById(R.id.bu_sure).setOnClickListener(this);
            passwordDialog.findViewById(R.id.bu_call_off).setOnClickListener(this);
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.init_pass) {
            Bundle bundle = new Bundle();
            bundle.putString("instruct", "setpassword");
            launchActivity(MoreSetActivity.class, bundle);
        } else if (v.getId() == R.id.shar_app) {
           // push();
            Intent textIntent = new Intent(Intent.ACTION_SEND);
            textIntent.setType("text/plain");
            textIntent.putExtra(Intent.EXTRA_TEXT, "硅谷金融真好用！");
            startActivity(Intent.createChooser(textIntent, "分享"));
        } else if (v.getId() == R.id.msg_app) {
            Bundle bundle = new Bundle();
            bundle.putString("instruct", "investmsg");
            launchActivity(MoreSetActivity.class, bundle);
        } else if (v.getId() == R.id.bu_sure) {
            Bundle bundle = new Bundle();
            bundle.putString("instruct", "setpassword");
            launchActivity(MoreSetActivity.class, bundle);
            passwordDialog.cancel();
        } else if (v.getId() == R.id.bu_call_off) {
            switchView.setChecked(false);
            passwordDialog.cancel();
        } else if (v.getId() == R.id.tv_phonecall) {
            showCallDialog();
        } else if (v.getId() == R.id.phone_call) {
            showCallDialog();
        } else if (v.getId() == R.id.bu_sure_call) {
            //进行拨打电话
            callphone();
            callPhoenDialog.cancel();
        } else if (v.getId() == R.id.bu_phone_off) {
            callPhoenDialog.cancel();
        } else if (v.getId() == R.id.tv_feek) {
            showFeekDialog();
        } else if (v.getId() == R.id.feek_sure) {
            showMsg("反馈成功");
            feedbackDiaLog.cancel();
        } else if (v.getId() == R.id.feek_off) {
            feedbackDiaLog.cancel();
        } else if (v.getId() == R.id.tv_upapp) {
            Bundle bundle = new Bundle();
            bundle.putString("instruct", "appmsg");
            launchActivity(MoreSetActivity.class, bundle);
        }else  if (v.getId()==R.id.shar_app){

        }
    }

    private PopupWindow popupWindow;
    private RecyclerView recyView;
    private ShareAdapter shareAdapter;
    private static ArrayList<ShareBean> shareBeanlist;

    static {
        shareBeanlist = new ArrayList<>();
        shareBeanlist.add(new ShareBean(R.mipmap.icon_weibo, "新浪微博"));
        shareBeanlist.add(new ShareBean(R.mipmap.icon_share_qq, "QQ"));
        shareBeanlist.add(new ShareBean(R.mipmap.icon_weixin, "微信"));
        shareBeanlist.add(new ShareBean(R.mipmap.messi, "新浪微博"));
        shareBeanlist.add(new ShareBean(R.mipmap.messi, "新浪微博"));
        shareBeanlist.add(new ShareBean(R.mipmap.messi, "新浪微博"));
        shareBeanlist.add(new ShareBean(R.mipmap.messi, "新浪微博"));
        shareBeanlist.add(new ShareBean(R.mipmap.messi, "新浪微博"));
        shareBeanlist.add(new ShareBean(R.mipmap.messi, "新浪微博"));
        shareBeanlist.add(new ShareBean(R.mipmap.messi, "新浪微博"));
        shareBeanlist.add(new ShareBean(R.mipmap.messi, "新浪微博"));
        shareBeanlist.add(new ShareBean(R.mipmap.messi, "新浪微博"));
    }

    private void push() {
        popupWindow = new PopupWindow();
        View popshawindview = LinearLayout.inflate(getContext(), R.layout.share_popwindow, null);
        popupWindow.setContentView(popshawindview);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        recyView = (RecyclerView) popshawindview.findViewById(R.id.recy_view);
        recyView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        shareAdapter = new ShareAdapter();
        recyView.setAdapter(shareAdapter);
        shareAdapter.updataData(shareBeanlist);
        popupWindow.showAtLocation(popshawindview, Gravity.BOTTOM, 0, 0);

    }

    private void showCallDialog() {
        //代表开启手势密码
        if (getContext() != null && getActivity() != null) {
            callPhoenDialog = new CallPhoenDialog(getContext());
            callPhoenDialog.create();
            callPhoenDialog.show();
            if (callPhoenDialog.getWindow() != null) {
                final WindowManager.LayoutParams params = callPhoenDialog.getWindow().getAttributes();
                Point point = new Point();
                Display display = getActivity().getWindowManager().getDefaultDisplay();
                display.getSize(point);
                params.width = (point.x);
                params.height = (int) (point.y * 0.31);
                callPhoenDialog.getWindow().setAttributes(params);
            }

            callPhoenDialog.findViewById(R.id.bu_sure_call).setOnClickListener(this);
            callPhoenDialog.findViewById(R.id.bu_phone_off).setOnClickListener(this);

        }
    }

    private void callphone() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + "010-56253825"));
        startActivity(intent);
    }

    private void showFeekDialog() {
        if (getContext() != null && getActivity() != null) {
            feedbackDiaLog = new FeedbackDiaLog(getContext());
            feedbackDiaLog.create();
            feedbackDiaLog.show();
            if (feedbackDiaLog.getWindow() != null) {
                final WindowManager.LayoutParams params = feedbackDiaLog.getWindow().getAttributes();
                Point point = new Point();
                Display display = getActivity().getWindowManager().getDefaultDisplay();
                display.getSize(point);
                params.width = (point.x);
                params.height = (int) (point.y * 0.34);
                feedbackDiaLog.getWindow().setAttributes(params);
            }
            feedbackDiaLog.findViewById(R.id.feek_sure).setOnClickListener(this);
            feedbackDiaLog.findViewById(R.id.feek_off).setOnClickListener(this);
        }
    }

    @Override
    public void onItemClick(int position) {
        switch (shareBeanlist.get(position).getTitle()) {
            case "微博":

                break;
            case "QQ":
                
                break;
            case "微信":
                break;
            case "":
                break;

        }
    }


}
