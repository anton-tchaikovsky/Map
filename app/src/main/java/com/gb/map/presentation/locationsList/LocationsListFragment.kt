package com.gb.map.presentation.locationsList

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gb.map.data.LocationDto
import com.gb.map.databinding.FragmentLocationListBinding
import com.gb.map.presentation.locationsList.recycler_view.ItemTouchHelperCallback
import com.gb.map.presentation.locationsList.recycler_view.LocationsListAdapter
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

class LocationsListFragment : Fragment(), AndroidScopeComponent,
    LocationsListContract.LocationsListView {

    override val scope: Scope by fragmentScope()

    private val locationsListPresenter: LocationsListContract.LocationsListPresenter by inject()

    private val locationsListAdapter: LocationsListAdapter by inject {
        parametersOf(
            ::nameChangedPlayerClickListener,
            ::annotationChangedPlayerClickListener,
            ::removeLocation
        )
    }

    private val itemTouchHelperCallback: ItemTouchHelperCallback by inject()

    private var _binding: FragmentLocationListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.locationsListRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = locationsListAdapter
            ItemTouchHelper(itemTouchHelperCallback)
                .attachToRecyclerView(this@apply)
        }
        locationsListPresenter.attach(this)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onPause() {
        locationsListPresenter.onQuerySaveChangedLocations()
        super.onPause()
    }

    override fun onDestroyView() {
        _binding = null
        locationsListPresenter.detach()
        super.onDestroyView()
    }

    override fun showError(message: String) =
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()

    private fun nameChangedPlayerClickListener(name: String, locationId: Long) {
        locationsListPresenter.onChangedName(name, locationId)
    }

    private fun annotationChangedPlayerClickListener(annotation: String, locationId: Long) {
        locationsListPresenter.onChangedAnnotation(annotation, locationId)
    }

    private fun removeLocation(locationId: Long) =
        locationsListPresenter.onQueryDeleteLocation(locationId)

    companion object {
        fun newInstance() =
            LocationsListFragment()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun showLocationsList(locationsList: List<LocationDto>) {
        locationsListAdapter.apply{
            this.locationsList = locationsList
            notifyDataSetChanged()
        }
    }

    override fun setLocationList(locationsList: List<LocationDto>) {
        locationsListAdapter.locationsList = locationsList
    }

    override fun deleteLocation(locationsList: List<LocationDto>, deletePosition: Int) {
        locationsListAdapter.apply {
            this.locationsList = locationsList
            notifyItemRemoved(deletePosition)
        }
    }
}