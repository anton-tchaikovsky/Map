package com.gb.map.data.LocationProvider

import android.location.Location

interface LocationProvider {
    fun getLocation(): Location?
}