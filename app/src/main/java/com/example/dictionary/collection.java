package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.dictionary.database.operateDatabase;
import com.example.dictionary.CharacterActivity;
import java.util.ArrayList;
import java.util.List;

public class collection extends Fragment {
    private String type;
    GridView gv;
    List<String>mDatas;
    private ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_collection, container, false);
        Bundle bundle = getArguments();
        type = bundle.getString("type");
        gv = view.findViewById(R.id.zifrag_gv);
        mDatas = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), R.layout.character, R.id.item_character, mDatas);
        gv.setAdapter(adapter);
        setGVListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        List<String>list;
        mDatas.clear();
        list = operateDatabase.findMsgInCollect();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();;
    }

    private void setGVListener() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String zi = mDatas.get(position);
                    Intent intent = new Intent(getActivity(), CharacterActivity.class);
                    intent.putExtra("zi",zi);
                    startActivity(intent);
            }
        });
    }

}


