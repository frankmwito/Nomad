package com.example.nomad

// ApiInterface.kt

import retrofit2.http.GET

interface ApiInterface {
  @GET("/purchase-orders")
  suspend fun getAllPurchaseOrders(): List<PurchaseOrder>

}
