package com.example.baseactivitytest

import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("test", "test")
        Toast.makeText(this,"test",Toast.LENGTH_SHORT).show()
    }
}