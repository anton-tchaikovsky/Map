package com.gb.map.repository

import com.gb.map.data.LocationDto

interface LocationRepository {
   val defaultLocation: LocationDto
   suspend fun getLocation(): LocationDto
}