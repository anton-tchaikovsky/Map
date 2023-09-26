package com.gb.map.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface LocationDao {
    @Query("SELECT * FROM location")
    suspend fun getLocations(): List<LocationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(locationEntity: LocationEntity): Long

    @Update
    suspend fun updateLocation(locationEntityList: List<LocationEntity>)

    @Delete
    suspend fun deleteLocation(locationEntity: LocationEntity)
}