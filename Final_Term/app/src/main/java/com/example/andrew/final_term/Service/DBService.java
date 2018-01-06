package com.example.andrew.final_term.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.andrew.final_term.Model.Info;

import java.util.ArrayList;

public class DBService extends SQLiteOpenHelper {
    private static final String DB = "app.db";
    private static final String TABLE = "Notes";
    private static final int DB_VERSION = 1;

    private static final String LAST_MODIFIED_TIME = "lastModifiedTime";
    private static final String CONTEXT = "context";

    private DBService(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static DBService getService(Context context) {
        return new DBService(context, DB, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("create table if not exists %s (" +
                "%s long primary key," +
                "%s varchar)", TABLE, LAST_MODIFIED_TIME, CONTEXT));
    }

    public ArrayList<Info> getAllInfo() {
        ArrayList <Info> infoArrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Long lastModifiedTime =  cursor.getLong(cursor.getColumnIndex(LAST_MODIFIED_TIME));
                String content = cursor.getString(cursor.getColumnIndex(CONTEXT));
                infoArrayList.add(new Info("", lastModifiedTime, content));
            } while (cursor.moveToNext());
        }
        return infoArrayList;
    }

    public Info getInfo(long lastModifiedTime) {
        SQLiteDatabase db = getReadableDatabase();
        Long lt = lastModifiedTime;
        Cursor cursor = db.query(TABLE, null, "lastModifiedTime = ?", new String[]{ lt.toString() }, null, null, null);
        if (cursor.moveToFirst()) {
            String content = cursor.getString(cursor.getColumnIndex(CONTEXT));
            return new Info("", lastModifiedTime, content);
        }
        return null;
    }
    public void updateInfo(long lastModifiedTime,  String context) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("context", context);
        db.update(TABLE, cv, String.format("%s = %s", LAST_MODIFIED_TIME, String.valueOf(lastModifiedTime)), null);
    }

    public void addInfo(String context) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LAST_MODIFIED_TIME, System.currentTimeMillis());
        cv.put(CONTEXT, context);
        db.insert(TABLE, null, cv);
    }

    public void deleteInfo(long lastModifiedTime) {
        SQLiteDatabase db= getWritableDatabase();
        ContentValues cv = new ContentValues();
        db.delete(TABLE, String.format("%s = %s", LAST_MODIFIED_TIME, String.valueOf(lastModifiedTime)), null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
