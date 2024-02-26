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

  @GET("/stock_management")
  suspend fun getALLStockManagement():List<StockManagement>

  @GET("/requisition")
  suspend fun getALLRequisition():List<Requisition>

  @GET("/receivegoods")
  suspend fun getALLReceiveGoods():List<ReceiveGoods>

  @GET("/users")
  suspend fun getAllUsers(): List<Users>
}
