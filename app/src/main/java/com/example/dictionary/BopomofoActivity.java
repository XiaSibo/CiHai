package com.example.dictionary;

import com.example.dictionary.database.operateDatabase;
import com.example.dictionary.merge.ChineseCharacter;

import android.os.Bundle;

import java.util.List;

public class BopomofoActivity extends CommonActivity {
    final int TYPE_BOPOMOFO = 0, TYPE_RADICAL = 1;
    final String FILE_BOPOMOFO = "bopomofo.txt", FILE_RADICAL = "radical.txt";
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMsg(FILE_BOPOMOFO, TYPE_BOPOMOFO);
        setElvListener(TYPE_BOPOMOFO);
        elv.expandGroup(0);
        tag = "a";
        url = com.example.dictionary.utils.URLUtils.getPinyinurl(tag, page, pageSize);
        loadData(url);
        setDataListener(TYPE_BOPOMOFO);
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        List<ChineseCharacter.resultMsg.character> clist = operateDatabase.findCharacterByBopomofo(tag, page, pageSize);
        refreshData(clist);
    }
}