package com.example.myappointments.Model

data class Specialty(val id:Int, val name:String){
    override fun toString(): String {
        return name
    }
}