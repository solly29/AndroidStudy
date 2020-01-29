package com.example.listexample01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.list);

        List<String> data = new ArrayList<>();

        //리스트랑 리스트 뷰를 연결해주는것이 어뎁터라고한다.
        //android.R.layout.simple_list_item_1 은 안드로이드에서 제공하는 리스트 폼(ui, 디자인)
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        list.setAdapter(adapter);//어뎁터 세팅하기.

        //데이터 추가
        data.add("안드로이드");
        data.add("홍드로이드");
        data.add("심심");
        adapter.notifyDataSetChanged();//저장 완료하기
    }
}
 