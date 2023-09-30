package com.gb.map.presentation.locationsList.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gb.map.data.LocationDto
import com.gb.map.databinding.FragmentLocationsListItemBinding

class LocationsListAdapter(
    private val nameChangedClickListener: (String, Long) -> Unit,
    private val annotationChangedClickListener: (String, Long) -> Unit,
    private val removeLocationClickListener: (Long) -> Unit
) : RecyclerView.Adapter<LocationViewHolder>(), ItemTouchHelperAdapter {
    var locationsList: List<LocationDto> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder =
        LocationViewHolderImpl(
            FragmentLocationsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            nameChangedClickListener,
            annotationChangedClickListener
        )

    override fun getItemCount(): Int =
        locationsList.size

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) =
        holder.bind(locationsList[position])

    override fun onRemoveItem(position: Int) {
        removeLocationClickListener(locationsList[position].id)
    }
}