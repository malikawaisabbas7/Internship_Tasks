package com.example.internshiptasks.Task9_Weather_and_News_app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.internshiptasks.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationPermission : AppCompatActivity() {

    private lateinit var tvPermissionTitle: TextView
    private lateinit var tvPermissionMessage: TextView
    private lateinit var btnAllowLocation: Button
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_location_permission_task9)

        tvPermissionTitle = findViewById(R.id.tvPermissionTitle)
        tvPermissionMessage = findViewById(R.id.tvPermissionMessage)
        btnAllowLocation = findViewById(R.id.btnAllowLocation)

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this)

        btnAllowLocation.setOnClickListener {

            checkLocationPermission()
        }
    }

    private fun checkLocationPermission() {

        if (
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            getCurrentLocation()

        } else {

            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        super.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        )

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {

            val fineLocationGranted =
                grantResults.getOrNull(0) == PackageManager.PERMISSION_GRANTED

            val coarseLocationGranted =
                grantResults.getOrNull(1) == PackageManager.PERMISSION_GRANTED

            if (fineLocationGranted || coarseLocationGranted) {

                getCurrentLocation()

            } else {

                tvPermissionMessage.text =
                    "Location permission is required to show local weather and news."

                Toast.makeText(
                    this,
                    "Location permission was denied.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun getCurrentLocation() {

        tvPermissionMessage.text =
            "Getting your current location..."

        if (
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }

        fusedLocationClient.getCurrentLocation(
            com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
            null
        ).addOnSuccessListener { location ->

            if (location != null) {

                val latitude = location.latitude
                val longitude = location.longitude

                openWeatherActivity(
                    latitude,
                    longitude
                )

            } else {

                tvPermissionMessage.text =
                    "Unable to get your location."

                Toast.makeText(
                    this,
                    "Please make sure Location is turned on.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun openWeatherActivity(
        latitude: Double,
        longitude: Double
    ) {

        val intent = Intent(
            this,
            WeatherActivity::class.java
        )

        intent.putExtra(
            "latitude",
            latitude
        )

        intent.putExtra(
            "longitude",
            longitude
        )

        startActivity(intent)

        finish()
    }
}