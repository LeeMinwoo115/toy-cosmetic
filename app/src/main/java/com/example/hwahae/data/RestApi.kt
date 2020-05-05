package com.example.hwahae.data

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://6uqljnm1pb.execute-api.ap-northeast-2.amazonaws.com"

class RestApi {
    val service : RetrofitNetwork

    init {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build();

        service = retrofit.create(RetrofitNetwork::class.java)
    }

    fun getListRetrofit(param : Map<String,String>):Call<CosmeticRepoList>{
        return service.getTop(param)
    }

    fun getDetailItem(param : String):Call<RepoInfoModel>{
        return service.getDetail(param)
    }

    suspend fun getListRetrofitCR(param: Map<String, String>):CosmeticRepoList{
        return service.getDeferredTop(param).await()
    }
}