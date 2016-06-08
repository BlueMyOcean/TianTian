package com.jinjiang.computer.tianyi.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.jinjiang.computer.tianyi.R;

/**
 * Created by Administrator on 2016/6/5 0005.
 */
public class MyDialog extends Dialog {

    public MyDialog(Context context, int theme) {
        super(context, theme);
    }

 //   public SelectDialog(Context context) {
 //       super(context);
 //   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydialog);
    }
}
