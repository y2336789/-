package com.dowon.wdd;

import androidx.appcompat.app.AppCompatActivity;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    ListView listView;

    ArrayAdapter<String> adapter;
    ArrayList<String> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listItem = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listItem);
        listView = findViewById(R.id.result);
        listView.setAdapter(adapter);

        editText = findViewById(R.id.editText);

        Button button = findViewById(R.id.input_btn);

        InputStream is = null;
        FileOutputStream fos = null;

        try {
            is = getAssets().open("dic.user");
            int size = is.available();
            byte[] buffer = new byte[size];
            File outfile = new File("/data/data/com.dowon.wdd/dic.user");
            fos = new FileOutputStream(outfile, false);
            for (int c = is.read(buffer); c != -1; c = is.read(buffer)){
                fos.write(buffer, 0, c);
            }
            is.close();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        Komoran komoran = new Komoran(DEFAULT_MODEL.LIGHT);
        komoran.setUserDic("/data/data/com.dowon.wdd/dic.user");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItem.clear();
                adapter.notifyDataSetChanged();

                String strToAnalyze = editText.getText().toString();
                KomoranResult analyzeResultList = komoran.analyze(strToAnalyze);

                List<String> NNPList = analyzeResultList.getMorphesByTags("NNP");

                for (String string : NNPList) {
                    Log.d("check4", string);
                    listItem.add(string);
                    adapter.notifyDataSetChanged();
                }
            }
        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                  요 안에서 Intent로 단어를 DB로 넘겨서 해당 단어의 정보가 나오도록 코드 작성
//            }
//        });
    }
}