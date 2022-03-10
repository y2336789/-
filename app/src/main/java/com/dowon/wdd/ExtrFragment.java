package com.dowon.wdd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;

public class ExtrFragment extends Fragment {

    MainActivity activity;

    EditText editText;
    ListView listView;

    ArrayAdapter<String> adapter;
    ArrayList<String> listItem;

    Komoran komoran;

    private FirebaseFirestore db;
    private List<Word> words = new ArrayList<>();

    public ExtrFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context){ // 시작할때 자동으로 실행됨.
        super.onAttach(context);

        activity = (MainActivity) getActivity();
        komoran = ((MainActivity)getActivity()).komoran;
    }

    @Override
    public void onDetach(){
        super.onDetach();
        activity = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_extract_word, container, false);

        //         어뎁터 준비
        listItem = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(activity.getApplicationContext(), android.R.layout.simple_list_item_1, listItem);
        // 뷰에 어뎁터 연결
        listView = rootView.findViewById(R.id.result);
        listView.setAdapter(adapter);

        editText = rootView.findViewById(R.id.editText);

        Button button = rootView.findViewById(R.id.input_btn);

        db = FirebaseFirestore.getInstance();

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

                    DocumentReference exist = db.collection("word").document(string);

                    exist.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    listItem.add(string);
                                    adapter.notifyDataSetChanged();
                                } else {
                                    Log.d("test2", "Document does not exist!");
                                }
                            } else {
                                Log.d("test2", "get failed with  ", task.getException());
                            }
                        }
                    });
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Toast.makeText(MainActivity.this,   adapterView.getAdapter().getItem(position).toString(),Toast.LENGTH_SHORT).show();

//                activity.replaceFragment(WordResultFragment.newInstance()); //테스트중 220216

                Intent intent = new Intent(activity.getApplicationContext(), WordResult.class);

                intent.putExtra("word", adapterView.getAdapter().getItem(position).toString());
                startActivity(intent);
            }
        });

        return rootView;
    }
}