package com.gb.map.data.mapper


import com.gb.map.data.LocationDto
import com.gb.map.data.room.LocationEntity

interface LocationMapper {
    fun mapToDto(locationEntity: LocationEntity): LocationDto

    fun mapToDatabase(locationDto: LocationDto): LocationEntity
}