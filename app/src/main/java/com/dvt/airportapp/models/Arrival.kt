package com.dvt.airportapp.models

import java.util.*

data class Arrival(val iataCode: String,
                   val icaoCode: String,
                   val terminal: String,
                   val gate: String,
                   val delay: Int,
                   val scheduledTime: Date,
                   val estimatedTime: Date,
                   val actualTime: Date)