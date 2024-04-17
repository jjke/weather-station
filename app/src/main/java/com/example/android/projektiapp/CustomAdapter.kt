package com.example.android.projektiapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CustomAdapter(private val dataSet: ArrayList<CloudantData>) :
        RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    var data = ArrayList<CloudantData>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val arrayNum : TextView = view.findViewById(R.id.arraynum_textView)
        val tempNum : TextView = view.findViewById(R.id.temperature_textView_value)
        val timeNum : TextView = view.findViewById(R.id.time_textView_value)
        val humiNum : TextView = view.findViewById(R.id.humidity_textView_value)
        val pressNum : TextView = view.findViewById(R.id.pressure_textView_value)

        /*init {
            // Define click listener for the ViewHolder's View.
            arrayNum = view.findViewById(R.id.arraynum_textView)
            tempNum = view.findViewById(R.id.temperature_textView)
            timeNum = view.findViewById(R.id.time_textView)
            humiNum = view.findViewById(R.id.humidity_textView)
            pressNum = view.findViewById(R.id.pressure_textView)
        }*/
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.recy_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        var item = dataSet[position]

        var cutOff = item.doc.myTime.toString().indexOf("-")
        var cutDate = item.doc.myTime.substring(0, cutOff)
        var cutTime = item.doc.myTime.substring(cutOff+1, item.doc.myTime.length)
        var count = dataSet.indexOf(item)+1

        viewHolder.arrayNum.text = count.toString()
        viewHolder.tempNum.text = item.doc.temperature.toString() + " C"
        viewHolder.humiNum.text = item.doc.humidity.toString() + " %"
        viewHolder.pressNum.text = item.doc.pressure.toString() + " Pascal"
        viewHolder.timeNum.text = cutDate + " $cutTime"
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
