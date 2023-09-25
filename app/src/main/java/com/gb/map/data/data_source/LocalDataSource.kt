package com.gb.map.data.data_source

import com.gb.map.data.room.LocationEntity

interface LocalDataSource {
    suspend fun getLocations(): List<LocationEntity>

    suspend fun insertLocation(locationEntity: LocationEntity): Long

    suspend fun updateLocation(locationEntity: LocationEntity)

    suspend fun deleteLocation(locationEntity: LocationEntity)
}