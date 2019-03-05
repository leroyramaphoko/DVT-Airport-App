package com.dvt.airportapp.models

data class AirportSchedule(val type: String,
                           val status: String,
                           val departure: Departure,
                           val arrival: Arrival,
                           val airline: Airline,
                           val flight: Flight)