package com.dvt.airportapp.models.viewModels

import android.databinding.BaseObservable

class ScreenVM(loading: Boolean) : BaseObservable() {
    var loading: Boolean = loading
        set(value) {
            if (field != value) {
                field = value
                notifyChange()
            }
        }
}