package com.p2p.bawei.p2pinvest1801.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.p2p.bawei.p2pinvest1801.R;

public class DoloadDialog extends Dialog {
    public DoloadDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doloaddialog);
    }


}
