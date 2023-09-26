package com.gb.map.presentation.map

import com.gb.map.data.LocationDto
import com.gb.map.repository.LocationRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MapPresenterImpl(private val locationRepository: LocationRepository) :
    MapContract.MapPresenter {

    private var mapView: MapContract.MapView? = null

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main
            + SupervisorJob()
            + CoroutineExceptionHandler { _, throwable ->
        errorHandler(throwable)
    })

    override fun attach(mapView: MapContract.MapView) {
        this.mapView = mapView
    }

    override fun detach() {
        mapView = null
    }

    override fun onPermissionLocationGrande() {
        val latLng = locationRepository.getCurrentLatLng()
        if (latLng != null)
            coroutineScope.launch {
                mapView?.showMarker(locationRepository.getLocation(latLng))
            }
        else
            mapView?.showMarker(locationRepository.defaultLocation)
    }

    override fun onPermissionLocationDenied() {
        mapView?.showMarker(locationRepository.defaultLocation)
    }

    override fun onMapReading() {
        mapView?.run{
            clearMap()
            checkPermissionLocation()
            coroutineScope.launch {
                locationRepository.getLocations().forEach {
                    showMarker(it, false)
                }
            }
        }
    }

    override fun onAddMarker(latLng: LatLng) {
        coroutineScope.launch {
            mapView?.showMarker(
                locationRepository.getLocation(latLng).also { saveLocation(it) },
                false
            )
        }
    }

    override fun onClickOpenLocationsList() {
        mapView?.openLocationsList()
    }

    private suspend fun saveLocation(locationDto: LocationDto) {
        locationRepository.insertLocation(locationDto)
    }

    private fun errorHandler(error: Throwable) {
        mapView?.showError(error.message ?: UNKNOWN_ERROR)
    }

    companion object {
        private const val UNKNOWN_ERROR = "Unknown error"
    }
}