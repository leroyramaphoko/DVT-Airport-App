package com.dvt.airportapp.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dvt.airportapp.databinding.AirportScheduleItemBinding
import com.dvt.airportapp.models.AirportSchedule

class AirportScheduleAdapter(val airportSchedules : ArrayList<AirportSchedule>) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AirportScheduleItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val airportSchedule = airportSchedules[position]
        viewHolder.setAirportSchedule(airportSchedule)
    }

    override fun getItemCount(): Int {
        return airportSchedules.size
    }
}

class ViewHolder(dataBinding: AirportScheduleItemBinding) : RecyclerView.ViewHolder(dataBinding.root) {
    private val airportScheduleItemBinding: AirportScheduleItemBinding = dataBinding

    fun setAirportSchedule(airportSchedule: AirportSchedule) {
        airportScheduleItemBinding.airportSchedule = airportSchedule
    }
}