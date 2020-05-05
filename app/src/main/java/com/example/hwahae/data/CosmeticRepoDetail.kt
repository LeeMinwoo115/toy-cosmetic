package com.example.hwahae.data

import android.renderscript.Sampler
import com.google.gson.annotations.SerializedName
import retrofit2.http.Path

class CosmeticRepoDetail {
    @SerializedName("id")
    val id : Int = 0

    @SerializedName("thumbnail_image")
    val thumbnail_image:String=""

    @SerializedName("title")
    val title:String= ""

    @SerializedName("price")
    val price : String = ""

    @SerializedName("oily_score}")
    val oily_score = 0

    @SerializedName("dry_score")
    val dry_score = 0

    @SerializedName("sensitive_score")
    val sensitive_score = 0

}