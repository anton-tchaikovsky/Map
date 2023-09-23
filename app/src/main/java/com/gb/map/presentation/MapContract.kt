package com.gb.map.presentation

interface MapContract {

    interface MapView{
        fun checkPermissionLocation()
    }

    interface MapPresenter{
        fun attach(mapView: MapView)
        fun detach()
        fun onPermissionLocationGrande()
        fun onPermissionLocationDenied()
    }
}