package com.example.nomad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nomad.ui.theme.NomadTheme
class RequisitionScreen : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      Requisitionscreen()
    }
  }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Requisitionscreen() {
  val onItemClick: (Requisition) -> Unit = { /* Handle item click */ }
  val viewModel: RequisitionViewModel = viewModel()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16. dp)
  ) {
    // Top Bar
    TopAppBar(
      title = { Text("Requisitions") },
      modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.primary),
      actions = {
        Row(
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier.fillMaxWidth()
        ) {
          FilterSortMenu(viewModel)
          SearchBar(viewModel)
        }
      }
    )

    var loading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel) {
      try {
        viewModel.fetchRequisitionData()
        loading = false
      } catch (e: Exception) {
        isError = true
        loading = false
      }
    }
    if (loading) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      contentAlignment = Alignment.Center
    ) {
      CircularProgressIndicator()
    }
  } else if (isError) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      contentAlignment = Alignment.Center
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(16.dp)
      ) {
        Icon(imageVector = Icons.Default.Info, contentDescription = "Info")
        Spacer(modifier = Modifier.height(8.dp))
        Text("An error occurred while loading data.")
      }
    }
  } else  {
      val searchQuery by viewModel.searchQuery.observeAsState("")

      // Display the requisitions data using the LiveData
      val requisitionsData = viewModel.filteredAndSortedRequisitions.value
      requisitionsData?.let {
        RequisitionList(it, searchQuery, onItemClick)
      }
    }
  }
}

@Composable
fun RequisitionList(requisitionsData: List<Requisition>, searchQuery: String, onItemClick: (Requisition) -> Unit) {
  LazyColumn {
      items(requisitionsData.filter { item ->
        item.requesterName?.contains(searchQuery, ignoreCase = true) == true ||
                item.requisitionStatus?.contains(searchQuery, ignoreCase = true) == true ||
                item.preferredVendor?.contains(searchQuery, ignoreCase = true) == true ||
                item.itemDescription?.contains(searchQuery, ignoreCase = true) == true
      }) { item ->
        RequisitionItemCard(item = item, onItemClick = onItemClick)
      }
  }
}

@Composable
fun RequisitionItemCard(item: Requisition, onItemClick: (Requisition) -> Unit) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp)
      .clickable { onItemClick(item) }
  ) {
    Column(
      modifier = Modifier.padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Text(
        text = "Requester Name: ${item.requesterName}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )/*
      Text(
        text = "Requisition Id: ${item.requisitionId}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )*/
      Text(
        text = "Requisition Status: ${item.requisitionStatus}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      Text(
        text = "Preferred Vendor: ${item.preferredVendor}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      Text(
        text = "Item Description: ${item.itemDescription}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      Text(
        text = "Requester Contact: ${item.requesterContact}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      Text(
        text = "Quantity: ${item.quantity}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      Text(
        text = "Unit of Measure: ${item.unitOfMeasure}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      Text(
        text = "CostCenter: ${item.costCenter}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      Text(
        text = "Budget Allocation: ${item.budgetAllocation}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      Text(
        text = "Delivery Address: ${item.deliveryAddress}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      Text(
        text = "Justification: ${item.justification}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      Text(
        text = "PurchaseOrder Id: ${item.purchaseOrderId}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      // Add more fields as needed
    }
  }
}

@Composable
fun RequisitionDetailScreen(requisition: Requisition) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    Text(
      text = "Requester Name: ${requisition.requesterName}",
      style = MaterialTheme.typography.headlineMedium,
      textAlign = TextAlign.Start
    )
    Text(
      text = "Requisition Status: ${requisition.requisitionStatus}",
      style = MaterialTheme.typography.headlineMedium,
      textAlign = TextAlign.Start
    )
    Text(
      text = "Preferred Vendor: ${requisition.preferredVendor}",
      style = MaterialTheme.typography.headlineMedium,
      textAlign = TextAlign.Start
    )
    Text(
      text = "Item Description: ${requisition.itemDescription}",
      style = MaterialTheme.typography.headlineMedium,
      textAlign = TextAlign.Start
    )
    // Add more fields as needed
  }
}

@Composable
fun FilterSortMenu(viewModel: RequisitionViewModel) {
  var expanded by remember { mutableStateOf(false) }

  IconButton(onClick = { expanded = true }) {
    Icon(Icons.Default.FilterList, "Filter and sort")
  }

  DropdownMenu(
    expanded = expanded,
    onDismissRequest = { expanded = false },
    modifier = Modifier.background(MaterialTheme.colorScheme.surface)
  ) {
    DropdownMenuItem(
      text = { Text("Sort by requester name") },
      onClick = {
        expanded = false
        viewModel.requisitionByRequesterName()
      }
    )
    DropdownMenuItem(
      text = { Text("Sort by requisition status") },
      onClick = {
        expanded = false
        viewModel.requisitionByRequisitionStatus()
      }
    )
    DropdownMenuItem(
      text = { Text("Sort by preferred vendor") },
      onClick = {
        expanded = false
        viewModel.requisitionByPreferredVendor()
      }
    )
    DropdownMenuItem(
      text = { Text("Sort by item description") },
      onClick = {
        expanded = false
        viewModel.requisitionByItemDescription()
      }
    )
    // Add more sorting options as needed
  }
}

@Composable
fun SearchBar(viewModel: RequisitionViewModel) {
  var searchQuery by remember { mutableStateOf("") }

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    TextField(
      value = searchQuery,
      onValueChange = { newValue ->
        searchQuery = newValue
        viewModel.searchItems(searchQuery)
      },
      modifier = Modifier
        .weight(1f)
        .padding(end = 8.dp),
      label = { Text("Search") },
      leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
      placeholder = { Text("Search...") },
      singleLine = true
    )
    IconButton(
      onClick = {
        viewModel.searchItems(searchQuery)
      }
    ) {
      Icon(Icons.Default.Clear, null)
    }
  }
  val requisitionViewModel: RequisitionViewModel = viewModel<RequisitionViewModel>()
  val requisition = requisitionViewModel.selectedRequisition.value?.get(0)
  if (requisition != null) {
    RequisitionDetailScreen(requisition)
  }
}

// Add the following code in the appropriate place in the MainActivity.kt or your equivalent

