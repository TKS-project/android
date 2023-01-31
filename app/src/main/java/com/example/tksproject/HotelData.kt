package com.example.tksproject

data class Hotels(
    var message: List<hotel>
)
data class  hotel(
    var hotelNo: Int,
    var hotelName: String,
    var hotelSpecial: String,
    var hotelMinCharge: Int,
    var postalCode: String,
    var address1: String,
    var address2: String,
    var telephoneNo: String,
    var parkingInformation: String,
    var nearestStation: String,
    var access: String,
    var hotelThumbnailUrl: String,
    var reviewAverage: Double,
    var userReview: String,
    var room : room
)

data class room(
    var roomInfo: roomInfo,
    var chargeInfo: chargeInfo,
)

data class roomInfo(
    var roomClass: String,
    var roomName: String,
    var planId: Int,
    var planName: String,
    var pointRate: Int,
    var withDinnerFlag: Int,
    var dinnerSelectFlag: Int,
    var withBreakfastFlag: Int,
    var breakfastSelectFlag: Int,
    var payment: String,
    var reserveUrl: String,
    var salesformFlag: Int

)


data class chargeInfo(
    var stayDate: String,
    var rakutenCharge: Int,
    var total: Int,
    var chargeFlag: Int
)
