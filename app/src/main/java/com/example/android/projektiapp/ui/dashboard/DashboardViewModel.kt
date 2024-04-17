package com.example.android.projektiapp.ui.dashboard

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.android.projektiapp.CloudantData
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//var jsonDataAr : ArrayList<CloudantData>? = null
lateinit var mMutLiveData: ArrayList<CloudantData>
class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext

     var _data = MutableLiveData<ArrayList<CloudantData>>().apply {
        viewModelScope.launch {
            mMutLiveData = getCloudant(context)
            Log.d("ScopeLaunch", mMutLiveData[0].toString())
            value = mMutLiveData
        }
     }
    private val _text = MutableLiveData<String>().apply {
        //value = "This is Old data Fragment"
    }
    val text: LiveData<String> = _text
    var mData : LiveData<ArrayList<CloudantData>> = _data

    suspend fun getCloudant(context : Context): ArrayList<CloudantData> {

        val gson = Gson()
        lateinit var mResponse : ArrayList<CloudantData>

        val queue = Volley.newRequestQueue(context)
        val url = "https://cecddff0-8f2d-4454-a95b-f2f282ec4e2a-bluemix.cloudantnosqldb.appdomain.cloud/team510final/_all_docs?include_docs=true&descending=true&stable=true"

        val stringRequest = StringRequest(Request.Method.GET, url,
                { response ->
                    Log.d("volley", response.length.toString())
                    var cutOffStart = response.indexOf('[')
                    var cutOffEnd = response.indexOf(']')
                    var cleanedJson = response.substring(cutOffStart, cutOffEnd-2)
                    cleanedJson += "]"
                    Log.d("cleanedJson", cleanedJson)

                    var parsedJson : ArrayList<CloudantData> = gson.fromJson(cleanedJson, Array<CloudantData>::class.java).toCollection(ArrayList())
                    //Log.d("parsedJsonArray", parsedJson[0].doc.toString())

                    parsedJson.sortByDescending { it.doc.time }
                    //jsonDataAr = parsedJson
                    mResponse = parsedJson


                    var loopcounter = 0
                    for (item : CloudantData in parsedJson)
                    {
                        Log.d("viewmodelcloudantjson", "value in array[$loopcounter]: $item")
                        loopcounter++
                    }
                },
                {   Log.d("volley", "That didn't work!") })

        queue.add(stringRequest)

        delay(1000)
        return mResponse

    }
}
