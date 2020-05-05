package com.example.hwahae.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.hwahae.Cosmetic

class CombineAdapter(val context: Context,val itemClick : (Cosmetic)->Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),Filterable {

    private var isSearch = false
    private var items : ArrayList<ViewType>
    private var searchList : List<Cosmetic>?=null

    private var delegateAdapters = SparseArrayCompat<ItemAdapter>()

    private val loadingItem = object : ViewType{
        override fun getViewType(): Int {
            return AdapterType.LOADING
        }

    }

    private val loadingTxtItem = object :ViewType{
        override fun getViewType(): Int {
            return AdapterType.LOAD_TXT
        }

    }

    init {
        delegateAdapters.put(AdapterType.LOAD_TXT,LoadingtxtAdapter())
        delegateAdapters.put(AdapterType.LOADING,LoadingItemAdapter())
        delegateAdapters.put(AdapterType.COSMETIC,CosmeticAdapter(context,itemClick))
        items = ArrayList()
        items.add(loadingTxtItem)
        items.add(loadingItem)
    }
    fun setFilteredList(){
        this.searchList = getItemList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType)!!.onCreateViewHolder(parent)
    }

    override fun getItemCount(): Int {
        if(isSearch)    return searchList!!.size
        else            return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(!isSearch){
            delegateAdapters.get(getItemViewType(position))?.onBindViewHolder(holder,items[position])
        }else{
            delegateAdapters.get(getItemViewType(position))?.onBindViewHolder(holder,searchList!![position])
        }
    }

    override fun getFilter(): Filter {
        isSearch=true
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                if(charString.isEmpty()){
                    searchList = getItemList()
                }else{
                    val filteredList = ArrayList<Cosmetic>()
                    for(elem in getItemList()){
                        if(elem.name.contains(charString.toLowerCase())){
                            filteredList.add(elem)
                        }
                    }

                    searchList = filteredList
                }

                val filteredResults = FilterResults()
                filteredResults.values = searchList
                return filteredResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                searchList= results?.values as ArrayList<Cosmetic>
                notifyDataSetChanged()
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].getViewType()
    }

    fun addItemList(itemList: List<Cosmetic>) {
        val initPosition = items.size - 1
        items.removeAt(initPosition)
        items.removeAt(initPosition-1)
        notifyItemRangeRemoved(initPosition-1,initPosition)

        items.addAll(itemList)
        items.add(loadingTxtItem)
        items.add(loadingItem)
        notifyItemRangeChanged(initPosition-1, items.size+2)
        isSearch =false
    }

    fun clearAddItemList(itemList: List<Cosmetic>) {
        items.clear()
        notifyItemRangeRemoved(0, getLastPosition())

        items.addAll(itemList)
        items.add(loadingTxtItem)
        items.add(loadingItem)
        notifyItemRangeInserted(0, items.size)
        isSearch =false
    }

    fun getItemList(): List<Cosmetic> = items
        .filter { it.getViewType() == AdapterType.COSMETIC }
        .map { it as Cosmetic }

    private fun getLastPosition() = if (items.lastIndex <= -1) 0 else items.lastIndex
}