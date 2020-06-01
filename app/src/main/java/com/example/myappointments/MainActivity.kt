package com.example.myappointments

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import com.example.myappointments.PreferenceHelper.get
import com.example.myappointments.PreferenceHelper.set
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val snackBar by lazy { //se inicializa en null pero cuando se la requiera, recien ahi se va a inicializar
        Snackbar.make(mainLayout, R.string.press_back_agaian, Snackbar.LENGTH_SHORT)
    }

    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        persistir datos: shared preferences, sqlite, files con distintos fines
//        val preferences = getSharedPreferences("general", Context.MODE_PRIVATE)
//        val session = preferences.getBoolean("session", false) //si la variable no se ha creado, devuelve falso

        if (preferences["session", false])
            goToMenuAcitivity()

        btnLogin.setOnClickListener{
            //deberia validar datos con el servidor
            createSessionPreference()
            goToMenuAcitivity()
        }

        tvGoToRegister.setOnClickListener{
            Toast.makeText(this,getString(R.string.please_fill_your_register_data), Toast.LENGTH_SHORT).show() //mensaje que se muestra y rapidamente desaparece

            val intent = Intent(this, RegisterActivity::class.java) //Intent es una clase java
            startActivity(intent)
        }
    }

    private fun goToMenuAcitivity(){
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
        //finaliza con main activity
    }

    private fun createSessionPreference(){
//        val preferences = getSharedPreferences("general", Context.MODE_PRIVATE)
//        val editor = preferences.edit()
//        editor.putBoolean("session", true)
//        editor.apply()
        preferences["session"] = true
    }

    override fun onBackPressed() {
        if (snackBar.isShown)
            super.onBackPressed()
        else
            snackBar.show()
    }
}
