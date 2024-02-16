package com.example.nomad

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
  private const val BASE_URL = "https://9a6e-41-90-179-182.ngrok-free.app"
  private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

  val api: ApiInterface = retrofit.create(ApiInterface::class.java)
}