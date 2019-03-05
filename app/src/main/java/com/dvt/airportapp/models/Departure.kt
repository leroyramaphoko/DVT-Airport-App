package com.dvt.airportapp.models

import java.util.*

data class Departure(val iataCode: String,
                     val icaoCode: String,
                     val terminal: String,
                     val scheduledTime: Date,
                     val estimatedTime: Date,
                     val actualTime: Date,
                     val estimatedRunway: Date,
                     val actualRunway: Date)