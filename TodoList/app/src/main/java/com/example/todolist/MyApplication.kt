package com.example.todolist

import android.app.Application
import io.realm.Realm

class MyApplication: Application() {
    override fun onCreate() { // 액티비티가 생성되기 전에 호출된다.
        super.onCreate()
        Realm.init(this) // Realm을 초기화시킨다. 그래야 다른 액티비티가 사용이 가능하다.
    }
}