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


class Dcity : AppCompatActivity() {
    var dCityList = mutableListOf<detailedCity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dcity)
        Log.v("###tag###", "DCITYスタート！！！")

        //var intent = Intent(applicationContext, Dcity::class.java)
        val token = intent.getStringExtra("TOKEN")!!
        val prefectureCode = intent.getStringExtra("PREFECTURE_CODE")!!
        val prefectureId = intent.getStringExtra("PREFECTURE_ID")!!
        val cityId = intent.getStringExtra("CITY_ID")!!
        val cityCode = intent.getStringExtra("CITY_CODE")!!
        val isDcityInclude = intent.getBooleanExtra("IS_DCITY_INCLUDE", true)
        lifecycleScope.launch {
            var response = apiForCity.getDetailedCity(authorization = token, cityId = cityId)

            response.enqueue(object : Callback<DetailedCities> {
                override fun onResponse(
                    call: Call<DetailedCities>,
                    response: Response<DetailedCities>
                ) {

                    val dCities = response.body()!!.data
                    for (i in dCities) {
                        dCityList.add(i)
                    }

                    Toast.makeText(applicationContext, "通信成功!!!!!!!!!!!!!!", Toast.LENGTH_LONG)
                        .show()

                    var list_view = findViewById<ListView>(R.id.detailed_city_list_view)

                    val adapter = DetailedCityListAdapter(getApplicationContext(), dCityList)
                    list_view.adapter = adapter

                    list_view.setOnItemClickListener { parent, view, position, id ->
                        Log.v("###tag###", "項目がタップされました")
                        val item = parent.getItemIdAtPosition(position).toInt()
                        val dCityId = response.body()!!.data[item].ID
                        val dCityCode = response.body()!!.data[item].Code
                        val dCity = response.body()!!.data[item].Name

                        //var intent = Intent(applicationContext, CheckDcityExist::class.java)

                        intent.putExtra("TOKEN", token)
                        intent.putExtra("PREFECTURE_ID", prefectureId)
                        intent.putExtra("PREFECTURE_CODE", prefectureCode)
                        intent.putExtra("CITY_ID", cityId.toString())
                        intent.putExtra("CITY_CODE", cityCode)
                        intent.putExtra("DETAILED_CITY_ID", dCityId.toString())
                        intent.putExtra("DETAILED_CITY_CODE", dCityCode)
                        intent.putExtra("DETAILED_CITY", dCity)
                        var intent = Intent(applicationContext, HotelList::class.java)


                        Toast.makeText(
                            applicationContext,
                            dCity + "がタップされました!!!!!!!!!!!!!!",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(intent)
                    }

                }

                override fun onFailure(call: Call<DetailedCities>, t: Throwable) {
                    Toast.makeText(applicationContext, "通信に失敗しました。", Toast.LENGTH_LONG).show()
                    Log.v("#####通信状況######", "通信に失敗")
                }
            })
        }
    }
}