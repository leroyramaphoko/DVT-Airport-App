package com.dvt.airportapp.activities

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.dvt.airportapp.R
import com.dvt.airportapp.adapters.AirportScheduleAdapter
import com.dvt.airportapp.constants.Keys.Companion.KEY_AIRPORT
import com.dvt.airportapp.databinding.AirportScheduleActivityBinding
import com.dvt.airportapp.models.Airport
import com.dvt.airportapp.models.AirportSchedule
import com.dvt.airportapp.models.viewModels.ScreenVM
import com.dvt.airportapp.services.requests.AirportScheduleService
import com.dvt.airportapp.services.responses.ErrorResponse
import com.dvt.airportapp.utils.ErrorUtil
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_airport_schedule.*
import org.json.JSONArray
import org.json.JSONObject


class AirportScheduleActivity : AppCompatActivity() {
    private lateinit var activityReference: AppCompatActivity
    private lateinit var airportScheduleActivityBinding: AirportScheduleActivityBinding
    private lateinit var airportScheduleService: AirportScheduleService
    lateinit var screen: ScreenVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityReference = this
        airportScheduleActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_airport_schedule)
        val airport: Airport = intent.getParcelableExtra(KEY_AIRPORT)
        screen = ScreenVM(false)
        airportScheduleActivityBinding.screen = screen
        setToolbar(toolbar, true)
        toolbar_layout.title = airport.nameAirport
        toolbar_layout.setExpandedTitleColor(ContextCompat.getColor(activityReference, R.color.colorPrimaryText))
        toolbar_layout.setCollapsedTitleTextColor(ContextCompat.getColor(activityReference, R.color.colorPrimaryText))
        airportScheduleService = AirportScheduleService(this)
        loadSchedule(airport.codeIataAirport)
        rvAirportSchedules.layoutManager = LinearLayoutManager(this)
    }

    private fun setToolbar(toolbar: Toolbar, enabledBackButton: Boolean) {
        setSupportActionBar(toolbar)

        if (enabledBackButton && this.supportActionBar != null) {
            this.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            this.supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

    }

    private fun loadSchedule(iataCode: String?) {
        screen.loading = true
        airportScheduleService.get(iataCode, object : JsonHttpResponseHandler() {

            override fun onSuccess(statusCode: Int, headers: Array<Header>, jsonArray: JSONArray) {
                val gson = GsonBuilder().setDateFormat(getString(R.string.date_format)).create()
                val airportSchedules = gson.fromJson(jsonArray.toString() , Array<AirportSchedule>::class.java).toList()
//                airportSchedules = filterAirportSchedules(airportSchedules, KEY_DEPARTED, KEY_BOARDING, "active")
                rvAirportSchedules.adapter = AirportScheduleAdapter(airportSchedules as ArrayList<AirportSchedule>)
                screen.loading = false
            }

            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject?) {
                if (response!!.getString("error") != null) {
                    val gson = Gson()
                    val errorResponse = gson.fromJson<ErrorResponse>(response.toString(), ErrorResponse::class.java)
                    ErrorUtil.displayErrorSnackbar(container, activityReference, errorResponse.error.text)
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                throwable: Throwable?,
                errorResponse: JSONObject?
            ) {
                super.onFailure(statusCode, headers, throwable, errorResponse)
                displayErrorSnackbar()
                finish()
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                throwable: Throwable?,
                errorResponse: JSONArray?
            ) {
                super.onFailure(statusCode, headers, throwable, errorResponse)
                displayErrorSnackbar()
                finish()
            }
        })
    }

    private fun displayErrorSnackbar() {
        ErrorUtil.displayErrorSnackbar(container, this)
    }

    private fun filterAirportSchedules(airportSchedules: List<AirportSchedule>, vararg filters: String) : List<AirportSchedule> {
        return airportSchedules.filter { airportSchedule ->
            filters.contains(airportSchedule.status)
        }
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(menuItem)
    }
}
