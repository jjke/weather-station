import com.google.gson.annotations.SerializedName

/*
Copyright (c) 2021 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class MqttData (

		@SerializedName("temperature") val temperature : Double,
		@SerializedName("humidity") val humidity : Double,
		@SerializedName("pressure") val pressure : Int,
		@SerializedName("accelerationX") val accelerationX : Int,
		@SerializedName("accelerationY") val accelerationY : Int,
		@SerializedName("accelerationZ") val accelerationZ : Int,
		@SerializedName("battery") val battery : Int,
		@SerializedName("txPower") val txPower : Int,
		@SerializedName("movementCounter") val movementCounter : Int,
		@SerializedName("sequenceCounter") val sequenceCounter : Int,
		@SerializedName("mac") val mac : String,
		@SerializedName("lat") val lat : Double,
		@SerializedName("class") val mclass : String,
		@SerializedName("device") val device : String,
		@SerializedName("mode") val mode : Int,
		@SerializedName("time") val time : String,
		@SerializedName("ept") val ept : Double,
		@SerializedName("lon") val lon : Double,
		@SerializedName("alt") val alt : Double,
		@SerializedName("epx") val epx : Double,
		@SerializedName("epy") val epy : Double,
		@SerializedName("epv") val epv : Double,
		@SerializedName("track") val track : Double,
		@SerializedName("speed") val speed : Double,
		@SerializedName("climb") val climb : Int,
		@SerializedName("eps") val eps : Double,
		@SerializedName("epc") val epc : Double,
		@SerializedName("myTime") val myTime : String
)