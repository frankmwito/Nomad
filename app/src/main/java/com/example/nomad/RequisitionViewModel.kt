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

class RequisitionViewModel : ViewModel() {
  private val _requisitions: MutableLiveData<List<Requisition>> = MutableLiveData<List<Requisition>>()
  private val requisitions: LiveData<List<Requisition>> = _requisitions

  private val _selectedRequisition: MutableLiveData<List<Requisition>> = MutableLiveData<List<Requisition>>()
  val selectedRequisition: LiveData<List<Requisition>> = _selectedRequisition


  private val _filteredAndSortedRequisitions = mutableStateOf<List<Requisition>>(emptyList())
  val filteredAndSortedRequisitions: State<List<Requisition>> = _filteredAndSortedRequisitions

  init {
    fetchRequisitionData()
  }

  fun fetchRequisitionData() {
    viewModelScope.launch {
      try {
        val response = ApiClient.api.getALLRequisition()
        _requisitions.postValue(response)
        // Initial filtering and sorting when data is loaded
        filterAndSortRequisitions("")
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }
  fun onRequisitionClicked(requisition: Requisition) {
    _selectedRequisition.value = listOf(requisition)
  }
  fun requisitionByRequesterName() {
    val sortedData = requisitions.value?.sortedBy { it.requesterName.orEmpty() } ?: emptyList()
    _filteredAndSortedRequisitions.value = sortedData
  }
  fun requisitionByRequisitionStatus() {
    val sortedData = requisitions.value?.sortedBy { it.requisitionStatus.orEmpty() } ?: emptyList()
    _filteredAndSortedRequisitions.value = sortedData
  }
  fun requisitionByPreferredVendor() {
    val sortedData = requisitions.value?.sortedBy { it.preferredVendor.orEmpty() } ?: emptyList()
    _filteredAndSortedRequisitions.value = sortedData
  }
  fun requisitionByItemDescription() {
    val sortedData = requisitions.value?.sortedBy { it.itemDescription.orEmpty()  } ?: emptyList()
    _filteredAndSortedRequisitions.value = sortedData
  }

  // Add similar sorting methods based on your requirements

  fun searchItems(query: String) {
    _searchQuery.value = query
  }

  private val _searchQuery = MutableLiveData<String>()
  val searchQuery: LiveData<String> = _searchQuery

  // Method to filter and sort requisitions based on search query and other criteria
  private fun filterAndSortRequisitions(query: String) {
    val fetchRequisitionData = requisitions.value ?: emptyList()

    val filteredData = if (query.isBlank()) {
      fetchRequisitionData
    } else {
      fetchRequisitionData.filter { requisition ->
        requisition.requesterName?.contains(query, ignoreCase = true) == true ||
                requisition.requisitionStatus?.contains(query, ignoreCase = true) == true ||
                requisition.preferredVendor?.contains(query, ignoreCase = true) == true||
                requisition.itemDescription?.contains(query, ignoreCase = true) == true
        // Add more fields as needed for searching
      }
    }

    // Sort the filtered data (adjust the sorting criteria as needed)
    val sortedData = filteredData.sortedBy { it.itemDescription }

    _filteredAndSortedRequisitions.value = sortedData
  }
}