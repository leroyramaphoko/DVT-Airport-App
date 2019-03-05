package com.dvt.airportapp.utils

object TextUtil {

    @JvmStatic
    fun capitalizeFirstLetter(original: String?): String? {
        return if (original == null || original.isEmpty()) {
            original
        } else original.substring(0, 1).toUpperCase() + original.substring(1)
    }
}

