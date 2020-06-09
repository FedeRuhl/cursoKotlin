package com.example.myappointments.ui

import com.example.myappointments.Model.Appointment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myappointments.R
import kotlinx.android.synthetic.main.activity_appointments.*

class AppointmentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointments)

        val appointmentsDataSet = ArrayList<Appointment>()
        appointmentsDataSet.add(Appointment(1, "Medico test", "28/05/2020", "15:00"))
        appointmentsDataSet.add(Appointment(2, "Medico B", "01/06/2020", "16:00"))
        appointmentsDataSet.add(Appointment(3, "Medico C", "04/06/2020", "17:00"))

        rvAppointments.layoutManager = LinearLayoutManager(this) //layout manager indica como se van a mostrar los elementos
        rvAppointments.adapter =
            AppointmentAdapter(appointmentsDataSet)//el adapter adapta la informacion para mostrarla en XML e indica como renderizarla, creamos una clase kotlin para esto
    }
}
