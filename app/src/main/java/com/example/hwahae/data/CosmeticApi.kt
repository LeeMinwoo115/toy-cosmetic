package com.example.hwahae.data

import com.example.hwahae.CosmeticList
import retrofit2.Call

interface CosmeticApi {
    fun getCosmeticListNM(param: Map<String, String>):Call<CosmeticRepoList>

    fun getCosmeticDetailInfo(param:String):Call<RepoInfoModel>

    suspend fun getCosmeticListCR(param:Map<String,String>):CosmeticRepoList
}