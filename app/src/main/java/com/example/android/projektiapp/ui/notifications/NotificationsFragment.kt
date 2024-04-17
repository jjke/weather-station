package com.example.android.projektiapp.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.projektiapp.MainActivity
import com.example.android.projektiapp.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    private val callback = OnMapReadyCallback { googleMap ->

        var mLat = MainActivity.lat
        var mLon = MainActivity.lon

        val rovaniemi = LatLng(mLat, mLon)
        //val rovaniemi = LatLng(66.503059, 25.726967)
        googleMap.addMarker(MarkerOptions().position(rovaniemi).title("Tiedonkeruulaite"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(rovaniemi))
        with(googleMap.uiSettings) {
            isZoomControlsEnabled = true
        }
        googleMap.setPadding(0,0,0,150)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(rovaniemi, 10f))
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}