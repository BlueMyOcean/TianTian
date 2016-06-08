package com.jinjiang.computer.tianyi.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ben on 2016/4/12 0012.
 */
public class DBHelper extends SQLiteOpenHelper{

    private final String CREATE_STUDENTINFO = "create table StudentInfo("
                                +"studentId integer primary key,"
                                +"studentName text,"
                                +"sex integer,"
                                +"className text,"
                                +"ill integer,"
                                +"absent integer,"
                                +"late integer,"
                                +"total integer)";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENTINFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
