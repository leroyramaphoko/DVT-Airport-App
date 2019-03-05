package com.dvt.airportapp.utils

import android.content.Context
import android.support.design.widget.Snackbar
import android.view.ViewGroup
import com.dvt.airportapp.R

object ErrorUtil {
    @JvmStatic
    fun displayErrorSnackbar(viewGroup: ViewGroup, context: Context){
        ErrorUtil.displayErrorSnackbar(viewGroup, context, context.getString(R.string.somethingWentWrong))
    }

    @JvmStatic
    fun displayErrorSnackbar(viewGroup: ViewGroup, context: Context, errorMessage: String){
        Snackbar.make(viewGroup, errorMessage, Snackbar.LENGTH_INDEFINITE).show()
    }
}