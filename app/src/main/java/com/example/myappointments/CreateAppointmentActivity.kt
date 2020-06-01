package com.example.myappointments

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_create_appointment.*
import java.util.*

class CreateAppointmentActivity : AppCompatActivity() {

    private val selectedCalendar = Calendar.getInstance()
    private var selectedRadioButton: RadioButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_appointment)

        btnNext.setOnClickListener {
            if (etDescription.text.toString().length < 3)
                etDescription.error = getString(R.string.validate_appointment_description)
            else {
                cvStep1.visibility = View.GONE;
                cvStep2.visibility = View.VISIBLE;
            }
        }

        btnConfirm.setOnClickListener {
            Toast.makeText(this, "Turno registrado correctamente", Toast.LENGTH_LONG).show()
            finish()
        }

        val specialtyOptions = arrayOf("Specialty A", "Specialty B", "Specialty C")
        spinnerSpecialties.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, specialtyOptions)

        val doctorOptions = arrayOf("Doctor A", "Doctor B", "Doctor C")
        spinnerDoctors.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, doctorOptions)

    }

    fun onClickScheduledDate(v: View?) {
        val year = selectedCalendar.get(Calendar.YEAR)
        val month = selectedCalendar.get(Calendar.MONTH)
        val dayOfMonth = selectedCalendar.get(Calendar.DAY_OF_MONTH)

        val listener = DatePickerDialog.OnDateSetListener { view, y, m, d ->
            //Toast.makeText(this, "$y-$m-$d", Toast.LENGTH_SHORT).show()
            selectedCalendar.set(y, m, d)
            etScheduledDate.setText(
                resources.getString(
                    R.string.date_format,
                    y,
                    m.twoDigits(),
                    d.twoDigits()
                )
            )
            displayRadioButtons()
        }

        val datePickerDialog = DatePickerDialog(this, listener, year, month, dayOfMonth)

        val datePicker = datePickerDialog.datePicker
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        datePicker.minDate = calendar.timeInMillis
        calendar.add(Calendar.MONTH, 1)
        datePicker.maxDate = calendar.timeInMillis
        datePickerDialog.show()
    }

    private fun displayRadioButtons() {
//        rgScheduledTime.clearCheck()
//        rgScheduledTime.removeAllViews()
        rgScheduledTimeLeft.removeAllViews()
        rgScheduledTimeRight.removeAllViews()

        selectedRadioButton = null

        val hours = arrayOf("15:00", "15:30", "14:00", "14:30")
        var goToLeft = true

        hours.forEach {
            val radioButton = RadioButton(this)
            radioButton.id = View.generateViewId()
            radioButton.text = it
            radioButton.setOnClickListener {
                selectedRadioButton?.isChecked = false
                selectedRadioButton = it as RadioButton?
                selectedRadioButton?.isChecked = true
            }
            if (goToLeft)
                rgScheduledTimeLeft.addView(radioButton)
            else
                rgScheduledTimeRight.addView(radioButton)

            goToLeft = !goToLeft
        }

    }

    private fun Int.twoDigits() = if (this > 9) this.toString() else "0$this" //extension function

    override fun onBackPressed() {
        if (cvStep2.visibility == View.VISIBLE) {
            cvStep2.visibility = View.GONE
            cvStep1.visibility = View.VISIBLE
        } else if (cvStep1.visibility == View.VISIBLE) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.dialog_create_appointment_exit_title))
            builder.setMessage(getString(R.string.dialog_create_appointment_exit_message))
            builder.setPositiveButton(getString(R.string.dialog_create_appointment_exit_positive)) { _, _ ->
                finish()
            }
            builder.setNegativeButton(getString(R.string.dialog_create_appointment_exit_negative)) { dialog, _ ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
            //super.onBackPressed()
        }


    }
}
