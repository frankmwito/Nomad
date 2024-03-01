package com.example.nomad

// InventoryManagementActivity.kt
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.compose.runtime.collectAsState
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TabRowDefaults.contentColor
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class InventoryManagementScreen : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      InventoryManagement()
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryManagement() {
  // Instantiate the ViewModel
  val viewModel: InventoryManagementViewModel = viewModel()
  val context = LocalContext.current

  // Set the content of the Composable
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
        title = { Text("Inventory List") },
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

      LaunchedEffect(key1 = Unit) {
        try {
          viewModel.fetchInventoryData()
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

        // Display the inventory data using the LiveData
        val inventoryData = viewModel.filteredAndSortedInventory.value
        if (inventoryData != null) {
          InventoryList(inventoryData, onItemClick = {
            // Handle item click
          }, searchQuery = searchQuery)
        }
      }
    }
  }
}
@Composable
fun InventoryList(inventoryData: List<InventoryManagement>, onItemClick: (InventoryManagement) -> Unit, searchQuery: String) {
  LazyColumn {
    items(inventoryData.filter { item ->
      item.itemName?.contains(searchQuery, ignoreCase = true) == true ||
              item.itemDescription?.contains(searchQuery, ignoreCase = true) == true ||
              item.itemCategory?.contains(searchQuery, ignoreCase = true) == true
    }) { item ->
      // Each item in the inventory list
      InventoryItemCard(item = item, onItemClick = onItemClick)
    }
  }
}

@Composable
fun InventoryItemCard(item: InventoryManagement, onItemClick: (InventoryManagement) -> Unit) {
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
        text = "Item Name: ${item.itemName ?: "N/A"}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      Text(
        text = "Price: ${item.itemPrice?.toFloat() ?: 0.0f}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      Text(
        text = "Description: ${item.itemDescription ?: "N/A"}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      Text(
        text = "Category: ${item.itemCategory ?: "N/A"}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
    }
  }
}

@Composable
fun FilterSortMenu(viewModel: InventoryManagementViewModel) {
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
      text = { Text("Filter by name") },
      onClick = {
        expanded = false
        // Handle filter by name action
        viewModel.sortInventoryByName()
      }
    )
    DropdownMenuItem(
      text = { Text("Sort by price") },
      onClick = {
        expanded = false
        // Handle sort by price action
        viewModel.sortInventoryByPrice()
      }
    )
    DropdownMenuItem(
      text = { Text("Sort by category") },
      onClick = {
        expanded = false
        // Handle sort by category action
        viewModel.sortInventoryByCategory()
      }
    )
  }
}

@Composable
fun SearchBar(viewModel: InventoryManagementViewModel) {
  val context = LocalContext.current
  var searchQuery by remember { mutableStateOf("") }

  TextField(
    value = searchQuery,
    onValueChange = { newValue ->
      searchQuery = newValue
      viewModel.searchItems(searchQuery)
    },
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 8.dp, vertical = 4.dp),
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
