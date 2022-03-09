package com.dowon.wdd;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

public class Initial_Fragment extends Fragment {
    // 리스트뷰에 아이템 추가할려면 LIST_MENU에 "추가할 내용" 해주면 됨
    static final String[] LIST_MENU = {"LIST1", "LIST2", "LIST3"};
    public Initial_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_initial, null);

        // https://recipes4dev.tistory.com/42
        // String 배열을 입력으로 받으니 ArrayAdapter 사용
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, LIST_MENU);

        ListView listView = (ListView) view.findViewById(R.id.chosung_listview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                String chosung = (String)parent.getAdapter().getItem(position);
                Intent intent = new Intent(getActivity(), InitialActivity.class);
                intent.putExtra("chosung", chosung);
                startActivity(intent);
            }
        });

        return view;
    }
}