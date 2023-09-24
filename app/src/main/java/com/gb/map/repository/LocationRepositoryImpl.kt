package com.gb.map.repository

import com.gb.map.data.GeocoderProvider
import com.gb.map.data.LocationDto
import com.gb.map.data.LocationProvider
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationRepositoryImpl(
    private val locationProvider: LocationProvider,
    private val geocoderProvider: GeocoderProvider
) : LocationRepository {

    override val defaultLocation: LocationDto
        get() = LocationDto(DEFAULT_LOCATION, DEFAULT_CITY)

    override fun getLatLng(): LatLng? {
        val location = locationProvider.getLocation()
        return if (location != null)
            LatLng(location.latitude, location.longitude)
        else null
    }

    override suspend fun getLocationDto(latLng: LatLng): LocationDto {
        var locationName = ""
        return withContext(Dispatchers.IO
                + CoroutineExceptionHandler { _, _ ->
            LocationDto(latLng, locationName)
        }) {
            geocoderProvider.getFromLocation(latLng, MAX_RESULT)?.get(0)?.let {
                locationName = it.getAddressLine(0)
            }
            LocationDto(latLng, locationName)
        }
    }

    companion object {
        val DEFAULT_LOCATION = LatLng(-34.0, 151.0)
        private const val DEFAULT_CITY = "Sidney"
        private const val MAX_RESULT = 1
    }
}