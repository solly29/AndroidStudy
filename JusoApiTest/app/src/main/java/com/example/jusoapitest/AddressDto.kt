package com.example.jusoapitest

import com.google.gson.annotations.SerializedName

data class AddressDto(
    @SerializedName("results") val results: Results
) {
    data class Results(
        @SerializedName("common") val common: Common,
        @SerializedName("juso") val juso: List<Juso>
    ){
    }

    data class Common(
        @SerializedName("errorMessage") val errorMessage: String,
        @SerializedName("countPerPage") val countPerPage: Int,
        @SerializedName("currentPage") val currentPage: Int
    ){}

    data class Juso(
        @SerializedName("roadAddr") val roadAddr: String,
        @SerializedName("roadAddrPart1") val roadAddrPart1: String
    ){}
}