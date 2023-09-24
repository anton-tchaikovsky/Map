package com.gb.map.repository

import com.gb.map.data.LocationDto
import com.google.android.gms.maps.model.LatLng

interface LocationRepository {
   val defaultLocation: LocationDto

   fun getLatLng(): LatLng?

   suspend fun getLocationDto(latLng: LatLng): LocationDto
}