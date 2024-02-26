package com.example.nomad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nomad.ui.theme.NomadTheme
import kotlinx.coroutines.launch

class StockManagementViewModel : ViewModel() {
  private val _stockManagement: MutableLiveData<List<StockManagement>> = MutableLiveData<List<StockManagement>>()
  private val stockManagement: LiveData<List<StockManagement>> = _stockManagement

  private val _filteredAndSortedStockManagement = mutableStateOf<List<StockManagement>>(emptyList())
  val filteredAndSortedStockManagement: State<List<StockManagement>> = _filteredAndSortedStockManagement

  init {
    fetchStockManagementData()
  }

  fun fetchStockManagementData() {
    viewModelScope.launch {
      try {
        val response = ApiClient.api.getALLStockManagement()
        _stockManagement.postValue(response)
        // Initial filtering and sorting when data is loaded
        filterAndSortstockManagement("")
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  // ... sorting and filtering methods similar to InventoryManagementViewModel
  fun stockManagementBySupplierName() {
    val sortedData = stockManagement.value?.sortedBy { it.supplierName.orEmpty() } ?: emptyList()
    _filteredAndSortedStockManagement.value = sortedData
  }

  fun stockManagementByCategory() {
    val sortedData = stockManagement.value?.sortedBy { it.category } ?: emptyList()
    _filteredAndSortedStockManagement.value = sortedData
  }

  fun sortstockManagementByItemName() {
    val sortedData = stockManagement.value?.sortedBy { it.itemName.orEmpty() } ?: emptyList()
    _filteredAndSortedStockManagement.value = sortedData
  }

  fun sortstockManagementByItemPrice() {
    val sortedData = stockManagement.value?.sortedBy { it.itemPrice } ?: emptyList()
    _filteredAndSortedStockManagement.value = sortedData
  }

  private val _searchQuery = MutableLiveData<String>()
  val searchQuery: LiveData<String> = _searchQuery

  fun searchItems(query: String) {
    _searchQuery.value = query
  }

  // Method to filter and sort purchase orders based on search query and other criteria
  private fun filterAndSortstockManagement(query: String) {
    val fetchStockManagementData = stockManagement.value ?: emptyList()

    val filteredData = if (query.isBlank()) {
      fetchStockManagementData
    } else {
      fetchStockManagementData.filter { item ->
        item.itemName?.contains(query, ignoreCase = true) == true ||
                item.category?.contains(query, ignoreCase = true) == true ||
                item.supplierName?.contains(query, ignoreCase = true) == true
      }
    }

    // Sort the filtered data (adjust the sorting criteria as needed)
    val sortedData = filteredData.sortedBy { it.itemName }

    _filteredAndSortedStockManagement.value = sortedData
  }
}