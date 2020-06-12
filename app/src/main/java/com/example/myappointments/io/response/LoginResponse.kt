package com.example.myappointments.io.response

import com.example.myappointments.Model.User

data class LoginResponse(val user: User, val access_token:String, val token_type:String, val expires_at:String)