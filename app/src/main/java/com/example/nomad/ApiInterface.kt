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
  suspend fun getAllSales(
    @Query("start_date") startDate: String? = null,
    @Query("end_date") endDate: String? = null
  ): List<Sales>

  @GET("/users")
  suspend fun getAllUsers(
    @Query("role") role: String? = null,
    @Query("status") status: String? = null
  ): List<Users>
}
