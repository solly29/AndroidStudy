package com.example.intenttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {

    private TextView tv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tv_test = findViewById(R.id.tv_test);

        Intent intent = getIntent();
        String str = intent.getStringExtra("str");//아까 적은 별명을 적는다.

        tv_test.setText(str);
    }
}
