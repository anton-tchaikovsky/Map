package com.gb.map.data

import android.location.Location

interface LocationProvider {
    fun getLocation(): Location?
}