package com.dowon.wdd;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;

// https://ddolcat.tistory.com/452
// 프레그먼트 버튼 온클릭 설정하는 법
public class AddFragment extends Fragment {
    Button button;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_word, container, false);

        button = v.findViewById(R.id.req_btn);
        button.setOnClickListener(this::onClick);
        return v;
//        return inflater.inflate(R.layout.fragment_add_word, container, false);
    }

    // @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.req_btn:
            {
                Log.d("test1", "테스트입니다");
                // 아직 화면만 만들고 만들진 않음
                // 파베랑 연결해서 단어 등록 요청을 받을 예정임
            }
        }
    }
}