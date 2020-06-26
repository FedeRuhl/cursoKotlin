package com.example.myappointments.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myappointments.util.PreferenceHelper
import kotlinx.android.synthetic.main.activity_main.*
import com.example.myappointments.util.PreferenceHelper.get
import com.example.myappointments.util.PreferenceHelper.set
import com.example.myappointments.R
import com.example.myappointments.io.ApiService
import com.example.myappointments.io.response.LoginResponse
import com.example.myappointments.util.toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.iid.FirebaseInstanceId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    private val snackBar by lazy { //se inicializa en null pero cuando se la requiera, recien ahi se va a inicializar
        Snackbar.make(
            mainLayout,
            R.string.press_back_agaian, Snackbar.LENGTH_SHORT
        )
    }

    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(this){ instanceIdResult ->
            val deviceToken = instanceIdResult.token
            Log.d("MyFirebaseMsgService", deviceToken)
        }

//        persistir datos: shared preferences, sqlite, files con distintos fines
//        val preferences = getSharedPreferences("general", Context.MODE_PRIVATE)
//        val session = preferences.getBoolean("session", false) //si la variable no se ha creado, devuelve falso


        if (preferences["jwt", ""].contains(".")) //el token tiene 3 .
            goToMenuActivity()

        btnLogin.setOnClickListener {
            performLogin()
        }

        tvGoToRegister.setOnClickListener {
            Toast.makeText(
                this,
                getString(R.string.please_fill_your_register_data),
                Toast.LENGTH_SHORT
            ).show() //mensaje que se muestra y rapidamente desaparece

            val intent = Intent(this, RegisterActivity::class.java) //Intent es una clase java
            startActivity(intent)
        }
    }

    private fun performLogin() {
        if (etEmail.text.isNotEmpty() && etPassword.text.isNotEmpty()) {
            val call = apiService.postLogin(etEmail.text.toString(), etPassword.text.toString())
            call.enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    toast(getString(R.string.login_failure))
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        loginResponse?.let {
                            createSessionPreference(loginResponse.access_token)
                            toast(getString(R.string.welcome_name, loginResponse.user.name))
                            goToMenuActivity()
                        } ?: run { //401 Unauthorized
                            toast(getString(R.string.error_login_response))
                            return
                        }
                    } else {
                        toast(getString(R.string.error_invalid_credentials))
                    }
                }
            })
        } else {
            toast(getString(R.string.email_or_password_empty))
        }
    }

    private fun goToMenuActivity() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
        //finaliza con main activity
    }

    private fun createSessionPreference(jwt: String) {
//        val preferences = getSharedPreferences("general", Context.MODE_PRIVATE)
//        val editor = preferences.edit()
//        editor.putBoolean("session", true)
//        editor.apply()
        preferences["jwt"] = jwt
    }

    override fun onBackPressed() {
        if (snackBar.isShown)
            super.onBackPressed()
        else
            snackBar.show()
    }
}
