package com.example.nomad

// InventoryManagementActivity.kt
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
fun InventoryManagement() {
  // Instantiate the ViewModel
  val viewModel = viewModel<InventoryManagementViewModel>()

  // Set the content of the Composable
  Surface(
    modifier = Modifier.fillMaxSize(),
  ) {
    Column(
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Fetch the inventory data from the ViewModel
      LaunchedEffect(Unit) {
        viewModel.fetchInventoryData()
      }

      // Display the inventory data using the LiveData
      val inventoryData = viewModel.inventory.value
      if (!inventoryData.isNullOrEmpty()) {
        Column(
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          inventoryData.forEach { item ->
            Text(
              text = "Item Name: ${item.itemName ?: "N/A"}",
              style = MaterialTheme.typography.h6,
              textAlign = TextAlign.Center
            )
            Text(
              text = "Item Price: ${item.itemPrice ?: 0.0}",
              style = MaterialTheme.typography.body1,
              textAlign = TextAlign.Center
            )
            Text(
              text = "Item Description: ${item.itemDescription ?: "N/A"}",
              style = MaterialTheme.typography.body1,
              textAlign = TextAlign.Center,
              modifier = Modifier.fillMaxWidth()
            )
            Text(
              text = "Item Category: ${item.itemCategory ?: "N/A"}",
              style = MaterialTheme.typography.body1,
              textAlign = TextAlign.Center,
              modifier = Modifier.fillMaxWidth()
            )
          }
        }
      }
    }
  }
}
