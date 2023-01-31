package com.example.tksproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val apiForCity = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
    .baseUrl("http://"+ ipAddress +":3000/")//"https://api-henseihu.shibadog.org/"AndroidManifest.xml usesCleartextTraffic="false" trueがhttp
    .build().create(GetCity::class.java)

class City : AppCompatActivity() {

    var cityList = mutableListOf<city>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)
        val token = intent.getStringExtra("TOKEN")!!
        val prefectureCode = intent.getStringExtra("PREFECTURE_CODE")!!
        val prefectureId = intent.getStringExtra("PREFECTURE_ID")!!
        Log.v("確認", token + " : " + prefectureCode)
        Toast.makeText(applicationContext, prefectureId.toString() + prefectureCode + token, Toast.LENGTH_LONG).show()
        //ID, name, code 取得 -> detail city に存在するかを確認
        lifecycleScope.launch {
            var response = apiForCity.getCities(authorization = token, prefectureId = prefectureId)

            Log.v("Json 確認！！！！！", response.toString())

            response.enqueue(object : Callback<Cities> {
                override fun onResponse(call: Call<Cities>, response: Response<Cities>) {

                    val cities = response.body()!!.data

                    for (i in cities) {
                        cityList.add(i)
                    }

                    Log.v("###トークン###", token)
                    Log.v("リストの中身" ,cities[0].Name.toString())
                    Toast.makeText(applicationContext, "通信成功!!!!!!!!!!!!!!", Toast.LENGTH_LONG).show()

                    var list_view = findViewById<ListView>(R.id.city_list_view)

                    val adapter = CityListAdapter(getApplicationContext(), cityList)
                    list_view.adapter = adapter

                    list_view.setOnItemClickListener { parent, view, position, id ->
                        Log.v("###tag###" ,"項目がタップされました")
                        val item = parent.getItemIdAtPosition(position).toInt()
                        val cityId = response.body()!!.data[item].ID
                        val cityCode = response.body()!!.data[item].Code
                        val selectedCity = response.body()!!.data[item].Name

                        var intent = Intent(applicationContext, CheckDcityExist::class.java)

                        intent.putExtra("TOKEN", token)
                        intent.putExtra("PREFECTURE_ID", prefectureId)
                        intent.putExtra("PREFECTURE_CODE", prefectureCode)
                        intent.putExtra("CITY_ID", cityId.toString())
                        intent.putExtra("CITY_CODE", cityCode)

                        Toast.makeText(applicationContext, selectedCity+"がタップされました!!!!!!!!!!!!!!", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                    }

                }

                override fun onFailure(call: Call<Cities>, t: Throwable) {
                    Toast.makeText(applicationContext, "通信に失敗しました。", Toast.LENGTH_LONG).show()
                    Log.v("#####通信状況######", "通信に失敗")
                }

            })
        }

    }
}