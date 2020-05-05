package com.example.hwahae.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.Filter
import android.widget.Filterable
import com.example.hwahae.Cosmetic
import com.example.hwahae.R
import com.example.hwahae.utils.inflate
import com.example.hwahae.utils.loadImg
import kotlin.collections.ArrayList

class CosmeticAdapter(val context: Context,val itemClick : (Cosmetic)->Unit) : ItemAdapter{

    var cosmeticList = mutableListOf<Cosmetic>()

    inner class Holder(parent: ViewGroup, itemClick: (Cosmetic) -> Unit) : RecyclerView.ViewHolder(parent.inflate(R.layout.comestic_item)){

        val img = itemView.findViewById<ImageView>(R.id.imageView)
        val name = itemView.findViewById<TextView>(R.id.textView_name)
        val price = itemView.findViewById<TextView>(R.id.textView_price)

        fun bind(cosmetic: Cosmetic,context: Context){

            img.loadImg(cosmetic.img)
            name.text = cosmetic.name
            price.text = cosmetic.price

            super.itemView.setOnClickListener{ itemClick(cosmetic) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return Holder(parent,itemClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as Holder
        holder.bind(item as Cosmetic,context)
    }


}