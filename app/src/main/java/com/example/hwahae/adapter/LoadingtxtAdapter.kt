package com.example.hwahae.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hwahae.R
import com.example.hwahae.utils.inflate

class LoadingtxtAdapter:ItemAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return LoadingTxtHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
    }

    class LoadingTxtHolder(parent: ViewGroup):RecyclerView.ViewHolder(parent.inflate(R.layout.loading_txt_view))

}