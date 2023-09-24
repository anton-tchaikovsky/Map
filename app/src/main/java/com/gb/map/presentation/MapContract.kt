package com.gb.map.presentation

import com.gb.map.data.LocationDto

interface MapContract {

    interface MapView{
        fun checkPermissionLocation()

        fun showMarker(location: LocationDto)
    }

    interface MapPresenter{
        fun attach(mapView: MapView)

        fun detach()

        fun onPermissionLocationGrande()

        fun onPermissionLocationDenied()

        fun onMapReading ()
    }
}