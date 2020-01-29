package com.example.sharedexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText et_save;
    private String shared = "file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_save = (EditText) findViewById(R.id.et_save);

        //앱을 종료했을때도 데이터를 저장(설정 화면에서 많이 쓴다.)
        //앱을 삭제하면 없어진다.
        SharedPreferences sharedPreferences = getSharedPreferences(shared, 0);
        String value = sharedPreferences.getString("home","");//불러오기
        et_save.setText(value);
    }

    @Override
    protected void onDestroy() {//앱(엑티비티)을 종료했을때 실행
        super.onDestroy();

        SharedPreferences sharedPreferences = getSharedPreferences(shared, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();//sharedPreferences와 연결
        String value = et_save.getText().toString();
        editor.putString("home",value);//저장하기(별명(키), 값)
        editor.commit();//세이브 완료
    }
}
