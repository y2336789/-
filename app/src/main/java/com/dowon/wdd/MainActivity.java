package com.dowon.wdd;

import androidx.appcompat.app.AppCompatActivity;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
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


    private FirebaseFirestore db;
    private List<Word> words = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//         어뎁터 준비
        listItem = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listItem);
        // 뷰에 어뎁터 연결
        listView = findViewById(R.id.result);
        listView.setAdapter(adapter);

        editText = findViewById(R.id.editText);

        Button button = findViewById(R.id.input_btn);

        InputStream is = null;
        FileOutputStream fos = null;

        db = FirebaseFirestore.getInstance();

//        파이어베이스 연동하고 컬렉션에 문서 저장하기
//        Word word = new Word("test3","test","테스트2");
//        db.collection("word").document("test")
//                .set(word)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Log.d("check3", "저장 성공");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d("check3", "저장 실패");
//                    }
//                });

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Toast.makeText(MainActivity.this,   adapterView.getAdapter().getItem(position).toString(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), WordResult.class);

                intent.putExtra("word", adapterView.getAdapter().getItem(position).toString());
                startActivity(intent);
            }
        });
    }
}

//        파이어베이스 연동하고 컬렉션에 문서 저장하기
//        Word word = new Word("hi","무야호","몰라");
//        db.collection("word").document("2")
//                .set(word)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Log.d("check3", "저장 성공");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d("check3", "저장 실패");
//                    }
//                });

//        사용자 지정 사전 없이 실행할 수 있도록 하는 코드
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