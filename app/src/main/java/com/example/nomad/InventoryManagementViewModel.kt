// InventoryManagementViewModel.kt
package com.example.nomad

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class InventoryManagementViewModel : ViewModel() {
  private val _inventory: MutableLiveData<List<InventoryManagement>> = MutableLiveData<List<InventoryManagement>>()
  private val inventory: LiveData<List<InventoryManagement>> = _inventory

  private val _filteredAndSortedInventory = mutableStateOf<List<InventoryManagement>>(emptyList())
  val filteredAndSortedInventory: State<List<InventoryManagement>> = _filteredAndSortedInventory

  init {
    fetchInventoryData()
  }

  fun fetchInventoryData() {
    viewModelScope.launch {
      try {
        val response = ApiClient.api.getAllInventoryManagement()
        _inventory.postValue(response)
        // Initial filtering and sorting when data is loaded
        filterAndSortInventory("")
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }
  fun sortInventoryByName() {
    val sortedData = inventory.value?.sortedBy { it.itemName.orEmpty() } ?: emptyList()
    _filteredAndSortedInventory.value = sortedData
  }

  fun sortInventoryByPrice() {
    val sortedData = inventory.value?.sortedBy { it.itemPrice ?: Double.MAX_VALUE } ?: emptyList()
    _filteredAndSortedInventory.value = sortedData
  }


  fun sortInventoryByCategory() {
    val sortedData = inventory.value?.sortedBy { it.itemCategory.orEmpty() } ?: emptyList()
    _filteredAndSortedInventory.value = sortedData
  }

  private val _searchQuery = MutableLiveData<String>()
  val searchQuery: LiveData<String> = _searchQuery

  fun searchItems(query: String) {
    _searchQuery.value = query
  }

  // Method to filter and sort inventory based on search query and other criteria
  private fun filterAndSortInventory(query: String) {
    val inventoryData = inventory.value ?: emptyList()

    val filteredData = if (query.isBlank()) {
      inventoryData
    } else {
      inventoryData.filter { item ->
        item.itemName?.contains(query, ignoreCase = true) == true
      }
    }

    // Sort the filtered data (adjust the sorting criteria as needed)
    val sortedData = filteredData.sortedBy { it.itemName }

    _filteredAndSortedInventory.value = sortedData
  }
}
