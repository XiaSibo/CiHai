package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dictionary.merge.infoMerge;
import com.example.dictionary.database.operateDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CharacterActivity extends DataActivity {
    TextView ziTv, pyTv, wubiTv, bihuaTv, bushouTv, basicTv,detailedTv;
    ListView lv;
    ImageView iv;
    String zi;
    List<String> characterData;
    private ArrayAdapter<String> adapter;
    private List<String> jijie;
    private List<String> xiangjie;
    boolean isStar = false;
    boolean isExist = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);
        Intent intent = getIntent();
        zi = intent.getStringExtra("zi");
        String url = com.example.dictionary.utils.URLUtils.getWordurl(zi);
        initData();
        characterData = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.character_tail, R.id.item_character_tail, characterData);
        lv.setAdapter(adapter);
        loadData(url);
        isExist = operateDatabase.isExistMsgInCollect(zi);
        isStar = isExist;
        setStarColor();
    }

    private void setStarColor() {
        if(isStar) {
            iv.setImageResource(R.mipmap.ic_star_sel);
        } else {
            iv.setImageResource(R.mipmap.ic_star);
        }
    }

    //@Override
    public void onSuccess(String json) {
        infoMerge wordBean = new Gson().fromJson(json, infoMerge.class);
        infoMerge.resultData rdata = wordBean.getResult();
        
        operateDatabase.insertObjToMsg(rdata);
        displayData(rdata);
    }

    public void onError(Throwable ex, boolean isOnCallback) {
        infoMerge.resultData rdata = operateDatabase.findMsgByMsg(zi);
        if (rdata!=null) {
            displayData(rdata);
        }
    }

    private void displayData(infoMerge.resultData rdata) {
        ziTv.setText(rdata.getZi());
        pyTv.setText(rdata.getPinyin());
        wubiTv.setText("五笔 : "+ rdata.getWubi());
        bihuaTv.setText("笔画 : "+ rdata.getBihua());
        bushouTv.setText("部首 : "+ rdata.getBushou());
        jijie = rdata.getJijie();
        xiangjie = rdata.getXiangjie();
        characterData.clear();
        characterData.addAll(jijie);
        adapter.notifyDataSetChanged();
    }

    private void initData() {
        ziTv = findViewById(R.id.character_zi);
        wubiTv = findViewById(R.id.character_wubi);
        pyTv = findViewById(R.id.character_pinyin);
        bihuaTv = findViewById(R.id.character_bihua);
        bushouTv = findViewById(R.id.character_bushou);
        basicTv = findViewById(R.id.character_basic);
        detailedTv = findViewById(R.id.character_detailed);
        lv = findViewById(R.id.character_tail);
        iv = findViewById(R.id.character_collection);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.character_back:
                finish();
                break;
            case R.id.character_collection:
                isStar = !isStar;
                setStarColor();
                break;
            case R.id.character_basic:
                basicTv.setTextColor(Color.WHITE);
                basicTv.setBackgroundColor(Color.rgb(55,0,179));
                detailedTv.setTextColor(Color.rgb(55,0,179));
                detailedTv.setBackgroundColor(Color.WHITE);
                characterData.clear();
                characterData.addAll(jijie);
                adapter.notifyDataSetChanged();
                break;
            case R.id.character_detailed:
                detailedTv.setTextColor(Color.WHITE);
                detailedTv.setBackgroundColor(Color.rgb(55,0,179));
                basicTv.setTextColor(Color.rgb(55,0,179));
                basicTv.setBackgroundColor(Color.WHITE);
                characterData.clear();
                characterData.addAll(xiangjie);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isExist&&!isStar) {
            operateDatabase.deleteMsgFromCollect(zi);
        }
        if (!isExist&&isStar) {
            operateDatabase.insertMsgToCollect(zi);
        }
    }
}