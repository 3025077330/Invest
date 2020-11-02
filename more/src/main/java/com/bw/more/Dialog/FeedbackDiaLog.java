package com.bw.more.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.bw.more.R;

public class FeedbackDiaLog extends Dialog {
    public FeedbackDiaLog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feekba_dialog);
    }
}
