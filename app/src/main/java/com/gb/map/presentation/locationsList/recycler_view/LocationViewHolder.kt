package com.gb.map.presentation.locationsList.recycler_view

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.gb.map.data.LocationDto

abstract class LocationViewHolder(binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(locationDto: LocationDto)
}