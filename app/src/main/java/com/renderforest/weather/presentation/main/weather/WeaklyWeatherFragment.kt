package com.renderforest.weather.presentation.main.weather

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.location.LocationManagerCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.renderforest.weather.R
import com.renderforest.weather.databinding.FragmentWeaklyWeatherBinding
import com.renderforest.weather.presentation.main.common.DefaultState
import com.renderforest.weather.presentation.main.common.ErrorState
import com.renderforest.weather.presentation.main.common.LoadingState
import com.renderforest.weather.presentation.main.common.utils.ConnectionUtil
import kotlinx.android.synthetic.main.fragment_weakly_weather.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class WeaklyWeatherFragment : Fragment(), LocationListener {

    private val weatherViewModel: WeatherViewModel by viewModel()
    private val weatherAdapter = WeatherAdapter()
    private lateinit var locationManager: LocationManager
    private var location: Location? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentWeaklyWeatherBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_weakly_weather, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getCurrentLocation()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        initOnClicks()
        observeWeatherState()
    }

    private fun getCurrentLocation() {

        Dexter.withContext(activity)
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
            .withListener(object : MultiplePermissionsListener {
                @SuppressLint("MissingPermission")
                override fun onPermissionsChecked(permissions: MultiplePermissionsReport?) {
                    if (permissions!!.areAllPermissionsGranted()) {

                        if (!isLocationEnabled()) return

                        val criteria = Criteria()
                        criteria.accuracy = Criteria.ACCURACY_FINE
                        criteria.powerRequirement = Criteria.POWER_HIGH
                        criteria.isAltitudeRequired = false
                        criteria.isSpeedRequired = false
                        criteria.isCostAllowed = true
                        criteria.isBearingRequired = false
                        criteria.horizontalAccuracy = Criteria.ACCURACY_HIGH
                        criteria.verticalAccuracy = Criteria.ACCURACY_HIGH
                        locationManager.requestSingleUpdate(
                            criteria,
                            this@WeaklyWeatherFragment,
                            null
                        )
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }

            })
            .check()
    }

    private fun isLocationEnabled(): Boolean {
        if (!LocationManagerCompat.isLocationEnabled(locationManager)) {
            showEnableLocationDialog()
            return false
        }
        return true
    }

    private fun showEnableLocationDialog() {
        AlertDialog.Builder(context)
            .setTitle(R.string.no_location)
            .setMessage(R.string.no_location_message)
            .setPositiveButton(R.string.settings) { paramDialogInterface, paramInt ->
                requireContext().startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton(R.string.close, null)
            .show()
    }

    private fun observeWeatherState() {
        weatherViewModel.weatherState.observe(viewLifecycleOwner, {
            when (it) {
                is DefaultState -> {
                    if (it.days.isEmpty() && !ConnectionUtil.isInternetAvailable(requireActivity().applicationContext)) {
                        showNoInternetDialog()
                    }
                    weatherAdapter.addItems(it.days)
                    swipeRefresh.isRefreshing = false
                }
                is LoadingState -> {
                    swipeRefresh.isRefreshing = true
                }
                is ErrorState -> {
                    swipeRefresh.isRefreshing = false
                    Toast.makeText(
                        context,
                        getString(R.string.something_went_wrong),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun showNoInternetDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.no_internet_connection))
        builder.setMessage(getString(R.string.no_internet_connection_message))
        builder.setNegativeButton(getString(R.string.close)) { dialog, which ->
            dialog.dismiss()
        }
        builder.setPositiveButton(getString(R.string.settings)) { dialog, which ->
            dialog.dismiss()
            val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
            startActivity(intent)
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun initOnClicks() {

        weatherRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = weatherAdapter
        }

        swipeRefresh.setOnRefreshListener {
            location?.apply {
                weatherViewModel.getWeeklyWeather(latitude, longitude)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        weatherViewModel.weatherState.removeObservers(this)
    }

    override fun onLocationChanged(location: Location?) {
        location?.apply {
            this@WeaklyWeatherFragment.location = this
            weatherViewModel.getWeeklyWeather(latitude, longitude)
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
    }
}