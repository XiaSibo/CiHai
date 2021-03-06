package com.example.dictionary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class openDatabase extends SQLiteOpenHelper {
    public openDatabase(@Nullable Context context) {
        super(context, "dict.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table info(_id integer primary key autoincrement,id varchar(20),zi varchar(4) unique not null," +
                "py varchar(10),wubi varchar(10),pinyin varchar(10),bushou varchar(4),bihua integer)";
        db.execSQL(sql);

        sql = "create table character_msg(_id integer primary key autoincrement,id varchar(20),zi varchar(4) unique not null,py varchar(10),"
                + "wubi varchar(10),pinyin varchar(10),bushou varchar(4),bihua integer,jijie text,xiangjie text)";
        db.execSQL(sql);

        sql = "create table collect_msg(_id integer primary key autoincrement,zi varchar(4) unique not null)";
        db.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

