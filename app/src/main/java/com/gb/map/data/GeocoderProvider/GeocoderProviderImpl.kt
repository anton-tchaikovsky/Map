package com.gb.map.data.GeocoderProvider

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng

class GeocoderProviderImpl(context: Context): GeocoderProvider {
    private val geoCoder = Geocoder(context)
    @Suppress("DEPRECATION")
    override suspend fun getFromLocation(latLng: LatLng, maxResult: Int): MutableList<Address>? =
        geoCoder.getFromLocation(latLng.latitude, latLng.longitude, maxResult)

}