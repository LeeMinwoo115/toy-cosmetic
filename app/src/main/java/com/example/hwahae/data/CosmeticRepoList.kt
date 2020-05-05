package com.example.hwahae.data

import com.google.gson.annotations.SerializedName

class CosmeticRepoList {
    @SerializedName("statusCode")
    var statusCode:Int = 404

    @SerializedName("page")
    val page = 1

    @SerializedName("scanned_count")
    val scanned_count = 20

    @SerializedName("body")
    val body : List<CosmeticRepoDetail> = listOf()
}