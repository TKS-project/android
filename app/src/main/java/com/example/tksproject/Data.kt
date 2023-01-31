package com.example.tksproject

data class LoginRes(
    var messgae: String,
    var status: String,
    var token: String
)

data class LoginReq(
    var mail: String,
    var password: String
)

data class Prefectures(
    var data: List<prefecture>
)
data class prefecture(
    var ID: Int,
    var Name: String,
    var Code: String,
    var CreatedAt: String,
    var UpdatedAt: String,
    var DeletedAt: String
)

data class Cities(
    var data : List<city>
)
data class city(
    var ID: Int,
    var Name: String,
    var Code: String,
    var CreatedAt: String,
    var UpdatedAt: String,
    var DeletedAt: String
)

data class isExist(
    val exist : Boolean
)

data class  DetailedCities(
    var data: List<detailedCity>
)
data class  detailedCity(
    var ID: Int,
    var Name: String,
    var Code: String,
    var CreatedAt: String,
    var UpdatedAt: String,
    var DeletedAt: String
)