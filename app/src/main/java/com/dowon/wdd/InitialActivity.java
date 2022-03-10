package com.dowon.wdd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
// https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=cosmosjs&logNo=221050368244
public class InitialActivity extends AppCompatActivity {
    // 초성별로 나열되는 단어 목록들을 확인할 수 있는 액티비티
    List<String> WordList = new ArrayList<String>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial);

        ListView listView = findViewById(R.id.initial_result);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, WordList);
        listView.setAdapter(adapter1);

        Intent intent = getIntent();
        String initial = intent.getExtras().getString("initial").toString();

        CollectionReference productRef = db.collection(initial);
        productRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //작업이 성공적으로 마쳤을때
                if (task.isSuccessful()) {
                    //컬렉션 아래에 있는 모든 정보를 가져온다.
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //document.getData() or document.getId() 등등 여러 방법으로 데이터를 가져올 수 있다.
                        // 모든 문서 이름들을 가져오는 코드
                        // Log.d("test2", document.getId().toString());
                        WordList.add(document.getId());
                    }
                    adapter1.notifyDataSetChanged();
                } else {

                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String word = (String)adapterView.getAdapter().getItem(position);
                Intent intent = new Intent(getApplicationContext(), WordResult.class);
                intent.putExtra("word", word);
                startActivity(intent);
            }
        });
    }
}
