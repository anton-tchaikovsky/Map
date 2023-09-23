package com.gb.map.presentation

import android.util.Log

class MapPresenterImpl: MapContract.MapPresenter {

    private var mapView: MapContract.MapView? = null

    override fun attach(mapView: MapContract.MapView) {
        this.mapView = mapView.also {
            it.checkPermissionLocation()
        }
    }

    override fun detach() {
        mapView = null
    }

    override fun onPermissionLocationGrande() {
        Log.d("@@@", "grande")
    }

    override fun onPermissionLocationDenied() {
        Log.d("@@@", "denied")
    }
}