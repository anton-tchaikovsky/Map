package com.gb.map.repository

import com.gb.map.data.LocationDto
import com.google.android.gms.maps.model.LatLng

interface LocationRepository {
   val defaultLocation: LocationDto

   fun getCurrentLatLng(): LatLng?

   suspend fun getLocation(latLng: LatLng): LocationDto

   suspend fun getLocations(): List<LocationDto>

   suspend fun insertLocation(locationDto: LocationDto): Long

   suspend fun updateLocation(locationDto: LocationDto)

   suspend fun deleteLocation(locationDto: LocationDto)
}