package com.bw.common.cache.sql;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Myprovider extends ContentProvider {
    private SQLiteDatabase db;
    private SqlHelper sqlHelper;

    @Override
    public boolean onCreate() {
        sqlHelper = new SqlHelper(getContext());
        db = sqlHelper.getWritableDatabase();
        if (db == null) {
            return false;
        }
        return true;

    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = db.query("banners", projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        db.insert("banners", null, values);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return db.delete("banners", selection, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return db.update("banners", values, selection, selectionArgs);
    }
}
