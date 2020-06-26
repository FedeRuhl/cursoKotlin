package com.example.myappointments.ui

import com.example.myappointments.Model.Appointment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.example.myappointments.R
import kotlinx.android.synthetic.main.item_appointment.view.*

class AppointmentAdapter : RecyclerView.Adapter<AppointmentAdapter.ViewHolder>(){

    var appointments = ArrayList<Appointment>()

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bind(appointment: Appointment) = with(itemView){
            tvAppointmentId.text = context.getString(R.string.item_appointment_id, appointment.id)
            tvDoctorName.text = appointment.doctor.name
            tvScheduledDate.text = context.getString(R.string.item_appointment_date, appointment.scheduledDate)
            tvScheduledTime.text = context.getString(R.string.item_appointment_time, appointment.scheduledTime)
            //el with evita escribir itemView.tvAppointmentId, itemView.tvDoctorName, itemView.context, etc.
            tvSpecialty.text = appointment.specialty.name
            tvDescription.text = appointment.description
            tvStatus.text = appointment.status
            tvType.text = appointment.type
            tvCreatedAt.text = context.getString(R.string.item_appointment_created_at, appointment.createdAt)

            ibExpand.setOnClickListener {
                TransitionManager.beginDelayedTransition(parent as ViewGroup, Fade()) //cuando se ven los detalles del turno, se usa un efecto

                if (llDetails.visibility == View.VISIBLE){
                    llDetails.visibility = View.GONE
                    ibExpand.setImageResource(R.drawable.ic_expand_more)
                }
                else{
                    llDetails.visibility = View.VISIBLE
                    ibExpand.setImageResource(R.drawable.ic_expand_less)
                }
            }
        }

    }

    // crear la vista a partir del XML - 3
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_appointment,
                parent,
                false
            )
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