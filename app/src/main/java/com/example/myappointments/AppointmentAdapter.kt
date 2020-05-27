package com.example.myappointments

import Model.Appointment
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_appointment.view.*

class AppointmentAdapter(private val appointments:ArrayList<Appointment>) : RecyclerView.Adapter<AppointmentAdapter.ViewHolder>(){
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bind(appointment: Appointment) = with(itemView){
            tvAppointmentId.text = context.getString(R.string.item_appointment_id, appointment.id)
            tvDoctorName.text = appointment.doctorName
            tvScheduledDate.text = context.getString(R.string.item_appointment_date, appointment.scheduledDate)
            tvScheduledTime.text = context.getString(R.string.item_appointment_time, appointment.scheduledTime)
            //el with evita escribir itemView.tvAppointmentId, itemView.tvDoctorName, itemView.context, etc.
        }

    }

    // crear la vista a partir del XML - 3
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_appointment, parent, false)
        )
    }

    //retorna la cantidad de elementos - 1
    override fun getItemCount() = appointments.size

    //asociar el data set con la vista - 2
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.bind(appointment)
    }

    //ctrl + O implementan solo los override
}