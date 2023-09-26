package com.gb.map.presentation.locationsList

import com.gb.map.data.LocationDto

interface LocationsListContract {

    interface LocationsListView{
        fun showLocationsList(locationsList: List<LocationDto>)

        fun setLocationList(locationsList: List<LocationDto>)

        fun deleteLocation(locationsList: List<LocationDto>, deletePosition: Int)

        fun showError(message: String)
    }

    interface LocationsListPresenter{
        fun attach(locationsListView: LocationsListView)

        fun detach()

        fun onChangedName(name: String, locationId: Long)

        fun onChangedAnnotation(annotation: String, locationId: Long)

        fun onQuerySaveChangedLocations()

        fun onQueryDeleteLocation(locationId: Long)
    }
}