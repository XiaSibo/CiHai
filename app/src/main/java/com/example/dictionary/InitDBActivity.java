package com.example.dictionary;

import android.app.AppComponentFactory;
import android.app.Application;
import android.content.ContentValues;

import com.example.dictionary.database.operateDatabase;
import com.example.dictionary.merge.ChineseCharacter;

import org.xutils.x;

public class InitDBActivity extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        operateDatabase.initDatabase(this);
    }
}
