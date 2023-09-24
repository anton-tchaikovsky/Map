package com.gb.map.data

import com.google.android.gms.maps.model.LatLng

data class LocationDto(
    val latLng: LatLng,
    val name: String
)