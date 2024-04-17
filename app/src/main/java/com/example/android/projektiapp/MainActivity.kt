package com.example.android.projektiapp

import MqttData
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import de.nitri.gauge.Gauge
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*


class MainActivity : AppCompatActivity() {

    var mqttAndroidClient: MqttAndroidClient? = null

    companion object loc {
        var lat : Double = 66.503059
        var lon : Double = 25.726967
    }

    val serverUri = "tcp://cj4ojv.messaging.internetofthings.ibmcloud.com:1883"
    //"cj4ojv.messaging.internetofthings.ibmcloud.com"
    var clientId = "a:cj4ojv:tvt-mj-4"
    val subscriptionTopic = "iot-2/type/mittauskurssi/id/mj4/evt/data/fmt/json"
    val mqttUsername = "a-cj4ojv-hlleqcphw5"
    val mqttPassword = "6Z*t+s@t_8h97PmriW"

    var gson : Gson = Gson()

    val cloudantusername = "3232ba0f34fb39409113b98e283f0a7c908dee77dd59efeabc050e17241fc0ca"
    val cloudantpassword = "cecddff0-8f2d-4454-a95b-f2f282ec4e2a-bluemix"
    //var returnVar : String = "test"
    private lateinit var geocoder: Geocoder
    private lateinit var humidityView: Gauge
    private lateinit var pressureView: Gauge
    private lateinit var localeView: TextView
    private lateinit var temperatureView: TextView

    /*val serverUri = "tcp://i5u4t3.messaging.internetofthings.ibmcloud.com:1883"
    val clientId = "a:i5u4t3:tvtplab1234"
    val subscriptionTopic = "iot-2/type/ws-10/id/3005/evt/data/fmt/json"
    val mqttUsername = "a-i5u4t3-kg2otp2blv"
    val mqttPassword = "iJt_MQqHikrls*)Cpc" */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))

        // THIS COMMENTED LINE DISABLES ACTION BAR
        // setupActionBarWithNavController(navController, appBarConfiguration)

        navView.setupWithNavController(navController)

        Log.d("test", "main activity oncreate function run")

        mqttStart()
    }

    // mqtt
    fun mqttStart() {
        // Create the client!
        mqttAndroidClient = MqttAndroidClient(applicationContext, serverUri, clientId)
        geocoder = Geocoder(this)

        // CALLBACKS, these will take care of the connection if something unexpected happen
        mqttAndroidClient?.setCallback(object : MqttCallbackExtended {
            override fun connectComplete(reconnect: Boolean, serverURI: String) {
                if (reconnect) {
                    Log.d("MYERROR", "reconnected to MQTT")
                    // we have to subscribe again because of the reconnection
                    subscribeToTopic()
                } else {
                    Log.d("MYERROR", "connected to MQTT")
                }
            }

            override fun connectionLost(cause: Throwable) {
                Log.d("MYERROR", "MQTT connection lost")
            }

            @Throws(Exception::class)
            override fun messageArrived(topic: String, message: MqttMessage) {
                // THIS VARIABLE IS THE JSON DATA. you can use GSON to get the needed
                // data (temperature for example) out of it, and show it in a textview or something else
                try {

                    // RESULT OF THE MQTT MESSAGE IS HERE :DD
                    val result = String(message.payload)
                    Log.d("MYERROR", result)

                    // tänne parsetus
                    var a = result.substring(5, result.length-1)
                    //Log.d("parse", a)

                    var parsed = gson.fromJson(a, MqttData::class.java)
                    Log.d("parsed", "time : ${parsed.time}")
                    Log.d("parsed", "temperature : ${parsed.temperature}")
                    Log.d("parsed", "humidity : ${parsed.humidity}")
                    Log.d("parsed", "lat : ${parsed.lat}")
                    Log.d("parsed", "lon : ${parsed.lon}")
                    Log.d("parsed", "myTime : ${parsed.myTime}")
                    Log.d("parsed", "-")


                    if(parsed.lat != 0.0) {
                        setCO(a = parsed.lat, b = parsed.lon)
                    }

                    localeView = findViewById(R.id.localeView)
                    temperatureView = findViewById(R.id.temperatureView)
                    humidityView = findViewById(R.id.humidityView)
                    pressureView = findViewById(R.id.pressureView)

                    val location: MutableList<Address> = geocoder.getFromLocation(parsed.lat, parsed.lon, 1)
                    if (location.size > 0) {
                        localeView.text = location[0].locality.toString() + ", " + location[0].countryCode.toString()
                    }
                    val temperatureText = "%.1f" .format(parsed.temperature)

                    temperatureView.text = temperatureText + " ℃"

                    val pressure = parsed.pressure.toFloat()
                    val humidity = parsed.humidity.toFloat()

                    if (humidity > 0.0 && pressure > 0.0) {
                        humidityView.moveToValue(humidity)
                        pressureView.moveToValue(pressure / 100)
                    }

                } catch (e: Exception) {
                    Log.d("MYERROR", e.toString());
                }
            }

            // used when sending data via MQTT
            override fun deliveryComplete(token: IMqttDeliveryToken) {}
        })

        // CONNECT TO MQTT

        val mqttConnectOptions = MqttConnectOptions()
        mqttConnectOptions.isAutomaticReconnect = true
        mqttConnectOptions.isCleanSession = false
        mqttConnectOptions.userName = mqttUsername
        mqttConnectOptions.password = mqttPassword.toCharArray()

        try {
            mqttAndroidClient?.connect(mqttConnectOptions, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    val disconnectedBufferOptions = DisconnectedBufferOptions()
                    disconnectedBufferOptions.isBufferEnabled = true
                    disconnectedBufferOptions.bufferSize = 100
                    disconnectedBufferOptions.isPersistBuffer = false
                    disconnectedBufferOptions.isDeleteOldestMessages = false
                    mqttAndroidClient!!.setBufferOpts(disconnectedBufferOptions)
                    subscribeToTopic()
                }

                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    Log.d("MYERROR", "Failed to connect!")
                }
            })
        } catch (ex: MqttException) {
            ex.printStackTrace()
        }
    }


    // subscriber method
    fun subscribeToTopic() {
        try {
            mqttAndroidClient?.subscribe(subscriptionTopic, 0, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    Log.d("MYERROR", "Subscribed!")
                }

                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    Log.d("MYERROR", "Failed to subscribe")
                }
            })
        } catch (ex: MqttException) {
            System.err.println("Exception whilst subscribing")
            ex.printStackTrace()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        mqttAndroidClient?.close()
        Log.d("MYERROR", "mqtt client closed")
    }
    fun setCO(a: Double, b: Double) {
        lat = a
        lon = b
        Log.d("setCO", lat.toString() +" and " + lon.toString())
    }
}