package com.gb.map.presentation

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.gb.map.R
import com.gb.map.data.GeocoderProviderImpl
import com.gb.map.data.LocationProviderImpl
import com.gb.map.repository.LocationRepositoryImpl

abstract class BasePermissionLocationFragment : Fragment(), MapContract.MapView {

    private val requestPermissionsLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isPermission ->
            if (isPermission)
                mapPresenter.onPermissionLocationGrande()
            else {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) // срабатывает много раз после отказа с “Never ask again” (после Rationale)
                    createAlertDialogOpenAppSettings()
                else
                    mapPresenter.onPermissionLocationDenied()
            }
        }

    private val requestPermissionsLauncherRationale: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isPermission ->
            if (isPermission)
                mapPresenter.onPermissionLocationGrande()
            else {
                mapPresenter.onPermissionLocationDenied()
                if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) // срабатывает один раз при отказе с “Never ask again” (при Rationale)
                    createAlertDialogNeverAskAgain()
            }
        }

    protected lateinit var mapPresenter: MapContract.MapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })
        mapPresenter = MapPresenterImpl(
            LocationRepositoryImpl(
                LocationProviderImpl(requireContext()),
                GeocoderProviderImpl(requireContext())
            )
        )
        mapPresenter.attach(this)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroyView() {
        mapPresenter.detach()
        super.onDestroyView()
    }

    override fun checkPermissionLocation() {
        // проверка, есть ли разрешение на чтение локации
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> mapPresenter.onPermissionLocationGrande()
            //  запрашиваем разрешение (с Rationale) - вызывается в случае первичного отказа пользователя в разрешении на чтение локации
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> createAlertDialogRationale()
            else -> requestPermissionsLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION) // запрашиваем разрешение (без Rationale)
        }
    }

    private fun createAlertDialogRationale() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.access_gps))
            .setMessage(getString(R.string.access_gps_dialog_rationale))
            .setPositiveButton(getString(R.string.resume)) { _, _ ->
                requestPermissionsLauncherRationale.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                mapPresenter.onPermissionLocationDenied()
                dialog.dismiss()
            }
            .show()
    }

    private fun createAlertDialogOpenAppSettings() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.access_gps))
            .setMessage(getString(R.string.access_gps_dialog_open_app_settings))
            .setPositiveButton(android.R.string.ok) { _, _ ->
                openAppSetting() // открываем настройки приложения
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                mapPresenter.onPermissionLocationDenied()
                dialog.dismiss()
            }
            .show()
    }

    private fun createAlertDialogNeverAskAgain() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.access_gps))
            .setMessage(getString(R.string.access_gps_never_ask_again))
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun openAppSetting() {
        startActivity(Intent().apply {
            action = android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.parse("package:" + context?.packageName)
        })
    }

}