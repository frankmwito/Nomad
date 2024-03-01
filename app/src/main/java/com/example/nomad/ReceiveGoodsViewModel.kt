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

// ReceiveGoodsViewModel.kt
class ReceiveGoodsViewModel : ViewModel() {
  private val _receiveGoods: MutableLiveData<List<ReceiveGoods>> = MutableLiveData<List<ReceiveGoods>>()
  private val receiveGoods: LiveData<List<ReceiveGoods>> = _receiveGoods

  private val _filteredAndSortedReceiveGoods = mutableStateOf<List<ReceiveGoods>>(emptyList())
  val filteredAndSortedReceiveGoods: State<List<ReceiveGoods>> = _filteredAndSortedReceiveGoods

  private val _searchQuery = MutableLiveData<String>()
  val searchQuery: LiveData<String> = _searchQuery

  init {
    fetchReceiveGoodsData()
  }

  fun fetchReceiveGoodsData() {
    viewModelScope.launch {
      try {
        val response = ApiClient.api.getALLReceiveGoods()
        _receiveGoods.postValue(response)
        filteredAndSortedReceiveGoods("")
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  fun sortReceiveGoodsBySupplierName() {
    val sortedData = receiveGoods.value?.sortedBy { it.supplierName.orEmpty() } ?: emptyList()
    _filteredAndSortedReceiveGoods.value = sortedData
  }

  fun sortReceiveGoodsByInvoiceNumber() {
    val sortedData = receiveGoods.value?.sortedBy { it.invoiceNumber } ?: emptyList()
    _filteredAndSortedReceiveGoods.value = sortedData
  }

  fun sortReceiveGoodsByItemName() {
    val sortedData = receiveGoods.value?.sortedBy { it.itemName.orEmpty() } ?: emptyList()
    _filteredAndSortedReceiveGoods.value = sortedData
  }


  fun searchItems(query: String) {
    _searchQuery.value = query
  }

  // Method to filter and sort receive goods based on search query and other criteria
  private fun filteredAndSortedReceiveGoods(query: String) {
    val receiveGoodsData = receiveGoods.value ?: emptyList()

    val filteredData = if (query.isBlank()) {
      receiveGoodsData
    } else {
      receiveGoodsData.filter { item ->
        item.itemName?.contains(query, ignoreCase = true) == true ||
                item.supplierName?.contains(query, ignoreCase = true) == true ||
                item.invoiceNumber?.contains(query, ignoreCase = true) == true
      }
    }

    // Sort the filtered data (adjust the sorting criteria as needed)
    val sortedData = filteredData.sortedBy { it.itemName }

    _filteredAndSortedReceiveGoods.value = sortedData
  }
}