package com.example.nomad

// PurchaseOrderViewModel.kt
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PurchaseOrderViewModel : ViewModel() {
  private val _purchaseOrders:MutableLiveData<List<PurchaseOrder>> =MutableLiveData<List<PurchaseOrder>>()
  private val purchaseOrders: LiveData<List<PurchaseOrder>> = _purchaseOrders

  private val _filteredAndSortedPurchaseOrders = mutableStateOf<List<PurchaseOrder>>(emptyList())
  val filteredAndSortedPurchaseOrders: State<List<PurchaseOrder>> = _filteredAndSortedPurchaseOrders

  init {
    fetchPurchaseOrdersData()
  }

  fun fetchPurchaseOrdersData() {
    viewModelScope.launch {
      try {
        val response = ApiClient.api.getAllPurchaseOrders()
        _purchaseOrders.postValue(response)
        // Initial filtering and sorting when data is loaded
        filterAndSortPurchaseOrders("")
      } catch (e: Exception) {
        // Handle error
        e.printStackTrace()
      }
    }

  }

  fun sortPurchaseOrdersByDistributorName() {
    val sortedData = purchaseOrders.value?.sortedBy { it.distributorName.orEmpty() } ?: emptyList()
    _filteredAndSortedPurchaseOrders.value = sortedData
  }

  fun sortPurchaseOrdersByDeliveryDate() {
    val sortedData = purchaseOrders.value?.sortedBy { it.orderId } ?: emptyList()
    _filteredAndSortedPurchaseOrders.value = sortedData
  }

  fun sortPurchaseOrdersByItemName() {
    val sortedData = purchaseOrders.value?.sortedBy { it.itemName.orEmpty() } ?: emptyList()
    _filteredAndSortedPurchaseOrders.value = sortedData
  }

  fun sortPurchaseOrdersByPaymentType() {
    val sortedData = purchaseOrders.value?.sortedBy { it.paymentType.orEmpty() } ?: emptyList()
    _filteredAndSortedPurchaseOrders.value = sortedData
  }

  private val _searchQuery = MutableLiveData<String>()
  val searchQuery: LiveData<String> = _searchQuery

  fun searchItems(query: String) {
    _searchQuery.value = query
  }

  // Method to filter and sort purchase orders based on search query and other criteria
  private fun filterAndSortPurchaseOrders(query: String) {
    val purchaseOrdersData = purchaseOrders.value ?: emptyList()

    val filteredData = if (query.isBlank()) {
      purchaseOrdersData
    } else {
      purchaseOrdersData.filter { item ->
        item.itemName?.contains(query, ignoreCase = true) == true ||
                item.distributorName?.contains(query, ignoreCase = true) == true ||
                item.paymentType?.contains(query, ignoreCase = true) == true
      }
    }

    // Sort the filtered data (adjust the sorting criteria as needed)
    val sortedData = filteredData.sortedBy { it.itemName }

    _filteredAndSortedPurchaseOrders.value = sortedData
  }
}
