package com.gb.map.data

import com.google.android.gms.maps.model.LatLng

data class LocationDto(
    val latLng: LatLng,
    var name: String,
    var annotation: String = DEFAULT_ANNOTATION,
    var id: Long = 0
){
    companion object{
        private const val DEFAULT_ANNOTATION = "annotation"
    }
}