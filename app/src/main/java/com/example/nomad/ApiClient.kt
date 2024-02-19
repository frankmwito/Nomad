package com.example.nomad

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
  private const val BASE_URL = "https://b3e7-41-90-189-14.ngrok-free.app"
  private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

  val api: ApiInterface = retrofit.create(ApiInterface::class.java)
}