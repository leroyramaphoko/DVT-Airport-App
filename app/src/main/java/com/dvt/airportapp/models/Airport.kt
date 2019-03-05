package com.dvt.airportapp.models

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng

data class Airport(val nameAirport: String?,
                   val codeIataAirport: String?,
                   val codeIcaoAirport: String?,
                   val nameTranslations: String?,
                   val latitudeAirport: Double,
                   val longitudeAirport: Double,
                   val timezone: String?,
                   val GMT: String?,
                   val phone: String?,
                   val nameCountry: String?,
                   val codeIso2Country: String?,
                   val codeIataCity: String?,
                   val distance: String?) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    fun getLatLng(): LatLng {
        return LatLng(latitudeAirport, longitudeAirport)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nameAirport)
        parcel.writeString(codeIataAirport)
        parcel.writeString(codeIcaoAirport)
        parcel.writeString(nameTranslations)
        parcel.writeDouble(latitudeAirport)
        parcel.writeDouble(longitudeAirport)
        parcel.writeString(timezone)
        parcel.writeString(GMT)
        parcel.writeString(phone)
        parcel.writeString(nameCountry)
        parcel.writeString(codeIso2Country)
        parcel.writeString(codeIataCity)
        parcel.writeString(distance)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Airport> {
        override fun createFromParcel(parcel: Parcel): Airport {
            return Airport(parcel)
        }

        override fun newArray(size: Int): Array<Airport?> {
            return arrayOfNulls(size)
        }
    }
}