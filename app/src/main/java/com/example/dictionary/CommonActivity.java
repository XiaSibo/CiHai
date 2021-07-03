package com.example.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.dictionary.adapter.CharacterAdapter;
import com.example.dictionary.adapter.SearchAdapter;
import com.example.dictionary.merge.ChineseCharacter;
import com.example.dictionary.merge.txtMerge;
import com.example.dictionary.utils.AssetUtils;
import com.example.dictionary.database.operateDatabase;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import java.util.ArrayList;
import java.util.List;

public class CommonActivity extends DataActivity {
    final int TYPE_BOPOMOFO = 0, TYPE_RADICAL = 1;
    final String FILE_BOPOMOFO = "bopomofo.txt", FILE_RADICAL = "radical.txt";
    TextView title;
    ExpandableListView elv;
    PullToRefreshGridView ptrgv;
    List<String> fatherMsg;
    List<List<txtMerge.resultMerge>> childMsg;
    private SearchAdapter adapter;
    private CharacterAdapter cAdapter;
    int selectFatherPos = 0, selectChildPos = 0;
    List<ChineseCharacter.resultMsg.character> characterData;
    int totalPage;
    int page = 1;
    int pageSize = 50;
    String tag = "";
    String url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bopomofo);
        title = findViewById(R.id.search_bopomofo);
        elv = findViewById(R.id.search_menu);
        ptrgv = findViewById(R.id.search_msg);
        initCharacter();
    }

    @Override
    public void onSuccess(String result) {
        ChineseCharacter cc = new Gson().fromJson(result, ChineseCharacter.class);
        ChineseCharacter.resultMsg rmsg = cc.getResult();
        totalPage = rmsg.getTotalpage();
        List<ChineseCharacter.resultMsg.character> clist = rmsg.getList();
        refreshData(clist);
        insertData(clist);
    }

    private void insertData(List<ChineseCharacter.resultMsg.character> clist) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                operateDatabase.insertObjsToInfo(clist);
            }
        }).start();
    }

    public void refreshData(List<ChineseCharacter.resultMsg.character> clist) {
        if (page == 1) {
            characterData.clear();
            characterData.addAll(clist);
            cAdapter.notifyDataSetChanged();
        } else {
            characterData.addAll(clist);
            cAdapter.notifyDataSetChanged();
            ptrgv.onRefreshComplete();
        }
    }

    private void initCharacter() {
        characterData = new ArrayList<>();
        cAdapter = new CharacterAdapter(this, characterData);
        ptrgv.setAdapter(cAdapter);
    }

    public void setDataListener(final int type) {
        ptrgv.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        ptrgv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<GridView>() {
            @Override
            public void onRefresh(PullToRefreshBase<GridView> refreshView) {
                if(page<totalPage){
                    page++;
                    if (type == TYPE_BOPOMOFO) {
                        url = com.example.dictionary.utils.URLUtils.getPinyinurl(tag, page, pageSize);
                    }else if (type == TYPE_RADICAL) {
                        url = com.example.dictionary.utils.URLUtils.getBushouurl(tag, page, pageSize);
                    }
                    loadData(url);
                }else{
                    ptrgv.onRefreshComplete();
                }
            }
        });
        ptrgv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChineseCharacter.resultMsg.character cmsg = characterData.get(position);
                String zi = cmsg.getZi();
                Intent intent = new Intent(getBaseContext(), CharacterActivity.class);
                intent.putExtra("zi",zi);
                startActivity(intent);
            }
        });
    }
    public void setElvListener(final int type) {
        elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                adapter.setSelectFatherPos(groupPosition);
                selectFatherPos = groupPosition;
                int fatherSize = childMsg.get(selectFatherPos).size();
                if (fatherSize <= selectChildPos){
                    selectChildPos = fatherSize-1;
                    adapter.setSelectChildPos(selectChildPos);
                }
                adapter.notifyDataSetInvalidated();
                getData(type);
                return false;
            }
        });
        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                adapter.setSelectFatherPos(groupPosition);
                adapter.setSelectChildPos(childPosition);
                adapter.notifyDataSetInvalidated();
                selectFatherPos = groupPosition;
                selectChildPos = childPosition;
                getData(type);
                return false;
            }
        });
    }

    private void getData(int type) {
        List<txtMerge.resultMerge> rList = childMsg.get(selectFatherPos);
        page = 1;
        txtMerge.resultMerge rMerge = rList.get(selectChildPos);
        if (type == TYPE_BOPOMOFO) {
            tag = rMerge.getPinyin();
            url = com.example.dictionary.utils.URLUtils.getPinyinurl(tag, page, pageSize);
        }else if (type == TYPE_RADICAL) {
            tag = rMerge.getBushou();
            url = com.example.dictionary.utils.URLUtils.getBushouurl(tag, page, pageSize);
        }
        loadData(url);

    }

    public void initMsg(String assetsTxt, int type) {
        fatherMsg = new ArrayList<>();
        childMsg = new ArrayList<>();
        String json = AssetUtils.getAssetsContent(this, assetsTxt);
        if (!TextUtils.isEmpty((json))) {
            txtMerge tm = new Gson().fromJson(json,txtMerge.class);
            List<txtMerge.resultMerge> list = tm.getResult();
            List<txtMerge.resultMerge> fatherlist = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                txtMerge.resultMerge m = list.get(i);

                if (type == TYPE_BOPOMOFO) {
                    String bopomofoKey = m.getPinyin_key();
                    if (!fatherMsg.contains(bopomofoKey)) {
                        fatherMsg.add(bopomofoKey);
                        if (fatherlist.size() > 0) {
                            childMsg.add(fatherlist);
                        }
                        fatherlist = new ArrayList<>();
                        fatherlist.add(m);
                    } else {
                        fatherlist.add(m);
                    }

                } else if (type == TYPE_RADICAL) {
                    String radicalKey = m.getBihua();
                    if (!fatherMsg.contains(radicalKey)) {
                        fatherMsg.add(radicalKey);
                        if (fatherlist.size() > 0) {
                            childMsg.add(fatherlist);
                        }
                        fatherlist = new ArrayList<>();
                        fatherlist.add(m);
                    } else {
                        fatherlist.add(m);
                    }
                }
            }

            childMsg.add(fatherlist);

            //Log.i("animee", "initMsg:fatherMsg="+fatherMsg);
            //Log.i("animee", "initMsg:this="+this);
            //Log.i("animee", "initMsg:childMsg="+childMsg);
            adapter = new SearchAdapter(this, fatherMsg, childMsg, type);
            elv.setAdapter(adapter);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_back:
                finish();
                break;
        }
    }
}