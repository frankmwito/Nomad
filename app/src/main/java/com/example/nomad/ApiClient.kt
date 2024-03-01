package com.example.nomad

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
  private const val BASE_URL = " https://282a-41-90-185-238.ngrok-free.app "
  private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

  val api: ApiInterface = retrofit.create(ApiInterface::class.java)
}