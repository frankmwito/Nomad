package com.example.nomad
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Atm
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class InventoryItem(
  val title: String,
  val icon: ImageVector,
  val onClick: () -> Unit
)

@Composable
fun InventoryManagementFragment() {
  Column(
    modifier = Modifier.fillMaxSize()
      .background(Color.White),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    horizontalAlignment = Alignment.Start
  ) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .padding(16.dp),
      elevation = 8.dp,
      backgroundColor = Color.White
    ) {
      Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        Icon(
          imageVector = Icons.Filled.Inventory,
          contentDescription = "Inventory Management",
          modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
          text = "Inventory Management",
          modifier = Modifier.weight(1f),
          style = androidx.compose.material.MaterialTheme.typography.h6
        )
      }
    }
    Spacer(modifier = Modifier.height(16.dp))
    LazyVerticalGrid(
      modifier = Modifier.fillMaxWidth(),
      columns = GridCells.Fixed(2),
      contentPadding = PaddingValues(8.dp)
    ) {
      items(inventoryItems) { item ->
        InventoryButton(item)
      }
    }
  }
}
private val inventoryItems = listOf(
  InventoryItem(
    title = "Sales",
    icon = Icons.Filled.AutoGraph,
    onClick = { /* Handle Sales Projectory button click */ }
  ),
  InventoryItem(
    title = "Stock",
    icon = Icons.Filled.AddShoppingCart,
    onClick = { /* Handle Stock Management button click */ }
  ),
  InventoryItem(
    title = "Purchase",
    icon = Icons.Filled.Atm,
    onClick = { /* Handle Purchase Order button click */ }
  ),
  InventoryItem(
    title = "Receive",
    icon = Icons.Filled.Bolt,
    onClick = { /* Handle Receive Goods button click */ }
  ),
  InventoryItem(
    title = "Requisition",
    icon = Icons.Filled.Download,
    onClick = { /* Handle Requisition button click */ }
  ),
  InventoryItem(
    title = "Inventory",
    icon = Icons.Filled.Download,
    onClick = { /* Handle Requisition button click */ }
)
)

@Composable
fun InventoryButton(item: InventoryItem) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .height(150.dp)
      .padding(8.dp),
    elevation = 4.dp,
    backgroundColor = Color.White
  ) {
    Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
      ) {
        androidx.compose.material3.Icon(
          imageVector = item.icon,
          contentDescription = item.title,
          modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
          text = item.title,
          fontSize = 15.sp,
          modifier = Modifier.weight(1f)
        )
      }
      Spacer(modifier = Modifier.height(8.dp))
      Button(
        onClick = item.onClick,
        modifier = Modifier.size(160.dp)
          .background(Color(0xFF00FFFF))
      ) {
        Text(text = "Go")
      }
    }
  }
}

@Preview
@Composable
fun PreviewInventoryManagementFragment() {
  InventoryManagementFragment()
}