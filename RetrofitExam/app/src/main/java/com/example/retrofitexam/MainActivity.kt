package com.example.retrofitexam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //172.30.1.24:8080

        val retrofit = Retrofit.Builder()
            .baseUrl("http://172.30.1.24:8080")
            .addConverterFactory(ScalarsConverterFactory.create()) // 응답을 String으로 받으려고
            .addConverterFactory(GsonConverterFactory.create())    // 응답을 json으로 받아서 gson라이브러리가 자바형태로 직렬화, 역직렬화해서 사용하는데
                                                                   // retrofit이 gson라이브러리를 사용하기 때문에 이것을 자동으로 해준다.
            .build()

        val server = retrofit.create(retrofitService::class.java)

        // select
        button_get.setOnClickListener {
            server.getRequest("1").enqueue(object:Callback<List<ResponseDTO>>{
                override fun onFailure(call: Call<List<ResponseDTO>>, t: Throwable) {
                    // object 모델이 ResponseDTO가 아니면 여기로온다.
                    println(t)
                }

                override fun onResponse(call: Call<List<ResponseDTO>>, response: Response<List<ResponseDTO>>) {
                    println(response.body().toString())
                }
            })
        }

        button_get_param.setOnClickListener {
            // 괄호안에는 경로를 넣는다. 그 경로로가서 결과값을 받아온다.
            server.getParamRequest("selectTest.jsp","2").enqueue((object:Callback<List<ResponseDTO>>{
                override fun onFailure(call: Call<List<ResponseDTO>>, t: Throwable) {

                }

                override fun onResponse(call: Call<List<ResponseDTO>>, response: Response<List<ResponseDTO>>) {
                    println(response.body().toString())
                }
            }))
        }

        // 디비에 값을 insert
        button_post.setOnClickListener {
            server.postRequest("5","test").enqueue(object : Callback<String>{
                override fun onFailure(call: Call<String>, t: Throwable) {
                    println(t)
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    println(response.body().toString())
                }
            })
        }

        button_update.setOnClickListener {
            server.putRequest("board01", "수정할 내용").enqueue(object : Callback<ResponseDTO>{
                override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {

                }

                override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                    println(response.body().toString())
                }
            })
        }

        button_delete.setOnClickListener {
            server.deleteRequest("board01").enqueue(object: Callback<ResponseDTO>{
                override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {

                }

                override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                    println(response.body().toString())
                }
            })
        }
    }
}
