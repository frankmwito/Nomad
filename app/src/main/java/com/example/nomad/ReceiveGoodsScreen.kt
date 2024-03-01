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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nomad.ui.theme.NomadTheme

class ReceiveGoodsScreen : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      ReceiveGoodsscreen()
    }
  }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiveGoodsscreen() {
  val onItemClick: (ReceiveGoods) -> Unit = { /* Handle item click */ }
  val viewModel: ReceiveGoodsViewModel = viewModel()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    // Top Bar
    TopAppBar(
      title = { Text("Receive Goods") },
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
        viewModel.fetchReceiveGoodsData()
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
    } else {
      val searchQuery by viewModel.searchQuery.observeAsState("")

      // Display the receive goods data using the LiveData
      val receiveGoodsData = viewModel.filteredAndSortedReceiveGoods.value
      receiveGoodsData?.let {
        ReceiveGoodsList(it, searchQuery, onItemClick)
      }
    }
  }
}
@Composable
fun ReceiveGoodsList(receiveGoodsData: List<ReceiveGoods>, searchQuery: String, onItemClick: (ReceiveGoods) -> Unit) {
  LazyColumn {
    items(receiveGoodsData.filter { item ->
      item.itemName?.contains(searchQuery, ignoreCase = true) == true ||
              item.supplierName?.contains(searchQuery, ignoreCase = true) == true ||
              item.invoiceNumber?.contains(searchQuery, ignoreCase = true) == true
    }) { item ->
      ReceiveGoodsItemCard(item = item, onItemClick = onItemClick)
    }
  }
}
@Composable
fun ReceiveGoodsItemCard(item: ReceiveGoods, onItemClick: (ReceiveGoods) -> Unit) {
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
        text = "Item Name: ${item.itemName}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      Text(
        text = "Received Quantity: ${item.receivedQuantity}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      Text(
        text = "Supplier Name: ${item.supplierName}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      Text(
        text = "Invoice Number: ${item.invoiceNumber}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      // Add more fields as needed
    }
  }
}

@Composable
fun FilterSortMenu(viewModel: ReceiveGoodsViewModel) {
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
      text = { Text("Sort by Supplier Name") },
      onClick = {
        expanded = false
        viewModel.sortReceiveGoodsBySupplierName()
      }
    )
    DropdownMenuItem(
      text = { Text("Sort by Invoice Number") },
      onClick = {
        expanded = false
        viewModel.sortReceiveGoodsByInvoiceNumber()
      }
    )
    DropdownMenuItem(
      text = { Text("Sort by Item Name") },
      onClick = {
        expanded = false
        viewModel.sortReceiveGoodsByItemName()
      }
    )
    // Add more sorting options as needed
  }
}

@Composable
fun SearchBar(viewModel: ReceiveGoodsViewModel) {
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
        searchQuery = ""
        viewModel.searchItems(searchQuery)
      }
    ) {
      Icon(Icons.Default.Clear, null)
    }
  }
}