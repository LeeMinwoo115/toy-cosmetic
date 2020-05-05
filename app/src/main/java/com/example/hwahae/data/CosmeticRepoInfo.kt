package com.example.hwahae.data

import com.google.gson.annotations.SerializedName

class RepoInfoModel{
    @SerializedName("statusCode")
    val statusCode:Int = 404

    @SerializedName("body")
    val body : CosmeticRepoInfo = CosmeticRepoInfo()
}

class CosmeticRepoInfo{
    @SerializedName("id")
    val id :Int= 0

    @SerializedName("full_size_image")
    val full_size_image = ""

    @SerializedName("description")
    val description = ""

    @SerializedName("oily_score")
    val oily_score = 0

    @SerializedName("dry_score")
    val dry_score = 0

    @SerializedName("sensitive_score")
    val sensitive_score = 0
}