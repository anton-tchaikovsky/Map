package com.gb.map.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gb.map.R
import com.gb.map.data.LocationDto
import com.gb.map.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : BasePermissionLocationFragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        mapPresenter.onMapReading()
        map.setOnMapLongClickListener {
            mapPresenter.onAddMarker(it)
        }
    }

    private lateinit var map: GoogleMap

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun showMarker(locationDto: LocationDto, isMoveCamera: Boolean) {
        map.run{
            addMarker(MarkerOptions().position(locationDto.latLng).title(locationDto.name))
            if (isMoveCamera)
                moveCamera(CameraUpdateFactory.newLatLng(locationDto.latLng))
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}