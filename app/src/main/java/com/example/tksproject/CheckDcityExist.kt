package com.example.tksproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckDcityExist : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_dcity_exist)

        val token = intent.getStringExtra("TOKEN")!!
        val prefectureCode = intent.getStringExtra("PREFECTURE_CODE")!!
        val prefectureId = intent.getStringExtra("PREFECTURE_ID")!!
        val cityId = intent.getStringExtra("CITY_ID")!!
        val cityCode = intent.getStringExtra("CITY_CODE")!!

        lifecycleScope.launch {
            Log.v("!!!!!!!!!!!!!!!!!!!!", "################3")
            val response = apiForCity.isDcityExist(authorization = token, cityId = cityId.toString())
            response.enqueue(object : Callback<isExist> {
                override fun onResponse(call: Call<isExist>, response: Response<isExist>) {
                    Toast.makeText(applicationContext, response.body()!!.exist.toString(), Toast.LENGTH_SHORT).show()
                    val isExist = response.body()!!.exist
                    Log.v("アクティビティーCheck", "start!!")
                    if (!isExist){
                        Log.v("Booleanチェック", "dcity not exist")
                        //find hotel
                        var intent = Intent(applicationContext, HotelList::class.java)
                        intent.putExtra("TOKEN", token)
                        intent.putExtra("PREFECTURE_ID", prefectureId)
                        intent.putExtra("PREFECTURE_CODE", prefectureCode)
                        intent.putExtra("CITY_ID", cityId.toString())
                        intent.putExtra("CITY_CODE", cityCode)
                        intent.putExtra("IS_DCITY_INCLUDE", false)
                        Log.v("Activity checkDcity", "to Hotel List")
                        startActivity(intent)
                    }
                    //to Detailed City Activity
                    var intent2 = Intent(applicationContext, Dcity::class.java)
                    intent2.putExtra("TOKEN", token)
                    intent2.putExtra("PREFECTURE_ID", prefectureId)
                    intent2.putExtra("PREFECTURE_CODE", prefectureCode)
                    intent2.putExtra("CITY_ID", cityId.toString())
                    intent2.putExtra("CITY_CODE", cityCode)
                    intent2.putExtra("IS_DCITY_INCLUDE", true)
                    startActivity(intent2)

                }
                override fun onFailure(call: Call<isExist>, t: Throwable) {
                    Toast.makeText(applicationContext, "通信に失敗しました。", Toast.LENGTH_LONG).show()
                    Log.v("#####通信状況######", "通信に失敗")
                }

            })
        }
    }
}