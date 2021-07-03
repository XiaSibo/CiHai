package com.example.dictionary;

import com.example.dictionary.database.operateDatabase;
import android.os.Bundle;

import com.example.dictionary.merge.ChineseCharacter;

import java.util.List;

public class RadicalActivity extends CommonActivity {
    final int TYPE_BOPOMOFO = 0, TYPE_RADICAL = 1;
    final String FILE_BOPOMOFO = "bopomofo.txt", FILE_RADICAL = "radical.txt";
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title.setText(R.string.main_radical);
        initMsg(FILE_RADICAL, TYPE_RADICAL);
        setElvListener(TYPE_RADICAL);
        elv.expandGroup(0);
        tag = "丨";
        url = com.example.dictionary.utils.URLUtils.getBushouurl(tag, page, pageSize);
        loadData(url);
        setDataListener(TYPE_RADICAL);
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        List<ChineseCharacter.resultMsg.character> clist = operateDatabase.findCharacterByRadical(tag, page, pageSize);
        refreshData(clist);
    }
}