package com.dvt.airportapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.databinding.DataBindingUtil
import android.location.Location
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.dvt.airportapp.R
import com.dvt.airportapp.constants.Keys.Companion.KEY_AIRPORT
import com.dvt.airportapp.databinding.ActivityMainBinding
import com.dvt.airportapp.models.Airport
import com.dvt.airportapp.models.viewModels.ScreenVM
import com.dvt.airportapp.services.requests.AirportNearbyService
import com.dvt.airportapp.services.responses.ErrorResponse
import com.dvt.airportapp.utils.ErrorUtil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_airport_schedule.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private lateinit var activityReference: AppCompatActivity
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var screen: ScreenVM
    private lateinit var airportNearbyService: AirportNearbyService
    private lateinit var gson: Gson
    private var airports: ArrayList<Airport> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityReference = this
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        screen = ScreenVM(false)
        gson = Gson()
        activityMainBinding.screen = screen
        airportNearbyService = AirportNearbyService(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap?) {
        screen.loading = true
        googleMap!!.setOnMarkerClickListener(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                if (location != null) {
                    val latLng = LatLng(location.latitude, location.longitude)
                    googleMap.isMyLocationEnabled = true
                    drawMapCircle(googleMap, latLng)
                    airportNearbyService.get(location, object : JsonHttpResponseHandler() {
                        override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject?) {
                            if (response!!.getString("error") != null) {
                                val errorResponse = gson.fromJson<ErrorResponse>(response.toString(), ErrorResponse::class.java)
                                ErrorUtil.displayErrorSnackbar(container, activityReference, errorResponse.error.text)
                            }
                        }

                        override fun onSuccess(statusCode: Int, headers: Array<Header>, jsonArray: JSONArray) {
                            airports = gson.fromJson(jsonArray.toString() , Array<Airport>::class.java).toList()
                            as ArrayList<Airport>
                            airports.forEach { airport ->
                                addMarker(googleMap, airport.getLatLng(), airport.codeIataAirport)
                            }

                            val update = CameraUpdateFactory.newLatLngZoom(latLng,
                                    10F)
                            googleMap.moveCamera(update)

                            screen.loading = false
                        }

                        override fun onFailure(
                            statusCode: Int,
                            headers: Array<out Header>?,
                            throwable: Throwable?,
                            errorResponse: JSONObject?
                        ) {
                            super.onFailure(statusCode, headers, throwable, errorResponse)
                            displayErrorSnackbar()
                        }

                        override fun onFailure(
                            statusCode: Int,
                            headers: Array<out Header>?,
                            throwable: Throwable?,
                            errorResponse: JSONArray?
                        ) {
                            super.onFailure(statusCode, headers, throwable, errorResponse)
                            displayErrorSnackbar()
                        }
                    })
                }
            }

            .addOnFailureListener {
                displayErrorSnackbar()
                screen.loading = false
            }
    }

    private fun displayErrorSnackbar() {
        ErrorUtil.displayErrorSnackbar(container, this)
    }

    private fun drawMapCircle(googleMap: GoogleMap, point: LatLng) {
        googleMap.addCircle(CircleOptions()
            .center(LatLng(point.latitude, point.longitude))
            .radius(10000.0)
            .strokeWidth(1F)
            .strokeColor(ContextCompat.getColor(activityReference, R.color.mapCircle))
            .fillColor(ContextCompat.getColor(activityReference, R.color.mapCircle)))
    }

    private fun addMarker(googleMap: GoogleMap?, latLng: LatLng, tag: String?) {
        val marker = googleMap!!.addMarker(MarkerOptions().position(latLng))
        marker.tag = tag
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        val airport = airports.find { airport ->
            airport.codeIataAirport == marker!!.tag.toString()
        }

        openAirportSchedule(airport)
        return true
    }

    private fun openAirportSchedule(airport: Airport?) {
        val intent = Intent(this, AirportScheduleActivity::class.java)
        intent.putExtra(KEY_AIRPORT, airport)
        startActivity(intent)
    }
}
