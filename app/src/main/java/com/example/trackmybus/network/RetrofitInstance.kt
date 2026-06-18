package com.example.trackmybus.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "http://10.34.192.83:8082/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)

            // Plain text handle karega
            .addConverterFactory(ScalarsConverterFactory.create())

            // JSON handle karega
            .addConverterFactory(GsonConverterFactory.create())

            .build()
            .create(ApiService::class.java)
    }
}