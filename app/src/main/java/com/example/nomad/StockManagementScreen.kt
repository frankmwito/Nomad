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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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

class StockManagementScreen : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      StockManagementscreen()
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockManagementscreen() {
  val viewModel: StockManagementViewModel = viewModel()

  Surface(
    modifier = Modifier.fillMaxSize(),
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
      // Top Bar
      TopAppBar(
        title = { Text("Stock Management List") },
        modifier = Modifier
          .fillMaxWidth()
          .background(MaterialTheme.colorScheme.primary),
        actions = {
          Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
          ) {
            FilterSortMenuStockManagement(viewModel)
            SearchBarStockManagement(viewModel)
          }
        }
      )

      var loading by remember { mutableStateOf(true) }
      var isError by remember { mutableStateOf(false) }

      LaunchedEffect(key1 = Unit) {
        try {
          viewModel.fetchStockManagementData()
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

        // Display the stock management data using the LiveData
        val stockManagementData = viewModel.filteredAndSortedStockManagement.value
        if (stockManagementData != null) {
          StockManagementList(stockManagementData, onItemClick = {
            // Handle item click
          }, searchQuery = searchQuery)
        }
      }
    }
  }
}

@Composable
fun StockManagementList(stockManagementData: List<StockManagement>, onItemClick: (StockManagement) -> Unit, searchQuery: String) {
  LazyColumn {
    items(stockManagementData.filter { item ->
      item.itemName?.contains(searchQuery, ignoreCase = true) == true ||
              item.category?.contains(searchQuery, ignoreCase = true) == true ||
              item.supplierName?.contains(searchQuery, ignoreCase = true) == true
    }) { item ->
      // Each item in the stock management list
      StockManagementItemCard(item = item, onItemClick = onItemClick)
    }
  }
}

@Composable
fun StockManagementItemCard(item: StockManagement, onItemClick: (StockManagement) -> Unit) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp)
      .clickable { onItemClick(item) }
  ){
    Text(
      text = "Item ID: ${item.id}",
      style = MaterialTheme.typography.headlineMedium,
      textAlign = TextAlign.Start
    )
    Text(
      text = "Item Name: ${item.itemName ?: "N/A"}",
      style = MaterialTheme.typography.bodyMedium,
      textAlign = TextAlign.Start
    )
    Text(
      text = "Category: ${item.category ?: "N/A"}",
      style = MaterialTheme.typography.bodyMedium,
      textAlign = TextAlign.Start
    )
    Text(
      text = "Supplier Name: ${item.supplierName ?: "N/A"}",
      style = MaterialTheme.typography.bodyMedium,
      textAlign = TextAlign.Start
    )
    Text(
      text = "Item Price: ${item.itemPrice ?: 0.0}",
      style = MaterialTheme.typography.bodyMedium,
      textAlign = TextAlign.Start
    )
    Text(
      text = "Current Stock: ${item.currentStock}",
      style = MaterialTheme.typography.headlineMedium,
      textAlign = TextAlign.Start
    )
    Text(
      text = "Min Stock Level: ${item.minStockLevel}",
      style = MaterialTheme.typography.headlineMedium,
      textAlign = TextAlign.Start
    )
    Text(
      text = "Max Stock Level: ${item.maxStockLevel}",
      style = MaterialTheme.typography.headlineMedium,
      textAlign = TextAlign.Start
    )
  }
}

@Composable
fun FilterSortMenuStockManagement(viewModel: StockManagementViewModel) {
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
      text = { Text("Sort by Item Name") },
      onClick = {
        expanded = false
        // Handle sort by item name action
        viewModel.sortstockManagementByItemName()
      }
    )
    DropdownMenuItem(
      text = { Text("Sort by Item Price") },
      onClick = {
        expanded = false
        // Handle sort by item price action
        viewModel.sortstockManagementByItemPrice()
      }
    )
    DropdownMenuItem(
      text = { Text("Sort by Category") },
      onClick = {
        expanded = false
        // Handle sort by category action
        viewModel.stockManagementByCategory()
      }
    )
    DropdownMenuItem(
      text = { Text("Sort by Supplier Name") },
      onClick = {
        expanded = false
        // Handle sort by supplier name action
        viewModel.stockManagementBySupplierName()
      }
    )
  }
}

@Composable
fun SearchBarStockManagement(viewModel: StockManagementViewModel) {
  var searchQuery by remember { mutableStateOf("") }

  OutlinedTextField(
    value = searchQuery,
    onValueChange = {
      searchQuery = it
      viewModel.searchItems(it)
    },
    label = { Text("Search") },
    modifier = Modifier
      .fillMaxWidth()
      .padding(start = 16.dp),
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
