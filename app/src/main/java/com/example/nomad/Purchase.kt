package com.example.nomad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class Purchase : ComponentActivity() {
  private val api = ApiClient.api

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      TodoList(api)

    }
  }
}


@Composable
fun TodoList(api: ApiInterface) {
  var purchaseOrders by remember { mutableStateOf(emptyList<PurchaseOrder>()) }
  var loading by remember { mutableStateOf(true) }
  var isError by remember { mutableStateOf(false) }


  LaunchedEffect(key1 = Unit) {
    try {
      purchaseOrders = api.getAllPurchaseOrders()
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
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
      LazyColumn {
        items(purchaseOrders) { purchaseOrder ->
          TodoItemCard(purchaseOrder)
        }
      }
    }
  }
}

@Composable
fun TodoItemCard(purchaseOrder: PurchaseOrder) {

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp)
  ) {
    Text(text = "Distributor Name: ${purchaseOrder.distributorName}", style = MaterialTheme.typography.headlineMedium)
    Spacer(modifier = Modifier.height(4.dp))
    Text(text = "Item Name: ${purchaseOrder.itemName}", style = MaterialTheme.typography.bodySmall)
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = "Price: ${purchaseOrder.itemPrice}", style = MaterialTheme.typography.bodyMedium)
  }
}


