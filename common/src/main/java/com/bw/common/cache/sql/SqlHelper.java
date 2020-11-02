package com.bw.common.cache.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlHelper extends SQLiteOpenHelper {


    public SqlHelper(Context context) {
        super(context, "banner.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table banners(ID varchar(100),IMAPAURL varchar(100),IMAURL varchar(100))");//在对应的数据库中创建所需的表

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
