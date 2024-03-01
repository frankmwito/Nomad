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


class SalesScreen : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      Salesscreen()
    }
  }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Salesscreen() {
  val onItemClick: (Sales) -> Unit = { /* Handle item click */ }
  val viewModel: SalesViewModel = viewModel()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    // Top Bar
    TopAppBar(
      title = { Text("Sales") },
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
        viewModel.fetchSalesData()
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

      // Display the sales data using the LiveData
      val salesData = viewModel.filteredAndSortedSales.value
      salesData?.let {
        SalesList(it, searchQuery, onItemClick)
      }
    }
  }
}

@Composable
fun SalesList(salesData: List<Sales>, searchQuery: String, onItemClick: (Sales) -> Unit) {
  LazyColumn {
      items(salesData.filter { item ->
        item.itemName?.contains(searchQuery, ignoreCase = true) == true ||
                item.transactionType?.contains(searchQuery, ignoreCase = true) == true
      }) { item ->
        SalesItemCard(item = item, onItemClick = onItemClick)
      }
  }
}

@Composable
fun SalesItemCard(item: Sales, onItemClick: (Sales) -> Unit) {
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
        text = "Number of Sales: ${item.noofSales}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      Text(
        text = "Sales Amount: ${item.salesAmount}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      Text(
        text = "Paid Amount: ${item.paidAmount}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      Text(
        text = "Bills: ${item.bills}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      Text(
        text = "Transaction Type: ${item.transactionType}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start
      )
      // Add more fields as needed
    }
  }
}

@Composable
fun FilterSortMenu(viewModel: SalesViewModel) {
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
      text = { Text("Sort by Number of Sales") },
      onClick = {
        expanded = false
        viewModel.sortSalesByNumberOfSales()
      }
    )
    DropdownMenuItem(
      text = { Text("Sort by Sales Amount") },
      onClick = {
        expanded = false
        viewModel.sortSalesBySalesAmount()
      }
    )
    DropdownMenuItem(
      text = { Text("Sort by Paid Amount") },
      onClick = {
        expanded = false
        viewModel.sortSalesByPaidAmount()
      }
    )
    DropdownMenuItem(
      text = { Text("Sort by Bills") },
      onClick = {
        expanded = false
        viewModel.sortSalesByBills()
      }
    )
    DropdownMenuItem(
      text = { Text("Sort by Transaction Type") },
      onClick = {
        expanded = false
        viewModel.sortSalesByTransactionType()
      }
    )
    DropdownMenuItem(
      text = { Text("Sort by Item Name") },
      onClick = {
        expanded = false
        viewModel.sortSalesByItemName()
      }
    )
    // Add more sorting options as needed
  }
}

@Composable
fun SearchBar(viewModel: SalesViewModel) {
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