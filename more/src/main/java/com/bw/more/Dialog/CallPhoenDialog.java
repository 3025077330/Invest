package com.bw.more.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.bw.more.R;

public class CallPhoenDialog extends Dialog {
    public CallPhoenDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ph_dialogfrag);
    }
}
