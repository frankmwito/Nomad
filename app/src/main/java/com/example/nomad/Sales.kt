package com.example.nomad

data class Sales(
  val no_of_sales: Int,
  val sales_amount: Double,
  val paid_amount: Double,
  val bills: Double,
  val transaction_type: String,
  val item_name: String
)
