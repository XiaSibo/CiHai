package com.example.dictionary.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class CollectAdapter extends FragmentPagerAdapter {
    List<Fragment>list;
    String titles;
    public CollectAdapter(FragmentManager fm, List<Fragment> list, String titles) {
        super(fm);
        this.list = list;
        this.titles = titles;
    }
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }
    @Override
    public int getCount() {
        return list.size();
    }
}