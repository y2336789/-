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
import java.util.ArrayList;
import java.util.List;
import java.io.FileOutputStream;

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

        Komoran komoran = new Komoran(DEFAULT_MODEL.LIGHT);

//        FileOutputStream fos;
//
//        String strFileContents = "핼로우";
//
//        try {
//            fos = openFileOutput("dic2.user", MODE_PRIVATE);
//            fos.write(strFileContents.getBytes());
//            fos.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        komoran.setUserDic("/data/data/com.dowon.wdd/files/dic2.user");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItem.clear();
                adapter.notifyDataSetChanged();

                String strToAnalyze = editText.getText().toString();
                KomoranResult analyzeResultList = komoran.analyze(strToAnalyze);

                List<String> NNPList = analyzeResultList.getMorphesByTags("NNP");

                for (String string : NNPList) {
//                    Log.d("check4", string);
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