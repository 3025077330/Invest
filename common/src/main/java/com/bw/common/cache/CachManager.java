package com.bw.common.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bw.common.cache.sql.SqlHelper;

import java.util.ArrayList;
import java.util.List;


public class CachManager {
    private static CachManager instance;
    private SqlHelper sqlHelper;
    private SQLiteDatabase wrdb;
    private ArrayList<BannerBean> bannerlist = new ArrayList<>();


    private CachManager() {
    }

    public static CachManager getInstance() {
        if (instance == null) {
            synchronized (CachManager.class) {
                if (instance == null) {
                    instance = new CachManager();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        sqlHelper = new SqlHelper(context);
        wrdb = sqlHelper.getWritableDatabase();
    }

    public void deleteBannerlist(DelCallBack delCallBack) {
        wrdb.delete("banners", null, null);
        if (delCallBack != null) {
            delCallBack.onDel();
        }
    }

    public void query(QuerCallBack querCallBack) {
        bannerlist.clear();
        Cursor cursor = wrdb.query("banners", null, null, null, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    BannerBean bannerBean = new BannerBean();
                    String ID = cursor.getString(cursor.getColumnIndex("ID"));
                    String IMAPAURL = cursor.getString(cursor.getColumnIndex("IMAPAURL"));
                    String IMAURL = cursor.getString(cursor.getColumnIndex("IMAURL"));
                    bannerBean.setID(ID);
                    bannerBean.setIMAPAURL(IMAPAURL);
                    bannerBean.setIMAURL(IMAURL);
                    bannerlist.add(bannerBean);
                }
                cursor.close();
                if (querCallBack != null) {
                    querCallBack.query(bannerlist);
                }
            }

        }
    }

    public void addBannerList(ArrayList<BannerBean> list, AddCallBack addCallBack) {
        for (int i = 0; i < list.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("ID", list.get(i).getID());
            contentValues.put("IMAPAURL", list.get(i).getIMAPAURL());
            contentValues.put("IMAURL", list.get(i).getIMAURL());
            wrdb.insert("banners", null, contentValues);
        }
        if (addCallBack != null) {
            addCallBack.onAdd();
        }
    }

    public interface AddCallBack {
        void onAdd();
    }

    public interface DelCallBack {
        void onDel();
    }

    public interface QuerCallBack {
        void query(List<BannerBean> bannerList);
    }


}
