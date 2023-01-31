package com.example.tksproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.lifecycle.lifecycleScope
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val apiForPrefecture = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
    .baseUrl("http://"+ ipAddress +":3000/")//"https://api-henseihu.shibadog.org/"AndroidManifest.xml usesCleartextTraffic="false" trueがhttp
    .build().create(GetPrefecture::class.java)

class Prefecture : AppCompatActivity() {
    var prefectureList = mutableListOf<prefecture>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prefecture)


        val token = intent.getStringExtra("TOKEN")!!
        var message = findViewById<EditText>(R.id.message)
        Log.v("#####トークンの中身Pre###", token)

        lifecycleScope.launch {
            var response = apiForPrefecture.getPrefecture(authorization = token)

            Log.v("Json 確認！！！！！", response.toString())

            response.enqueue(object : Callback<Prefectures> {
                override fun onResponse(call: Call<Prefectures>, response: Response<Prefectures>) {

                    val prefectures = response.body()!!.data
                    var preText = ""
                    for (i in prefectures) {
                        preText += i.Name
                        preText += "\n"
                        preText += "\n"
                    }

                    for (i in prefectures) {
                        prefectureList.add(i)
                    }

                    Log.v("###トークン###", token)
                    Log.v("リストの中身" ,prefectures[0].Name.toString())
                    Toast.makeText(applicationContext, "通信成功!!!!!!!!!!!!!!", Toast.LENGTH_LONG).show()

                    var list_view = findViewById<ListView>(R.id.prefecture_list_view)

                    val adapter = ListAdapter(getApplicationContext(), prefectureList)
                    list_view.adapter = adapter

                    list_view.setOnItemClickListener { parent, view, position, id ->

                        val item = parent.getItemIdAtPosition(position).toInt()
                        val selectedCode = response.body()!!.data[item].Code
                        val selectedPrefecture = response.body()!!.data[item].Name
                        val prefectureId = response.body()!!.data[item].ID
                        var intent = Intent(applicationContext, City::class.java)
                        intent.putExtra("PREFECTURE_CODE", selectedCode)
                        intent.putExtra("TOKEN", token)
                        intent.putExtra("PREFECTURE_ID", prefectureId.toString())
                        Toast.makeText(applicationContext, selectedPrefecture+"がタップされました!!!!!!!!!!!!!!", Toast.LENGTH_LONG).show()
                        startActivity(intent)
                    }

                }

                override fun onFailure(call: Call<Prefectures>, t: Throwable) {
                    Toast.makeText(applicationContext, "通信に失敗しました。", Toast.LENGTH_LONG).show()
                    Log.v("#####通信状況######", "通信に失敗")
                }

            })
        }
    }
}