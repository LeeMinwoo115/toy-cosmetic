package com.example.hwahae

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.vectordrawable.graphics.drawable.AnimationUtilsCompat
import com.example.hwahae.data.CosmeticRepoList
import com.example.hwahae.data.RepoInfoModel
import com.example.hwahae.data.RestApi
import com.example.hwahae.utils.loadImg
import kotlinx.android.synthetic.main.activity_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val strID = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        val price = intent.getStringExtra("price")

        var InfoApi :RestApi = RestApi()
        InfoApi.getDetailItem(strID).enqueue(object : Callback<RepoInfoModel> {
            override fun onFailure(call: Call<RepoInfoModel>, t: Throwable) {
                Log.d("InfoActivity",t.localizedMessage)
                Toast.makeText(applicationContext,"Error ${t.localizedMessage}", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<RepoInfoModel>, response: Response<RepoInfoModel>) {
                imageView_detail.loadImg(response.body()!!.body.full_size_image)
                textView_detail_info.setText(response.body()!!.body.description)
                textView_detail_name.setText(name)
                textView_detail_price.setText(price)
            }

        })

        val animation = AnimationUtils.loadAnimation(applicationContext,R.anim.up_animation)
        button_purchase.startAnimation(animation)

        back_button.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK)
        finish()
        overridePendingTransition(R.anim.fadein_animation,R.anim.down_animation)
    }
}
