package com.example.retrofitexam

import retrofit2.Call
import retrofit2.http.*

data class ResponseDTO(var id: String? = null, var pass: String? = null, var name: String? = null)

// api를 관리해주는 인터페이스
interface retrofitService {

    // get방식에는 query과 path가 있다.
    @GET("/selectTest.jsp")
    fun getRequest(@Query("id") id:String): Call<List<ResponseDTO>>

    @GET("/test.jsp")
    fun getParamRequest(@Path("msg") msg: String): Call<ResponseDTO>

    // post에는 formData
    // urlEncoding쓸때는 @formUrlEncoded를 써야됨
    @FormUrlEncoded
    @POST("/selectTest.jsp")
    fun postRequest(@Field("id") id:String) : Call<List<ResponseDTO>>
    @FormUrlEncoded // Field가 없으면 생략가능
    @PUT("/howl/{id}")
    fun putRequest(@Path("id") id: String, @Field("content") content: String): Call<ResponseDTO>

    @DELETE("/howl/{id}")
    fun deleteRequest(@Path("id") id : String) : Call<ResponseDTO>
}