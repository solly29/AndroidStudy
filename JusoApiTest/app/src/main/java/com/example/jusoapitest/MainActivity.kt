package com.example.jusoapitest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scope.launch {
            try {
                val responseBody = RetrofirConnection.getInstance()?.getAddressRequest("키넣는곳",1, countPerPage = 5, keyword = "경주", resultType = "json")
                Log.e("response", (responseBody as AddressDto).results.juso[0].toString())
            }catch (e: Exception){
                Log.e("retrofit",e.toString())
            }
        }
    }
}