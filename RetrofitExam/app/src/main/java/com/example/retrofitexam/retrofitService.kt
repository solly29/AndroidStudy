package com.example.retrofitexam

import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

data class ResponseDTO(var id: String? = null, var pass: String? = null, var name: String? = null)

// api를 관리해주는 인터페이스
interface retrofitService {

    // get방식에는 query과 path가 있다.
    // query는 get방식에서 url뒤에 ?id=1 와 같이 데이터를 전송하는 매개변수를 설정한다.
    @GET("/selectTest.jsp")
    fun getRequest(@Query("id") id:String): Call<List<ResponseDTO>>

    // @Path은 경로를 동적 url으로 만드는데 사용한다.
    @GET("/{name}")
    fun getParamRequest(@Path("name") name: String, @Query("id") id:String): Call<List<ResponseDTO>>

    // post에는 formData
    // urlEncoding쓸때는 @formUrlEncoded를 써야됨
    // 보통 post으로 데이터를 등록한다.
    @FormUrlEncoded  // Field가 없으면 생략가능
    @POST("/insertTest.jsp")
    fun postRequest(@Field("jsontest") jsontest:JSONObject) : Call<String>

    // 멀티파트를 이용해서 이미지를 업로드할수있다.
    @Multipart
    @POST("/fileUploadAndroid.jsp")
    fun post_Image_Request(@Part("name") name: String, @Part("subject") subject: String,
        @Part imageFile : MultipartBody.Part) : Call<String>

    @FormUrlEncoded
    @PUT("/howl/{id}")
    fun putRequest(@Path("id") id: String, @Field("content") content: String): Call<ResponseDTO>

    @DELETE("/howl/{id}")
    fun deleteRequest(@Path("id") id : String) : Call<ResponseDTO>
}