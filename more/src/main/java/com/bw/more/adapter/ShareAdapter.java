package com.bw.more.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.framwork.base.BaseRVAdapter;
import com.bw.more.R;
import com.bw.more.bean.ShareBean;

public class ShareAdapter extends BaseRVAdapter<ShareBean> {
    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.share_item;
    }

    @Override
    protected void convert(ShareBean itemData, BaseViewHolder baseViewHolder, int position) {
        TextView title = baseViewHolder.getView(R.id.title);
        title.setText(itemData.getTitle());
        ImageView image = baseViewHolder.getView(R.id.image);
        image.setImageResource(itemData.getImage());
    }

    @Override
    protected int getViewType(int postion) {
        return 0;
    }
}
