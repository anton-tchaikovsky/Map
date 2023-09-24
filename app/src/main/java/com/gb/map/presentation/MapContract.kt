package com.gb.map.presentation

import com.gb.map.data.LocationDto
import com.google.android.gms.maps.model.LatLng

interface MapContract {

    interface MapView{
        fun checkPermissionLocation()

        fun showMarker(locationDto: LocationDto, isMoveCamera: Boolean = true)
    }

    interface MapPresenter{
        fun attach(mapView: MapView)

        fun detach()

        fun onPermissionLocationGrande()

        fun onPermissionLocationDenied()

        fun onMapReading ()

        fun onAddMarker(latLng: LatLng)
    }
}