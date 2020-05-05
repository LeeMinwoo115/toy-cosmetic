package com.example.hwahae.utils

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.hwahae.R
import com.squareup.picasso.Picasso

fun ViewGroup.inflate(layoutID : Int, attachRoot:Boolean=false):View{
    return LayoutInflater.from(context).inflate(layoutID,this,attachRoot)
}

fun ImageView.loadImg(imageUrl:String){
    if(TextUtils.isEmpty(imageUrl)){
        Picasso.get().load(R.mipmap.ic_launcher).into(this)
    }else{
        Picasso.get().load(imageUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .centerCrop()
            .fit()
            .into(this)
    }
}

fun <T> androidLazy(initializer: () -> T) : Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)