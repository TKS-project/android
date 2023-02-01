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

data class transportsRequest(
    var orv: String,
    var dnv: String,
    var year: String,
    var month: String,
    var day: String,
    var hour: String,
    var minute: String
)

data class transport(
    var max_charge: String,
    var left_to_arrive: String,
    var transports: List<transports>
)
/*
	Type     string `json:"type"`
	Time     string `json:"time"`
	Name     string `json:"name"`
	Url      string `json:"url"`
	Plathome string `json:"plathome"`
 */
data class transports(
    var type: String,
    var time: String,
    var name: String,
    var url: String,
    var plathome: String
)