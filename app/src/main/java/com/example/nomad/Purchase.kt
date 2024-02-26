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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class Purchase : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      PurchaseOrdersScreen()

    }
  }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurchaseOrdersScreen() {
  val onItemClick: (PurchaseOrder) -> Unit = { /* Handle item click */ }
  val viewModel: PurchaseOrderViewModel = viewModel()
  val context = LocalContext.current

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    // Top Bar
    TopAppBar(
      title = { Text("Purchase Orders") },
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
        viewModel.fetchPurchaseOrdersData()
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
      } /* purchaseOrdersData */
    } else {
      val searchQuery by viewModel.searchQuery.observeAsState("")

      // Display the purchase orders data using the LiveData
      val purchaseOrdersData = viewModel.filteredAndSortedPurchaseOrders.value
      if(purchaseOrdersData!= null) {
        PurchaseOrdersList(purchaseOrdersData, searchQuery, onItemClick)
      }
    }
  }
}

@Composable
fun FilterSortMenu(viewModel: PurchaseOrderViewModel) {
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
      text = { Text("Sort by distributor name") },
      onClick = {
        expanded = false
        viewModel.sortPurchaseOrdersByDistributorName()
      }
    )
    DropdownMenuItem(
      text = { Text("Sort by order id") },
      onClick = {
        expanded = false
        viewModel.sortPurchaseOrdersByDeliveryDate()
      }
    )
    DropdownMenuItem(
      text = { Text("Sort by item name") },
      onClick = {
        expanded = false
        viewModel.sortPurchaseOrdersByItemName()
      }
    )
    DropdownMenuItem(
      text = { Text("Sort by payment type") },
      onClick = {
        expanded = false
        viewModel.sortPurchaseOrdersByPaymentType()
      }
    )
    // Add more sorting options as needed
  }
}

@Composable
fun SearchBar(viewModel: PurchaseOrderViewModel) {
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
       placeholder = { Text("Search...")},
      singleLine = true
    )
    IconButton(
      onClick = {
        viewModel.searchItems(searchQuery)
      }
    ) {
      Icon(Icons.Default.Clear, null)
    }/*purchaseOrdersData.filter { item ->
        item.itemName?.contains(query, ignoreCase = true) == true ||
                item.distributorName?.contains(query, ignoreCase = true) == true ||
                item.paymentType?.contains(query, ignoreCase = true) == true
      }     */
  }
}

@Composable
fun PurchaseOrdersList(purchaseOrdersData: List<PurchaseOrder>, searchQuery: String, onItemClick: (PurchaseOrder) -> Unit) {
  // Existing code...

  LazyColumn {
    if (purchaseOrdersData.isNotEmpty()) {
      items(purchaseOrdersData.filter { item ->
        item.itemName?.contains(searchQuery, ignoreCase = true) == true ||
                item.distributorName?.contains(searchQuery, ignoreCase = true) == true ||
                item.paymentType?.contains(searchQuery, ignoreCase = true) == true
      }) { item ->
        PurchaseOrderItemCard(item = item, onItemClick = onItemClick)
      }
    }
  }
}


@Composable
fun PurchaseOrderItemCard(item: PurchaseOrder, onItemClick: (PurchaseOrder) -> Unit) {
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
        text = "Distributor Name: ${item.distributorName}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      Text(
        text = "Contact Info: ${item.contactInfo}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      Text(
        text = "Order ID: ${item.orderId}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )/*
      Text(
        text = "Delivery Date: ${item.deliveryDate}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )*/
      Text(
        text = "Item Name: ${item.itemName}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      Text(
        text = "Item Category: ${item.itemCategory}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      Text(
        text = "Item Price: ${item.itemPrice}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      Text(
        text = "Payment Type: ${item.paymentType}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
    }
  }
}