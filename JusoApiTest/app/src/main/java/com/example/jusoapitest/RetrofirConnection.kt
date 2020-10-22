package com.example.jusoapitest

import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.internal.JavaNetCookieJar
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit

object RetrofirConnection {
    private var instance: RetrofitService? = null
    private val gson = GsonBuilder().setLenient().create()

    fun setInstance(){
        val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl("http://www.juso.go.kr/addrlink/")
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))


        // 통신 중 일어나는 로그를 인터셉트하는 Interceptor
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
        builder.interceptors().add(logInterceptor)

        // 서버는 클라에 쿠키 id를 가지고 세션을 할단하기 때문에 앱에서 쿠키를 관리하고 저장해야된다.
        val cookieHandler = CookieManager() // 웹뷰를 사용하지 않은 앱에서는 쿠캐 매니저를 이용해 쿠키를 관리해야된다.
        cookieHandler.setCookiePolicy(CookiePolicy.ACCEPT_ALL)

        val client = builder.cookieJar(JavaNetCookieJar(cookieHandler))// 쿠키관리를 쿠키 매니저에게 위임
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        retrofitBuilder.client(client)

        val retrofit = retrofitBuilder.build()
        instance = retrofit.create(RetrofitService::class.java)
    }

    fun getInstance() = instance
}