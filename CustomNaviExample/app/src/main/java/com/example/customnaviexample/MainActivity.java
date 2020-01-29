package com.example.customnaviexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        drView = (View)findViewById(R.id.drawer);//메뉴 바

        Button but_open = (Button)findViewById(R.id.but_open);
        but_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drView); //해당 뷰를 열어라
            }
        });

        drawerLayout.setDrawerListener(listener);//드레그를 했을때 이벤트
        drView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        Button btn_close = (Button)findViewById(R.id.but_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });

    }

    //슬라이드 했을때 이벤트
    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {//슬라이드 했을때

        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {//오픈 됬을때

        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {//닫혔을때

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };
}
