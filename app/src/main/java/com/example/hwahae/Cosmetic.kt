package com.example.hwahae

import android.os.Parcel
import android.os.Parcelable
import com.example.hwahae.adapter.AdapterType
import com.example.hwahae.adapter.ViewType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CosmeticList(
    var page:Int?,
    var body : List<Cosmetic>
) : Parcelable{}

@Parcelize
data class Cosmetic(val ID:Int,val img: String, val name: String, val price: String,val oily_score:Int
    ,val dry_score:Int, val sensitive_score:Int) : Parcelable,ViewType{
    override fun getViewType(): Int {
        return AdapterType.COSMETIC
    }

}