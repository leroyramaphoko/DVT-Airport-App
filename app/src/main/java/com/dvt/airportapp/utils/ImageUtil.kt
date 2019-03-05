package com.dvt.airportapp.utils


import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.dvt.airportapp.R

object ImageUtil {
    @JvmStatic
    fun getIcon(context: Context, status: String): Drawable? {
        when (status) {
            "active" -> return ContextCompat.getDrawable(context, R.drawable.red_dot_x1)
        }

        return ContextCompat.getDrawable(context, R.drawable.green_dot_x1)
    }
}