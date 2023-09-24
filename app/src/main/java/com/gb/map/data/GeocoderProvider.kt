package com.gb.map.data

import android.location.Address
import android.location.Location

interface GeocoderProvider {
    suspend fun getFromLocation(location: Location, maxResult: Int): MutableList<Address>?
}