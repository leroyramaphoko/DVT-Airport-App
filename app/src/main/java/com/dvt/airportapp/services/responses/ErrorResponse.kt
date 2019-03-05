package com.dvt.airportapp.services.responses

data class ErrorResponse(val error: ErrorObject)

data class ErrorObject(val text: String)