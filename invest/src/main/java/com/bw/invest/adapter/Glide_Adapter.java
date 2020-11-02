package com.bw.invest.adapter;

import android.widget.ImageView;


import com.bw.framwork.base.BaseRVAdapter;

import com.bw.framwork.glide.GlideUntils;
import com.bw.invest.R;


public class Glide_Adapter extends BaseRVAdapter<String> {
    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.glid_item;
    }

    @Override
    protected void convert(String itemData, BaseViewHolder baseViewHolder, int position) {
        ImageView imageView = baseViewHolder.getView(R.id.image);
        imageView.setImageResource(R.drawable.ic_launcher);
        imageView.setTag(itemData);
        GlideUntils.TaskBean taskBean = new GlideUntils.TaskBean(imageView, itemData);
        GlideUntils.getInstance().load(taskBean);
    }

    @Override
    protected int getViewType(int postion) {
        return 0;
    }


}
