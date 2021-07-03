package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.dictionary.adapter.CollectAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class CollectActivity extends AppCompatActivity {
    ViewPager cVp;
    String titles = "汉字";
    List<Fragment>mDatas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        cVp = (ViewPager) findViewById(R.id.collect_msgsss);
        initPager();
    }

    private void initPager() {
        mDatas = new ArrayList<>();
        Fragment frag = new collection();
        Bundle bundle = new Bundle();
        bundle.putString("type",titles);
        frag.setArguments(bundle);
        mDatas.add(frag);
        CollectAdapter ada = new CollectAdapter(getSupportFragmentManager(), mDatas, titles);
        cVp.setAdapter(ada);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.collect_back:
                finish();
                break;
        }
    }
}