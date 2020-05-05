package com.example.hwahae

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hwahae.adapter.AdapterType
import com.example.hwahae.adapter.CombineAdapter
import com.example.hwahae.adapter.CosmeticAdapter
import com.example.hwahae.data.CosmeticApi
import com.example.hwahae.data.CosmeticRepoList
import com.example.hwahae.data.RestApi
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
class MainActivity : AppCompatActivity() {

    val REQUEST_CODE = 101;
    var searchView : SearchView?=null
    val spinner_list : List<String> = mutableListOf("모든 피부 타입","지성","건성","민감성")
    var type : String= ""
    var url_map = mutableMapOf<String,String>()
    var m_adapter : CombineAdapter? = null
    var isType = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*tool bar 적용시키기*/
        setSupportActionBar(toolbar)

        /*스피너 - 선택 항목*/
        val spinner_adapter = ArrayAdapter(this,R.layout.view_spinner_item,spinner_list)
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinner_adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var isType = false

                UrlConnectToBuildMap(position)

                apiConnect(url_map,isType,position)
            }

        }


    }

    /*목록 선택시 처리*/
    fun UrlConnectToBuildMap(position : Int) {
        when(position){
            0->{url_map.clear()}

            1->{type = "oily"
                url_map.remove("skin_type")
                url_map.put("skin_type",type)
                isType=true}

            2->{type = "dry"
                url_map.remove("skin_type")
                url_map.put("skin_type",type)
                isType=true}

            3->{type = "sensitive"
                url_map.remove("skin_type")
                url_map.put("skin_type",type)
                isType=true}
        }
    }

    /*웹과의 연결*/
    fun apiConnect(param : MutableMap<String,String>,isType:Boolean,position:Int){
        var api : RestApi = RestApi()
        api.getListRetrofit(param).enqueue(object : Callback<CosmeticRepoList>{
            override fun onFailure(call: Call<CosmeticRepoList>, t: Throwable) {
                Log.d("MainActivity",t.localizedMessage)
                Toast.makeText(applicationContext,"Error ${t.localizedMessage}",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<CosmeticRepoList>,
                response: Response<CosmeticRepoList>
            ) {
                var itemList = ArrayList<Cosmetic>()

                for(item in response.body()!!.body){
                    itemList.add(Cosmetic(item.id,item.thumbnail_image,item.title,
                        NumberFormat.getInstance().format(item.price.toInt())+"원",
                        item.oily_score,item.dry_score,item.sensitive_score))
                }

                /*어댑터 연결, 목록 클릭 이벤트*/
                m_adapter =
                    CombineAdapter(this@MainActivity) { cosmetic ->
                        val InfoIntent = Intent(this@MainActivity, InfoActivity::class.java)
                        InfoIntent.putExtra("id",cosmetic.ID.toString())
                        InfoIntent.putExtra("name", "${cosmetic.name}")
                        InfoIntent.putExtra("price", "${cosmetic.price}")
                        startActivityForResult(InfoIntent, REQUEST_CODE)
                        overridePendingTransition(R.anim.up_animation, R.anim.fadeout_animation)
                    }

                /*피부 타입별로 타입 점수가 높은 순으로 정렬*/
                if(isType==true){
                    when(position){
                        1->{
                            itemList.sortedWith(Comparator<Cosmetic>{ small,big->
                                when{
                                    small.oily_score<big.oily_score ->1
                                    small.oily_score>big.oily_score ->-1
                                    else->0
                                }
                            })
                        }

                        2->{
                            itemList.sortedWith(Comparator<Cosmetic>{ small,big->
                                when{
                                    small.dry_score<big.dry_score ->1
                                    small.dry_score>big.dry_score ->-1
                                    else->0
                                }
                            })
                        }

                        3->{
                            itemList.sortedWith(Comparator<Cosmetic>{ small,big->
                                when{
                                    small.sensitive_score<big.sensitive_score ->1
                                    small.sensitive_score>big.sensitive_score ->-1
                                    else->0
                                }
                            })
                        }
                    }
                }

                m_adapter!!.clearAddItemList(itemList)
                m_adapter!!.setFilteredList()

                /*그리드 레이아웃 매니저를 이용하여 2열로 나타내기*/
                recyclerView.apply {
                    setHasFixedSize(true)
                    val gridLayoutManager = GridLayoutManager(context, 2)
                    layoutManager = gridLayoutManager as RecyclerView.LayoutManager?
                    clearOnScrollListeners()
                    addOnScrollListener(MyScrollListener({requestItem(m_adapter!!,response.body()!!.page,param)},gridLayoutManager))

                }
                recyclerView.adapter =m_adapter
            }

        })
    }

    private fun requestItem(adapter: CombineAdapter,page:Int,param: MutableMap<String, String>){
        param.set("page",page.plus(1).toString())
        var cosmeticList = ArrayList<Cosmetic>()

        var api : RestApi = RestApi()
        api.getListRetrofit(param).enqueue(object : Callback<CosmeticRepoList>{
            override fun onFailure(call: Call<CosmeticRepoList>, t: Throwable) {
                Toast.makeText(applicationContext,"Paging : Error ${t.localizedMessage}",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<CosmeticRepoList>,
                response: Response<CosmeticRepoList>
            ) {
                for(item in response.body()!!.body){
                    cosmeticList.add(Cosmetic(item.id,item.thumbnail_image,item.title,
                        NumberFormat.getInstance().format(item.price.toInt())+"원",
                        item.oily_score,item.dry_score,item.sensitive_score))
                }

            }

        })
        adapter.addItemList(cosmeticList)
        cosmeticList.clear()
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.custom_menu,menu)

        var item = menu?.findItem(R.id.search)
        item?.setOnActionExpandListener(ItemActionListener())

        searchView = item?.actionView as SearchView
        searchView?.queryHint="검색"
        searchView?.setOnQueryTextListener(SearchActionListener())

        return true
    }

    inner class ItemActionListener : MenuItem.OnActionExpandListener {
        override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
            return true
        }

        override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
            return true
        }

    }

    inner class SearchActionListener : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            m_adapter!!.filter.filter(query)
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            m_adapter!!.filter.filter(newText)
            return false
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

}

