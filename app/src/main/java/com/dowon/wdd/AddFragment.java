package com.dowon.wdd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.firebase.firestore.FirebaseFirestore;

// https://ddolcat.tistory.com/452
// 프레그먼트 버튼 온클릭 설정하는 법
public class AddFragment extends Fragment {
    EditText editText;
    Button button;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public AddFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_word, container, false);

        button = v.findViewById(R.id.req_btn);
        button.setOnClickListener(this::onClick);

        editText = v.findViewById(R.id.req_content);

        return v;
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.req_btn:
            {
                Request request = new Request(editText.getText().toString());
                db.collection("request").document()
                        .set(request);
                Toast.makeText(getActivity(), "요청이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                // 간단하게 요청만 받을 수 있게함
            }
        }
    }


}