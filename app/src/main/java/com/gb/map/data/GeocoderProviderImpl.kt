package com.gb.map.data

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location

class GeocoderProviderImpl(context: Context): GeocoderProvider {
    private val geoCoder = Geocoder(context)
    @Suppress("DEPRECATION")
    override suspend fun getFromLocation(location: Location, maxResult: Int): MutableList<Address>? =
        geoCoder.getFromLocation(location.latitude, location.longitude, maxResult)

}