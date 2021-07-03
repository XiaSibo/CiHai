package com.example.dictionary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dictionary.R;
import com.example.dictionary.merge.ChineseCharacter;

import java.util.List;

public class CharacterAdapter extends BaseAdapter {
    Context context;
    List<ChineseCharacter.resultMsg.character> cData;
    LayoutInflater inflater;
    public CharacterAdapter(Context context, List<ChineseCharacter.resultMsg.character> cData) {
        this.context = context;
        this.cData = cData;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return cData.size();
    }

    @Override
    public Object getItem(int position) {
        return cData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CharacterView cv = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.character,null);
            cv = new CharacterView(convertView);
            convertView.setTag(cv);
        }else{
            cv = (CharacterView) convertView.getTag();
        }
        ChineseCharacter.resultMsg.character bean = cData.get(position);
        String zi = bean.getZi();
        cv.tv.setText(zi);
        return convertView;
    }

    class CharacterView{
        TextView tv;
        public CharacterView(View view){
            tv = view.findViewById(R.id.item_character);
        }
    }
}

