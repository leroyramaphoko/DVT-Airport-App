package com.dvt.airportapp.services.requests

import android.content.Context
import com.dvt.airportapp.R
import com.dvt.airportapp.constants.Keys.Companion.KEY_API_KEY
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams

abstract class BaseService(val context: Context?) {
    abstract fun getEndpoint(): String

    abstract fun updateEndpoint(): String

    abstract fun postEndpoint(): String

    abstract fun deleteEndpoint(): String

    private val client = AsyncHttpClient()

    private fun getString(resId: Int) : String {
        return context!!.getString(resId)
    }

    protected fun getInt(resId: Int) : Int {
        return context!!.resources.getInteger(resId)
    }

    private fun getBaseUrl() : String {
        return getString(R.string.base_url)
    }

    protected fun buildEndpoint(resId: Int) : String {
        return getString(resId).replace("{$KEY_API_KEY}", getString(R.string.api_key))
    }

    fun get(params: RequestParams, jsonHttpResponseHandler: JsonHttpResponseHandler) {
        val url = getBaseUrl() + getEndpoint() + "&" + params
        client.get(url, jsonHttpResponseHandler)
    }
}