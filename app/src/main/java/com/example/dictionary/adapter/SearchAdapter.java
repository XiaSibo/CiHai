package com.example.dictionary.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.dictionary.R;
import com.example.dictionary.merge.txtMerge;

import java.util.List;

public class SearchAdapter extends BaseExpandableListAdapter {
    final int TYPE_BOPOMOFO = 0, TYPE_RADICAL = 1;
    final String FILE_BOPOMOFO = "bopomofo.txt", FILE_RADICAL = "radical.txt";
    Context context;
    List<String>fatherMsg;
    List<List<txtMerge.resultMerge>>childMsg;

    LayoutInflater inflater;
    int type;
    int selectFatherPos = 0, selectChildPos = 0;

    public void setSelectChildPos(int selectChildPos) {
        this.selectChildPos = selectChildPos;
    }

    public void setSelectFatherPos(int selectFatherPos) {
        this.selectFatherPos = selectFatherPos;
    }

    public SearchAdapter(Context context, List<String> fatherMsg, List<List<txtMerge.resultMerge>> childMsg, int type) {
        this.context = context;
        this.fatherMsg = fatherMsg;
        this.childMsg = childMsg;
        this.type = type;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return fatherMsg.size();
    }

    @Override
    public int getChildrenCount(int fatherIndex) {
        return childMsg.get(fatherIndex).size();
    }

    @Override
    public Object getGroup(int fatherIndex) {
        return fatherMsg.get(fatherIndex);
    }

    @Override
    public Object getChild(int fatherIndex, int childIndex) {
        return childMsg.get(fatherIndex).get(childIndex);
    }

    @Override
    public long getGroupId(int fatherIndex) {
        return fatherIndex;
    }

    @Override
    public long getChildId(int fatherIndex, int childIndex) {
        return childIndex;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int fatherIndex, boolean isExpanded, View convertView, ViewGroup parent) {
        FatherView fv = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.father,null);
            fv = new FatherView(convertView);
            convertView.setTag(fv);
        }else{
            fv = (FatherView) convertView.getTag();
        }

        String word = fatherMsg.get(fatherIndex);
        if (type == TYPE_BOPOMOFO) {
            fv.tvFather.setText(word);
        }else{
            fv.tvFather.setText(word+"画");
        }

        if (selectFatherPos == fatherIndex) {
            convertView.setBackgroundColor(Color.BLACK);
            fv.tvFather.setTextColor(Color.RED);
        }else{
            convertView.setBackgroundResource(R.color.grey);
            fv.tvFather.setTextColor(Color.BLACK);
        }
        return convertView;
    }

    @Override
    public View getChildView(int fatherIndex, int childIndex, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildView cv = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child,null);
            cv = new ChildView(convertView);
            convertView.setTag(cv);
        }else{
            cv = (ChildView) convertView.getTag();
        }
        txtMerge.resultMerge rm = childMsg.get(fatherIndex).get(childIndex);
        if (type == TYPE_BOPOMOFO) {
            cv.tvChild.setText(rm.getPinyin());
        }else{
            cv.tvChild.setText(rm.getBushou());
        }

        if (selectFatherPos == fatherIndex && selectChildPos == childIndex) {
            convertView.setBackgroundColor(Color.WHITE);
            cv.tvChild.setTextColor(Color.RED);
        }else{
            convertView.setBackgroundResource(R.color.grey);
            cv.tvChild.setTextColor(Color.BLACK);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int fatherIndex, int childIndex) {
        return true;
    }

    class FatherView{
        TextView tvFather;
        public FatherView(View view){
            tvFather = view.findViewById(R.id.item_father);
        }
    }
    class ChildView{
        TextView tvChild;
        public ChildView(View view){
            tvChild = view.findViewById(R.id.item_child);
        }
    }

}