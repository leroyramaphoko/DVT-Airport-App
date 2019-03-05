package com.dvt.airportapp.services.requests

import android.content.Context
import com.dvt.airportapp.R
import com.dvt.airportapp.constants.Keys.Companion.KEY_DEPARTURE
import com.dvt.airportapp.constants.Keys.Companion.KEY_IATA_CODE
import com.dvt.airportapp.constants.Keys.Companion.KEY_TYPE
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams

class AirportScheduleService(context: Context): BaseService(context) {
    override fun getEndpoint(): String {
        return buildEndpoint(R.string.timetable_endpoint)
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

    fun get(iataCode: String?, jsonHttpResponseHandler: JsonHttpResponseHandler) {
        val params = RequestParams()
        params.add(KEY_IATA_CODE, iataCode)
        params.add(KEY_TYPE, KEY_DEPARTURE)
        get(params, jsonHttpResponseHandler)
    }

}