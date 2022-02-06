package com.dowon.wdd;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class WordResult extends AppCompatActivity {

    TextView textView, textView2, textView3;
    String mean, eng_name;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_result);

        textView = findViewById(R.id.result_name);
        textView2 = findViewById(R.id.result_mean);

        Intent intent = getIntent();
        String word = intent.getExtras().getString("word");
        textView.setText(word);


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



    }
}
