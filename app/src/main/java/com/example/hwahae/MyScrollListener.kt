package com.example.hwahae

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MyScrollListener (val func : ()->Unit,val layoutManager:GridLayoutManager) : RecyclerView.OnScrollListener(){

    private var previousTotal = 0
    private var loading = true
    private var visibleThreshold = 2
    private var firstVisibleItem = 0
    private var visibleItemCnt = 0
    private var totalItemCnt = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        recyclerView.canScrollVertically(1)
        if(dy>0){
            visibleItemCnt = recyclerView.childCount
            totalItemCnt = layoutManager.itemCount
            firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

            if(loading){
                if(totalItemCnt>previousTotal){
                    loading = false
                    previousTotal = totalItemCnt
                }
            }
            if(!loading && (totalItemCnt - visibleItemCnt)
            <= (firstVisibleItem+visibleThreshold)){
                func()
                loading = true
            }
        }
    }
}