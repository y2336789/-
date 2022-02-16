package com.dowon.wdd;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class WordResultFragment extends Fragment { // 테스트 중 220216

    MainActivity activity;

    TextView textView, textView2, textView3;
    String mean, eng_name;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public WordResultFragment() {
        // Required empty public constructor
    }

    public static WordResultFragment newInstance() {
        return new WordResultFragment();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        activity = (MainActivity) getActivity();
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_word_result, container, false);

        textView = rootView.findViewById(R.id.result_name);
        textView2 = rootView.findViewById(R.id.result_mean);

        String word = "어쩔티비";
        textView.setText(word);
//        Intent intent = activity.getIntent();
//        String word = intent.getExtras().getString("word");
//        textView.setText(word);


        DocumentReference docRef = db.collection("word").document(word);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("test1", "DocumentSnapshot data : " + document.getData());
                        Log.d("test1", "mean : " + document.getString("mean"));
                    } else {
                        Log.d("test1", "failed");
                    }
                    mean = document.getString("mean");
//                  eng_name = document.getString("eng");
//                  Log.d("test2", mean);
                    textView2.setText(mean);
//                  textView3.setText(eng_name);
                } else {
                    Log.d("test1", "get failed with ", task.getException());
                }
            }
        });
//        Log.d("test2", mean);





        return rootView;
    }
}