package com.gb.map.repository

import android.location.Location
import com.gb.map.data.GeocoderProvider
import com.gb.map.data.LocationDto
import com.gb.map.data.LocationProvider
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationRepositoryImpl(
    private val locationProvider: LocationProvider,
    private val geocoderProvider: GeocoderProvider
) : LocationRepository {

    override val defaultLocation: LocationDto
        get() = LocationDto(DEFAULT_LOCATION, DEFAULT_CITY)

    override suspend fun getLocation(): LocationDto {
        val location = locationProvider.getLocation()
        return if (location != null)
            LocationDto(LatLng(location.latitude, location.longitude), getLocationName(location))
        else LocationDto(DEFAULT_LOCATION, DEFAULT_CITY)
    }

    private suspend fun getLocationName(location: Location): String {
        var locationName = ""
        withContext(Dispatchers.IO){
            geocoderProvider.getFromLocation(location, MAX_RESULT)?.get(0)?.let {
                locationName = it.getAddressLine(0)
            }
        }
        return locationName
    }

    companion object {
        val DEFAULT_LOCATION = LatLng(-34.0, 151.0)
        private const val DEFAULT_CITY = "Sidney"
        private const val MAX_RESULT = 1
    }
}