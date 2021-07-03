package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView bopomofo, radical;
    EditText msgInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        bopomofo=findViewById(R.id.main_bopomofo);
        radical=findViewById(R.id.main_radical);
        msgInput=findViewById(R.id.main_msgInput);
    }

    public void onClick(View view) {
        Intent intent = new Intent();
        switch(view.getId()) {
            case R.id.main_collect:
                intent.setClass(this, CollectActivity.class);
                startActivity(intent);
                break;
            case R.id.main_search:
                String text = msgInput.getText().toString();
                if (!TextUtils.isEmpty(text)) {
                    intent.setClass(this,CharacterActivity.class);
                    intent.putExtra("zi",text);
                    startActivity(intent);
                }
                break;
            case R.id.main_bopomofo:
                intent.setClass(this, BopomofoActivity.class);
                startActivity(intent);
                break;
            case R.id.main_radical:
                intent.setClass(this, RadicalActivity.class);
                startActivity(intent);
                break;
        }
    }


}