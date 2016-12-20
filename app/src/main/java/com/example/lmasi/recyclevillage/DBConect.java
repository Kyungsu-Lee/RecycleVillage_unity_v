package com.example.lmasi.recyclevillage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConect extends SQLiteOpenHelper{

    public DBConect(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table if not exists resources(attr text, numb integer, PRIMARY KEY(attr));");
        db.execSQL("create table if not exists product(name text, timer integer, image integer, PRIMARY KEY(name));");
        db.execSQL("create table if not exists idp(id text, product_name text , entry_num integer, PRIMARY KEY(id, product_name));");
        db.execSQL("create table if not exists account(id text , phone text, email text, addr text, PRIMARY KEY(id));");
        db.execSQL("create table if not exists point(point_index integer, item_index integer, right integer, PRIMARY KEY(point_index));");
        db.execSQL("create table if not exists character(id text, c_index integer, PRIMARY KEY(id));");
        db.execSQL("create table if not exists c_table(id text, c_index integer, buy integer, PRIMARY KEY(id, c_index));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
