package com.example.tksproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.TextView
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

val apiForTransportsFind = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
    .baseUrl("http://"+ ipAddress +":3000/")//"https://api-henseihu.shibadog.org/"AndroidManifest.xml usesCleartextTraffic="false" trueがhttp
    .build().create(GetTransport::class.java)

class Transport : AppCompatActivity() {
    var transportsList = mutableListOf<transports>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transport)
        var token = intent.getStringExtra("TOKEN")!!
        lifecycleScope.launch {
            var response = apiForTransportsFind.scrapeTransports(authorization = token, body = transportsRequest(
                orv = "中崎町",
                dnv = "東京",
                year = "2023",
                month = "02",
                day = "15",
                hour = "08",
                minute = "30"
            ))
            response.enqueue(object : Callback<transport> {
                override fun onResponse(call: Call<transport>, response: Response<transport>) {
                    val transportsInfo = response.body()!!

                    var charge = findViewById<TextView>(R.id.charge)
                    var leftToArrive = findViewById<TextView>(R.id.leftToArrive)
                    val t = response.body()!!.transports
                    for (i in t) {
                        transportsList.add(i)
                    }

                    charge.text = "総運賃：" + transportsInfo.max_charge
                    leftToArrive.text = "時刻表："+transportsInfo.left_to_arrive
                    var list_view = findViewById<ListView>(R.id.transports_list_view)

                    val adapter = TransportsAdapter(getApplicationContext(), transportsList)
                    list_view.adapter = adapter



                }

                override fun onFailure(call: Call<transport>, t: Throwable) {
                    Toast.makeText(applicationContext, "通信に失敗しました。", Toast.LENGTH_LONG).show()
                    Log.v("#####通信状況######", "通信に失敗")
                }
            })


        }



    }
}