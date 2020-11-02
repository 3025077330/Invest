package com.p2p.bawei.p2pinvest1801.home.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.common.cache.BannerBean;
import com.bw.common.cache.CachManager;
import com.bw.framwork.base.BaseFragment;
import com.p2p.bawei.p2pinvest1801.BannerMsgActivity;
import com.p2p.bawei.p2pinvest1801.R;
import com.p2p.bawei.p2pinvest1801.view.ProgressView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment implements CachManager.QuerCallBack, View.OnClickListener {
    private float num = 0.9f;
    private Banner banner;
    private TextView tvCount;
    private ProgressView myprogress2;
    private ArrayList<String> titlelist = new ArrayList<>();
    private ArrayList<String> banners = new ArrayList<>();
    private ArrayList<BannerBean> bannerslist = new ArrayList<>();
    @Override
    protected int bandlayout() {
        return R.layout.home_fragment;
    }

    @Override
    protected void initview() {
        banner = findViewById(R.id.banner);
        myprogress2 = findViewById(R.id.myprogress);
        tvCount = findViewById(R.id.tv_count);
        findViewById(R.id.add_bu).setOnClickListener(this);
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(getContext()).load(path).into(imageView);
            }
        });
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("bannerurl", bannerslist.get(position).getIMAPAURL());
                launchActivity(BannerMsgActivity.class, bundle);
            }
        });
    }

    @Override
    protected void initdata() {
        CachManager.getInstance().query(this);
        tvCount.setText("预期年利率：" + 8.00 + "%");
        myprogress2.setProgressWithAnim(num);
    }

    @Override
    public void query(List<BannerBean> bannerList) {

        bannerslist = (ArrayList<BannerBean>) bannerList;
        for (int i = 0; i < bannerslist.size(); i++) {
            banners.add(bannerslist.get(i).getIMAURL());
            printLog(bannerList.get(i).getIMAPAURL());
        }
        titlelist.add("分享砍学费");
        titlelist.add("人脉总动员");
        titlelist.add("想不到你是这样的app");
        titlelist.add("购物节，爱不单行");
        banner.setImages(banners);
        banner.setBannerTitles(titlelist);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        banner.setBannerAnimation(Transformer.DepthPage);
        banner.start();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_bu) {
            num += 0.01f;
            if (num >= 1f) {
                num = 1f;
                myprogress2.setProgressWithAnim(num);
                myprogress2.postInvalidate();
                return;
            }
            myprogress2.setProgressWithAnim(num);
            myprogress2.postInvalidate();

        }
    }


}
