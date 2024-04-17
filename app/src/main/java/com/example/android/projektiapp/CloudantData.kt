package com.example.android.projektiapp

import com.google.gson.annotations.SerializedName

data class CloudantData(

        @SerializedName("id") val id : String,
        @SerializedName("key") val key : String,
        @SerializedName("doc") val doc : Doc,
        @SerializedName("value") val value : Value
)

data class Doc(

        @SerializedName("_id") val _id : String,
        @SerializedName("temperature") val temperature : Double,
        @SerializedName("humidity") val humidity : Double,
        @SerializedName("pressure") val pressure : Double,
        @SerializedName("accelerationX") val accelerationX : Int,
        @SerializedName("accelerationY") val accelerationY : Int,
        @SerializedName("accelerationZ") val accelerationZ : Int,
        @SerializedName("battery") val battery : Int,
        @SerializedName("time") val time : String,
        @SerializedName("ept") val ept : Double,
        @SerializedName("lat") val lat : Double,
        @SerializedName("lon") val lon : Double,
        @SerializedName("alt") val alt : Double,
        @SerializedName("epx") val epx : Double,
        @SerializedName("epy") val epy : Double,
        @SerializedName("epv") val epv : Double,
        @SerializedName("track") val track : Double,
        @SerializedName("speed") val speed : Double,
        @SerializedName("climb") val climb : Double,
        @SerializedName("eps") val eps : Double,
        @SerializedName("epc") val epc : Double,
        @SerializedName("myTime") val myTime : String
)

data class Value(
        @SerializedName("rev") val rev : String
)