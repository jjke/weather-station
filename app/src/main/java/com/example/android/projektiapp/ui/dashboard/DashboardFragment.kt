package com.example.android.projektiapp.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.android.projektiapp.CloudantData
import com.example.android.projektiapp.CustomAdapter
import com.example.android.projektiapp.Doc
import com.example.android.projektiapp.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var adapter : CustomAdapter

    var gson : Gson = Gson()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        //val textView: TextView = root.findViewById(R.id.text_dashboard)

        /*dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        val recy : RecyclerView = root.findViewById(R.id.recy_recyclerview)
        val dayTempTextView : TextView= root.findViewById(R.id.temp_24h_value_textview)
        val weekTempTextView : TextView = root.findViewById(R.id.temp_week_value_textview)

        dashboardViewModel.mData.observe(viewLifecycleOwner, Observer {
            var counter = 0
            for (x in it)
            {
                Log.d("itlogger", "array[$counter]: $x.toString()")
                counter++
            }
            recy.apply {
                adapter = CustomAdapter(it)
                recy.adapter = adapter
                layoutManager = LinearLayoutManager(activity)
            }
            var dayAddedValues : Double = 0.0
            var logCounter = 1
            for (day in it ) {
                dayAddedValues += day.doc.temperature
                Log.d("dayAverage", logCounter.toString() + " " + day.doc.temperature)
                logCounter++
                if(logCounter == 96){
                    break
                }
            }
            var dayAverage = dayAddedValues/96
            Log.d("dayAverage", dayAverage.toString())
            dayTempTextView.text = dayAverage.toString().substring(0, 3) + " C"

            var weekAddedValues : Double = 0.0
            var weekLogCounter = 1
            for (week in it) {
                if(week.doc.temperature != 0.0) {
                weekAddedValues += week.doc.temperature
                weekLogCounter++
                }
                if (weekLogCounter == 672) {
                    break
                }
            }
            var weekAverage = weekAddedValues/weekLogCounter
            Log.d("weekAverage", weekAverage.toString())
            weekTempTextView.text = weekAverage.toString().substring(0, 3) + " C"
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("cloudant", "old data fragment onViewCreated")

    }
}