package com.jinjiang.computer.tianyi.utils;

import android.app.Application;
import android.content.Context;

/**
 * 自己初始化一个application方便全局获取Context
 * Created by Ben on 2016/4/12 0012.
 */
public class MyApplication extends Application {
    private  static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
    }

    public static Context getMyContext()
    {
        return  context;
    }
}
