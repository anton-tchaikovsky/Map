package com.gb.map.presentation.locationsList.recycler_view

import android.text.Editable
import android.text.TextWatcher
import com.gb.map.data.LocationDto
import com.gb.map.databinding.FragmentLocationsListItemBinding

class LocationViewHolderImpl(
    private val binding: FragmentLocationsListItemBinding,
    private val nameChangedClickListener: (String, Long) -> Unit,
    private val annotationChangedClickListener: (String, Long) -> Unit
) : LocationViewHolder(binding) {
    override fun bind(locationDto: LocationDto) {
        binding.locationNameEditText.apply {
            setText(locationDto.name)
            addTextChangedListener(createTextWatcher(nameChangedClickListener, locationDto.id))
        }
        binding.locationAnnotationEditText.apply {
            setText(locationDto.annotation)
            addTextChangedListener(createTextWatcher(annotationChangedClickListener, locationDto.id))
        }
    }

    private fun createTextWatcher(textChangedClickListener: (String, Long) -> Unit, locationId: Long) =
        object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int,
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int,
            ) {
            }

            override fun afterTextChanged(s: Editable?) {
                textChangedClickListener(s.toString(), locationId)
            }

        }
}