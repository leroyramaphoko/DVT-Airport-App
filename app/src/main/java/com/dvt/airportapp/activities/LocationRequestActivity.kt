package com.dvt.airportapp.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.dvt.airportapp.R
import com.dvt.airportapp.databinding.LocationRequestBinding
import com.dvt.airportapp.interfaces.IHandler

class LocationRequestActivity : AppCompatActivity(), IHandler {
    private val LOCATION_REQUEST_CODE = 100
    lateinit var locationRequestBinding: LocationRequestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationRequestBinding = DataBindingUtil.setContentView(this, R.layout.activity_location_request)
        locationRequestBinding.handler = this

        if (isAccessFileLocationAllowed()) navigateToNextIntent()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnEnableLocation -> requestLocationPermission()
        }
    }

    private fun isAccessFileLocationAllowed() : Boolean {
        return (ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        thePermissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                   navigateToNextIntent()
                }
            }
        }
    }

    private fun navigateToNextIntent() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}
