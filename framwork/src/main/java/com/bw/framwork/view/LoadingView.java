package com.bw.framwork.view;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.bw.framwork.R;

public class LoadingView extends LinearLayout {
    private AnimationDrawable animationDrawable;
    public LoadingView(Context context) {
        super(context);
        init(context);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.loading_layout, this);
        ImageView imageView = view.findViewById(R.id.image);
        animationDrawable = (AnimationDrawable) imageView.getDrawable();
        animationDrawable.start();
    }

    public void stop() {
        if (animationDrawable != null) {
            animationDrawable.stop();
        }
    }
}
