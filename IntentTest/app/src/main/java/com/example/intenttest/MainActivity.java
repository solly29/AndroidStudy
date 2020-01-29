package com.example.intenttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button but1;
    private EditText et_test;
    private String str;
    private ImageView imgTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        but1 = findViewById(R.id.but1);
        et_test = findViewById(R.id.et_test);
        imgTest = findViewById(R.id.imgTest);

        imgTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getApplicationContext() 메인에 뜬다.
                Toast.makeText(getApplicationContext(),"이미지를 클릭함", Toast.LENGTH_SHORT).show();
            }
        });

        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //현제 엑티비티에서 이동할 엑티비티
                str = et_test.getText().toString();
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                intent.putExtra("str", str);//별명, 실제 변수 및 값
                startActivity(intent); //엑티비티 이동
            }
        });
    }
}
