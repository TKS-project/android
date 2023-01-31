package com.example.tksproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
val ipAddress = "192.168.0.18"
val apiForUserReg = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
    .baseUrl("http://"+ ipAddress +":3000/")//"https://api-henseihu.shibadog.org/"AndroidManifest.xml usesCleartextTraffic="false" trueがhttp
    .build().create(ForUserReg::class.java)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var regBtn = findViewById<Button>(R.id.RegBtn) //
        var mail = findViewById<EditText>(R.id.mail)
        var password = findViewById<EditText>(R.id.Password)



        regBtn.setOnClickListener{

            lifecycleScope.launch {
                //val response = getRequest()//後で引数

                //val response = apiForUserReg.login(SendUserReg(mail.text.toString(), 0, Name.text.toString(), Password.text.toString()))
                var response = apiForUserReg.login(LoginReq(mail = mail.text.toString(), password = password.text.toString()))
                Log.v("Json 確認！！！！！", response.toString())

                response.enqueue(object : Callback<LoginRes> {
                    override fun onResponse(call: Call<LoginRes>, response: Response<LoginRes>) {

                        val message = response.body()!!.messgae
                        val status = response.body()!!.status
                        val token = response.body()!!.token
                        Log.v("###トークン###", token)
                        Toast.makeText(applicationContext, "通信成功!!!!!!!!!!!!!!", Toast.LENGTH_LONG).show()
                        var intent = Intent(applicationContext, Prefecture::class.java)
                        intent.putExtra("TOKEN", token)
                        startActivity(intent)

                    }

                    override fun onFailure(call: Call<LoginRes>, t: Throwable) {
                        Toast.makeText(applicationContext, "通信に失敗しました。", Toast.LENGTH_LONG).show()
                        Log.v("#####通信状況######", "通信に失敗")
                    }

                })
            }
        }
    }
}