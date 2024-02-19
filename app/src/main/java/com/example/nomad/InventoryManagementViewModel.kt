// InventoryManagementViewModel.kt
package com.example.nomad

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class InventoryManagementViewModel : ViewModel() {
  private val _inventory = MutableLiveData<List<InventoryManagement>>()
  val inventory: LiveData<List<InventoryManagement>> = _inventory

  init {
    fetchInventoryData()
  }

  private fun fetchInventoryData() {
    viewModelScope.launch {
      try {
        val response = ApiClient.api.getAllInventoryManagement()
        _inventory.postValue(response)
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }
}