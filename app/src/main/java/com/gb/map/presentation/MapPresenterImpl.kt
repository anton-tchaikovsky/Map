package com.gb.map.presentation

import com.gb.map.repository.LocationRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapPresenterImpl(private val locationRepository: LocationRepository) :
    MapContract.MapPresenter {

    private var mapView: MapContract.MapView? = null

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main
    + CoroutineExceptionHandler { _, _ ->
        mapView?.showMarker(locationRepository.defaultLocation)
    })


    override fun attach(mapView: MapContract.MapView) {
        this.mapView = mapView
    }

    override fun detach() {
        mapView = null
    }

    override fun onPermissionLocationGrande() {
        coroutineScope.launch {
            mapView?.showMarker(locationRepository.getLocation())
        }
    }

    override fun onPermissionLocationDenied() {
        mapView?.showMarker(locationRepository.defaultLocation)
    }

    override fun onMapReading() {
        mapView?.checkPermissionLocation()
    }
}