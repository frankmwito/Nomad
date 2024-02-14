// InventoryManagementViewModel.kt
package com.example.nomad

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class InventoryManagementViewModel : ViewModel() {

  // MutableLiveData for holding inventory data
  val inventory: MutableLiveData<List<InventoryManagement>> = MutableLiveData()

  // Fetch inventory data from the server
  fun fetchInventoryData() {
    viewModelScope.launch {
      try {
        // Make the API request using Retrofit
        val response = ApiClient.api.getAllInventoryManagement()

        // Update the MutableLiveData with the response data
        inventory.value = response
      } catch (e: Exception) {
        // Handle error if the request fails
        e.printStackTrace()
      }
    }
  }
}