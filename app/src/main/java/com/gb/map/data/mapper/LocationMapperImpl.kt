package com.gb.map.data.mapper

import com.gb.map.data.LocationDto
import com.gb.map.data.room.LocationEntity
import com.google.android.gms.maps.model.LatLng

class LocationMapperImpl: LocationMapper {
    override fun mapToDto(locationEntity: LocationEntity): LocationDto =
        locationEntity.let {
            LocationDto(LatLng(it.lat, it.lng), it.name, it.annotation, id = it.id)
        }

    override fun mapToDatabase(locationDto: LocationDto): LocationEntity =
        locationDto.let {
            LocationEntity(it.latLng.latitude, it.latLng.longitude, it.name, it.annotation, it.id)
        }
}