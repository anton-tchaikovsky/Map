package com.gb.map.presentation.locationsList

import com.gb.map.data.LocationDto
import com.gb.map.repository.LocationRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class LocationsListPresenterImpl(private val locationRepository: LocationRepository) :
    LocationsListContract.LocationsListPresenter {

    private val setIdChangedLocation: MutableSet<Long> = mutableSetOf()

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main
            + SupervisorJob()
            + CoroutineExceptionHandler { _, throwable ->
        errorHandler(throwable)
    })

    private val locationsList: MutableList<LocationDto> = mutableListOf()

    private var locationsListView: LocationsListContract.LocationsListView? = null

    override fun attach(locationsListView: LocationsListContract.LocationsListView) {
        this.locationsListView = locationsListView
        coroutineScope.launch {
            locationsList.addAll(locationRepository.getLocations())
            this@LocationsListPresenterImpl.locationsListView?.showLocationsList(locationsList)
        }
    }

    override fun detach() {
        locationsListView = null
    }

    override fun onChangedName(name: String, locationId: Long) {
        val changedPosition = locationsList.indexOfFirst {
            it.id == locationId
        }
        locationsList[changedPosition].name = name
        locationsListView?.setLocationList(locationsList)
        setIdChangedLocation.add(locationId)
    }

    override fun onChangedAnnotation(annotation: String, locationId: Long) {
        val changedPosition = locationsList.indexOfFirst {
            it.id == locationId
        }
        locationsList[changedPosition].annotation = annotation
        locationsListView?.setLocationList(locationsList)
        setIdChangedLocation.add(locationId)
    }

    override fun onQuerySaveChangedLocations() {
        val savingLocationsList: MutableList<LocationDto> = mutableListOf()
        setIdChangedLocation.forEach {id ->
            savingLocationsList.add(locationsList[locationsList.indexOfFirst {
                it.id == id
            }])
        }
        coroutineScope.launch {
            locationRepository.updateLocation(savingLocationsList)
        }
    }

    override fun onQueryDeleteLocation(locationId: Long) {
        val deletePosition =locationsList.indexOfFirst {
            it.id == locationId
        }
        val deleteLocation = locationsList.removeAt(deletePosition)
        setIdChangedLocation.remove(locationId)
        locationsListView?.deleteLocation(locationsList, deletePosition)
        coroutineScope.launch {
            locationRepository.deleteLocation(deleteLocation)
        }
    }

    private fun errorHandler(error: Throwable) {
        locationsListView?.showError(error.message ?: UNKNOWN_ERROR)
    }

    companion object {
        private const val UNKNOWN_ERROR = "Unknown error"
    }
}