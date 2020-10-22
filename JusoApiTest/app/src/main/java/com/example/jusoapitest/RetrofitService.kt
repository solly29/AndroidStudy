package com.example.jusoapitest

import com.google.gson.JsonArray
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService{
    //val key: String get() = "devU01TX0FVVEgyMDIwMTAyMjE3NDkxNjExMDMxOTg"

    @GET("addrLinkApi.do")
    suspend fun getAddressRequest(
        @Query("confmKey") confmKey:String,
        @Query("currentPage") currentPage: Int,
        @Query("countPerPage") countPerPage: Int,
        @Query("keyword") keyword: String,
        @Query("resultType") resultType: String? = null
    ): AddressDto
}