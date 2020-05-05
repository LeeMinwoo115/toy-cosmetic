package com.example.hwahae.data

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface RetrofitNetwork {
    @GET("/prod/products")
    fun getTop(@QueryMap par : Map<String,String>):Call<CosmeticRepoList>

    @GET("/prod/products/{id}")
    fun getDetail(@Path("id") Id:String):Call<RepoInfoModel>

    @GET("/prod/products")
    fun getDeferredTop(@QueryMap par : Map<String,String>):Deferred<CosmeticRepoList>

}