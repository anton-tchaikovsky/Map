package com.gb.map.data.data_source

import com.gb.map.data.room.LocationEntity
import com.gb.map.data.room.MapDatabase

class LocalDataSourceImpl(val mapDatabase: MapDatabase): LocalDataSource {

    private val locationDao = mapDatabase.getLocationDao()

    override suspend fun getLocations(): List<LocationEntity> =
        locationDao.getLocations()

    override suspend fun insertLocation(locationEntity: LocationEntity): Long =
        locationDao.insertLocation(locationEntity)

    override suspend fun updateLocation(locationEntityList: List<LocationEntity>) =
        locationDao.updateLocation(locationEntityList)

    override suspend fun deleteLocation(locationEntity: LocationEntity) =
        locationDao.deleteLocation(locationEntity)
}