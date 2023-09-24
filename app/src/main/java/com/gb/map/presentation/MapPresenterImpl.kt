package com.gb.map.presentation

import com.gb.map.repository.LocationRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapPresenterImpl(private val locationRepository: LocationRepository) :
    MapContract.MapPresenter {

    private var mapView: MapContract.MapView? = null

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    override fun attach(mapView: MapContract.MapView) {
        this.mapView = mapView
    }

    override fun detach() {
        mapView = null
    }

    override fun onPermissionLocationGrande() {
        val latLng = locationRepository.getLatLng()
        if (latLng!=null)
            coroutineScope.launch {
                mapView?.showMarker(locationRepository.getLocationDto(latLng))
            }
        else
            mapView?.showMarker(locationRepository.defaultLocation)
    }

    override fun onPermissionLocationDenied() {
        mapView?.showMarker(locationRepository.defaultLocation)
    }

    override fun onMapReading() {
        mapView?.checkPermissionLocation()
    }

    override fun onAddMarker(latLng: LatLng) {
        coroutineScope.launch {
            mapView?.showMarker(locationRepository.getLocationDto(latLng), false)
        }
    }
}