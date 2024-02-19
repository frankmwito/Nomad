// ApiInterface.kt
package com.example.nomad

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
  @GET("/inventory-management")
  suspend fun getAllInventoryManagement(): List<InventoryManagement>

  @GET("/purchase-order")
  suspend fun getAllPurchaseOrders(): List<PurchaseOrder>

  @GET("/sales")
  suspend fun getAllSales(): List<Sales>

  @GET("/users")
  suspend fun getAllUsers(): List<Users>
}
