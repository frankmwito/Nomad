package com.example.nomad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nomad.ui.theme.NomadTheme
import kotlinx.coroutines.launch

// SalesViewModel.kt

class SalesViewModel : ViewModel() {
  private val _sales: MutableLiveData<List<Sales>> = MutableLiveData()
  val sales: LiveData<List<Sales>> = _sales

  private val _filteredAndSortedSales = MutableLiveData<List<Sales>>()
  val filteredAndSortedSales: LiveData<List<Sales>> = _filteredAndSortedSales

  private val _searchQuery = MutableLiveData<String>()
  val searchQuery: LiveData<String> = _searchQuery

  init {
    fetchSalesData()
  }

  fun fetchSalesData() {
    viewModelScope.launch {
      try {
        val response = ApiClient.api.getAllSales()
        _sales.postValue(response)
        filteredAndSortedSales("")
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  fun sortSalesByNumberOfSales() {
    val sortedData = sales.value?.sortedBy { it.noofSales } ?: emptyList()
    _filteredAndSortedSales.value = sortedData
  }

  fun sortSalesBySalesAmount() {
    val sortedData = sales.value?.sortedBy { it.salesAmount } ?: emptyList()
    _filteredAndSortedSales.value = sortedData
  }

  fun sortSalesByPaidAmount() {
    val sortedData = sales.value?.sortedBy { it.paidAmount } ?: emptyList()
    _filteredAndSortedSales.value = sortedData
  }

  fun sortSalesByBills() {
    val sortedData = sales.value?.sortedBy { it.bills } ?: emptyList()
    _filteredAndSortedSales.value = sortedData
  }

  fun sortSalesByTransactionType() {
    val sortedData = sales.value?.sortedBy { it.transactionType.orEmpty() } ?: emptyList()
    _filteredAndSortedSales.value = sortedData
  }

  fun sortSalesByItemName() {
    val sortedData = sales.value?.sortedBy { it.itemName.orEmpty() } ?: emptyList()
    _filteredAndSortedSales.value = sortedData
  }

  fun searchItems(query: String) {
    _searchQuery.value = query
  }

  // Method to filter and sort sales based on search query and other criteria
  private fun filteredAndSortedSales(query: String) {
    val salesData = sales.value ?: emptyList()

    val filteredData = if (query.isBlank()) {
      salesData
    } else {
      salesData.filter { item ->
        item.itemName?.contains(query, ignoreCase = true) == true ||
                item.transactionType?.contains(query, ignoreCase = true) == true
      }
    }

    // Sort the filtered data (adjust the sorting criteria as needed)
    val sortedData = filteredData.sortedBy { it.itemName }

    _filteredAndSortedSales.value = sortedData
  }
}