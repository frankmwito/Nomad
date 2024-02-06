package com.example.nomad

// ApiClient.kt

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
  private const val BASE_URL = "http://127.0.0.1:8080"
  private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

  val api: ApiInterface = retrofit.create(ApiInterface::class.java)
}
