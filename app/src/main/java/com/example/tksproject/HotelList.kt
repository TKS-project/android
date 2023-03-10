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

val apiForHotelFind = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
    .baseUrl("http://"+ ipAddress +":3000/")//"https://api-henseihu.shibadog.org/"AndroidManifest.xml usesCleartextTraffic="false" trueがhttp
    .build().create(GetHotel::class.java)

class HotelList : AppCompatActivity() {
    var hotelList = mutableListOf<hotel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel_list)
        Toast.makeText(application,"hotel選択画面", Toast.LENGTH_SHORT).show()
        Log.v("hotellistログ!", "start!!!!!!!!!")


        val token = intent.getStringExtra("TOKEN")!!
        val prefectureCode = intent.getStringExtra("PREFECTURE_CODE")!!
        val cityCode = intent.getStringExtra("CITY_CODE")!!
        val detailedCityCode = intent.getStringExtra("DETAILED_CITY_CODE")!!
        var detailedCity = intent.getStringExtra("DETAILED_CITY")!!
        Log.v("!!!!!!!!!!!!!!!!!!", prefectureCode)
        Log.v("!!!!!!!!!!!!!!!!!", cityCode)
        Log.v("!!!!!!!!!!!!!!!!!", detailedCityCode)
        lifecycleScope.launch {
            var response = apiForHotelFind.getHoteldcity(
                authorization = token,
                prefectureCode = prefectureCode,
                cityCode = cityCode,
                checkin = "2023-07-14",
                checkout = "2023-07-15",
                adultNum = "1",
                maxCharge = "10000",
                detailedCity = detailedCityCode
            )

            response.enqueue(object : Callback<Hotels> {
                override fun onResponse(call: Call<Hotels>, response: Response<Hotels>) {
                    val hotels = response.body()!!.message
                    for (i in hotels) {
                        hotelList.add(i)
                    }
                    Log.v("#######レスポンス", hotelList[1].access)
                    var list_view = findViewById<ListView>(R.id.hotel_list_view)

                    val adapter = HotelListAdapter(getApplicationContext(), hotelList)
                    list_view.adapter = adapter
                    list_view.setOnItemClickListener { parent, view, position, id ->
                        Log.v("###tag###", "項目がタップされました")
                        val item = parent.getItemIdAtPosition(position).toInt()
                        var intent = Intent(applicationContext, Transport::class.java)
                        intent.putExtra("TOKEN", token)
                        startActivity(intent)
                    }
                }

                override fun onFailure(call: Call<Hotels>, t: Throwable) {
                    Toast.makeText(applicationContext, "通信に失敗しました。", Toast.LENGTH_LONG).show()
                    Log.v("#####通信状況######", "通信に失敗")
                }
            })
        }
    }
}