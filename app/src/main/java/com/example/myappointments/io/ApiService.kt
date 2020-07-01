package com.example.myappointments.io

import com.example.myappointments.Model.Appointment
import com.example.myappointments.Model.Doctor
import com.example.myappointments.Model.Schedule
import com.example.myappointments.Model.Specialty
import com.example.myappointments.io.response.LoginResponse
import com.example.myappointments.io.response.SimpleResponse
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ApiService {
    @GET("specialties")
    fun getSpecialties(): Call<ArrayList<Specialty>>

    @GET("specialties/{specialty}/doctors")
    fun getDoctors(@Path("specialty") specialtyId: Int): Call<ArrayList<Doctor>>

    @GET("schedule/hours")
    fun getHours(@Query("doctor_id") doctorId:Int, @Query("date") date:String): Call<Schedule>

    @POST("login")
    fun postLogin(@Query("email") email:String, @Query("password") password:String): Call<LoginResponse>

    @POST("logout")
    fun postLogout(@Header("Authorization") authHead:String): Call<Void> // no nos interesa lo que devuelva el método
    //el token no se envía como parámetro sino que como header

    @GET("appointments")
    fun getAppointments(@Header("Authorization") authHead:String): Call<ArrayList<Appointment>>

    @POST("appointments")
    @Headers("Accept: application/json")
    fun storeAppointment(
        @Header("Authorization") authHeader:String,
         @Query("description") description:String,
         @Query("specialty_id") specialtyId:Int,
         @Query("doctor_id") doctorId:Int,
         @Query("scheduled_date") scheduledDate:String,
         @Query("scheduled_time") scheduledTime:String,
         @Query("type") type:String
    ): Call<SimpleResponse>

    @POST("register")
    @Headers("Accept: application/json")
    fun postRegister(
        @Query("name") name:String,
        @Query("email") email:String,
        @Query("password") password:String,
        @Query("password_confirmation") passwordConfirmation:String
    ): Call<LoginResponse>

    @POST("fcm/token")
    @Headers("Accept: application/json")
    fun postToken(
        @Header("Authorization") authHeader:String,
        @Query("device_token") deviceToken:String
    ): Call<Void>

    companion object Factory{
        private const val BASE_URL = "http://167.99.65.203/api/"

        fun create(): ApiService{
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}