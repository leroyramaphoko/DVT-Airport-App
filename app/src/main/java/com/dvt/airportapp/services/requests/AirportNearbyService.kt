package com.dvt.airportapp.services.requests

import android.content.Context
import android.location.Location
import com.dvt.airportapp.R
import com.dvt.airportapp.constants.Keys.Companion.KEY_DISTANCE
import com.dvt.airportapp.constants.Keys.Companion.KEY_LAT
import com.dvt.airportapp.constants.Keys.Companion.KEY_LNG
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams

class AirportNearbyService(context: Context): BaseService(context) {
    override fun getEndpoint(): String {
        return buildEndpoint(R.string.nearby_endpoint)
    }

    override fun updateEndpoint(): String {
        return ""
    }

    override fun postEndpoint(): String {
        return ""
    }

    override fun deleteEndpoint(): String {
        return ""
    }

    fun get(location: Location, jsonHttpResponseHandler: JsonHttpResponseHandler) {
        val params = RequestParams()
        params.add(KEY_LAT, location.latitude.toString())
        params.add(KEY_LNG, location.longitude.toString())
        params.add(KEY_DISTANCE, getInt(R.integer.distance).toString())
        get(params, jsonHttpResponseHandler)
    }
}