package com.example.hwahae.data

import retrofit2.Call
import javax.inject.Inject

class CosmeticRestApi @Inject constructor(private val api:RetrofitNetwork):CosmeticApi{
    override fun getCosmeticListNM(param: Map<String, String>): Call<CosmeticRepoList> {
        return api.getTop(param)
    }

    override fun getCosmeticDetailInfo(param: String): Call<RepoInfoModel> {
        return api.getDetail(param)
    }

    override suspend fun getCosmeticListCR(param: Map<String, String>): CosmeticRepoList {
        return api.getDeferredTop(param).await()
    }
}