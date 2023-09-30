package com.gb.map.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocationEntity::class], version = 1, exportSchema = false)
abstract class MapDatabase: RoomDatabase() {
    abstract fun getLocationDao(): LocationDao
}