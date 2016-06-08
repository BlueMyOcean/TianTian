package com.jinjiang.computer.tianyi.utils;

import com.jinjiang.computer.tianyi.database.DBHelper;

/**
 * Created by Ben on 2016/4/13 0013.
 * 获取单例的DBHelper
 */
public class GetDBHelper {
    private static DBHelper dbHelper= new DBHelper(MyApplication.getMyContext(),"Sign.db",null,1);
    public static DBHelper GetTheDBHelper()
    {
        return dbHelper;
    }
}
