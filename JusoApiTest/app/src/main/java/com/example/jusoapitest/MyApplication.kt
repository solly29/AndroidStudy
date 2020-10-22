package com.example.jusoapitest

import android.app.Application

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        RetrofirConnection.setInstance()
    }
}