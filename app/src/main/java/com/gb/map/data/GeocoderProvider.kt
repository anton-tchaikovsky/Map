package com.gb.map.data

import android.location.Address
import com.google.android.gms.maps.model.LatLng

interface GeocoderProvider {
    suspend fun getFromLocation(latLng: LatLng, maxResult: Int): MutableList<Address>?
}