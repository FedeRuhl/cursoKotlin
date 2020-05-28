package com.example.myappointments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogin.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
             //finaliza con main activity
        }

        tvGoToRegister.setOnClickListener{
            Toast.makeText(this,getString(R.string.please_fill_your_register_data), Toast.LENGTH_SHORT).show() //mensaje que se muestra y rapidamente desaparece

            val intent = Intent(this, RegisterActivity::class.java) //Intent es una clase java
            startActivity(intent)
        }
    }
}
