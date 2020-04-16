package com.example.retrofitexam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //172.30.1.24:8080

        var retrofit = Retrofit.Builder()
            .baseUrl("http://172.30.1.24:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var server = retrofit.create(retrofitService::class.java)

        button_get.setOnClickListener {
            server.getRequest("aaaa").enqueue(object:Callback<List<ResponseDTO>>{
                override fun onFailure(call: Call<List<ResponseDTO>>, t: Throwable) {
                    // object 모델이 ResponseDTO가 아니면 여기로온다.
                    println(t)
                }

                override fun onResponse(call: Call<List<ResponseDTO>>, response: Response<List<ResponseDTO>>) {
                    println(response?.body().toString())
                }
            })
        }

        button_get_param.setOnClickListener {
            // 괄호안에는 경로를 넣는다. 그 경로로가서 결과값을 받아온다.
            server.getParamRequest("board01").enqueue((object:Callback<ResponseDTO>{
                override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {

                }

                override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                    println(response?.body().toString())
                }
            }))
        }

        button_post.setOnClickListener {
            server.postRequest("you6878@incloud.com").enqueue(object : Callback<ResponseDTO>{
                override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {

                }

                override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                    println(response?.body().toString())
                }
            })
        }

        button_update.setOnClickListener {
            server.putRequest("board01", "수정할 내용").enqueue(object : Callback<ResponseDTO>{
                override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {

                }

                override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                    println(response?.body().toString())
                }
            })
        }

        button_delete.setOnClickListener {
            server.deleteRequest("board01").enqueue(object: Callback<ResponseDTO>{
                override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {

                }

                override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                    println(response?.body().toString())
                }
            })
        }
    }
}
