package com.example.tksproject


import retrofit2.Call
import retrofit2.http.*

interface  ForUserReg{
    @POST("users/login")
    fun login(@Body user: LoginReq): Call<LoginRes>
}
interface GetPrefecture {
    @GET("prefectures")
    fun getPrefecture(@Header("Authorization") authorization: String): Call<Prefectures>
}

interface GetCity {
    @GET("cities/{prefectureId}")
    fun getCities(
        @Header("Authorization") authorization: String,
        @Path("prefectureId") prefectureId: String
    ): Call<Cities>

    @GET("detailed/exist/{cityId}")
    fun isDcityExist(
        @Header("Authorization") authorization: String,
        @Path("cityId") cityId: String
    ): Call<isExist>

    @GET("detailed/{cityId}")
    fun getDetailedCity(
        @Header("Authorization") authorization: String,
        @Path("cityId")cityId: String
    ): Call<DetailedCities>

}

interface GetHotel{
    @GET("hotel/{prefectureCode}/{cityCode}/{checkin}/{checkout}/{adultNum}/{maxCharge}")
    fun getHotel(
        @Header("Authorization") authorization: String,
        @Path("prefectureCode") prefectureCode: String,
        @Path("cityCode") cityCode: String,
        @Path("checkin") checkin: String,
        @Path("checkout") checkout: String,
        @Path("adultNum") adultNum: String,
        @Path("maxCharge") maxCharge: String
    ): Call<Hotels>

    @GET("hotel/{prefecture}/{city}/{checkin}/{checkout}/{adultNum}/{maxcharge}/{detailedCity}")
    fun getHoteldcity(
        @Header("Authorization") authorization: String,
        @Path("prefecture") prefectureCode: String,
        @Path("city") cityCode: String,
        @Path("checkin") checkin: String,
        @Path("checkout") checkout: String,
        @Path("adultNum") adultNum: String,
        @Path("maxcharge") maxCharge: String,
        @Path("detailedCity") detailedCity: String

    ): Call<Hotels>
}

interface GetTransport{
    @POST("scrape/transports")
    //    fun postRawRequestForPosts(@Body body:RequestBody):Call<ResponseBody>
    fun scrapeTransports(@Body body: transportsRequest,
                         @Header ("Authorization") authorization: String
    ):Call<transport>
}
