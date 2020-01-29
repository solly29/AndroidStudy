package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText Et_id;
    Button but_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//가장 처음 실행됨
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//activity_main.xml

        Et_id = findViewById(R.id.Et_id); //엑티비티에 있는 해당 id와 연결
        but_test = findViewById(R.id.but_test);

        but_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Et_id.setText("확인");
            }
        });
    }
}
