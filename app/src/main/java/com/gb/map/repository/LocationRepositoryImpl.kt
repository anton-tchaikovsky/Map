package com.gb.map.repository

import com.gb.map.data.GeocoderProvider.GeocoderProvider
import com.gb.map.data.LocationDto
import com.gb.map.data.LocationProvider.LocationProvider
import com.gb.map.data.data_source.LocalDataSource
import com.gb.map.data.mapper.LocationMapper
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationRepositoryImpl(
    private val locationProvider: LocationProvider,
    private val geocoderProvider: GeocoderProvider,
    private val locationMapper: LocationMapper,
    private val localDataSource: LocalDataSource
) : LocationRepository {

    private var locationDtoList: MutableList<LocationDto>? = null

    override val defaultLocation: LocationDto
        get() = LocationDto(DEFAULT_LOCATION, DEFAULT_CITY)

    override fun getCurrentLatLng(): LatLng? {
        val location = locationProvider.getLocation()
        return if (location != null)
            LatLng(location.latitude, location.longitude)
        else null
    }

    override suspend fun getLocation(latLng: LatLng): LocationDto {
        var locationName = ""
        return withContext(Dispatchers.IO) {
            geocoderProvider.getFromLocation(latLng, MAX_RESULT)?.get(0)?.let {
                locationName = it.getAddressLine(0)
            }
            LocationDto(latLng, locationName)
        }
    }

    override suspend fun getLocations(): List<LocationDto> {
        return locationDtoList
            ?: withContext(Dispatchers.IO) {
                localDataSource.getLocations().map {
                    locationMapper.mapToDto(it)
                }.also{ locationDtoList = it.toMutableList() }
            }
    }

    override suspend fun insertLocation(locationDto: LocationDto): Long {
        return withContext(Dispatchers.IO) {
            localDataSource.insertLocation(locationMapper.mapToDatabase(locationDto)).also {
                locationDtoList?.add(locationDto.apply { id = it })
            }
        }
    }

    override suspend fun updateLocation(locationDtoList: List<LocationDto>) {
        locationDtoList.forEach {
            this.locationDtoList?.run {
                val changedPosition = indexOfFirst { location ->
                    location.id == it.id
                }
                get(changedPosition).apply {
                    this@apply.name = it.name
                    this@apply.annotation = it.annotation
                }
            }
        }
        withContext(Dispatchers.IO) {
            localDataSource.updateLocation(locationDtoList.map {
                locationMapper.mapToDatabase(it)
            })
        }
    }

    override suspend fun deleteLocation(locationDto: LocationDto) {
        this.locationDtoList?.remove(locationDto)
        withContext(Dispatchers.IO) {
            localDataSource.deleteLocation(locationMapper.mapToDatabase(locationDto))
        }
    }

    companion object {
        val DEFAULT_LOCATION = LatLng(-34.0, 151.0)
        private const val DEFAULT_CITY = "Sidney"
        private const val MAX_RESULT = 1
    }
}