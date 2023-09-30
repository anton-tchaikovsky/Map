package com.gb.map.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class LocationEntity(
    @ColumnInfo(name = "latitude")
    val lat: Double,
    @ColumnInfo(name = "longitude")
    val lng: Double,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "annotation")
    var annotation: String,
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0
)
