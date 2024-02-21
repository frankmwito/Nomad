package com.example.nomad

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Atm
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class InventoryScreen : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
        InventoryManagementFragment()
      }
    }
  }

@Composable
fun InventoryManagementFragment() {
  val coroutineScope = rememberCoroutineScope()
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text(text = "Inventory Management", textAlign = TextAlign.Center) },
      )
    },
    content = { paddingValues ->
      Box(
        Modifier
          .fillMaxSize()
          .padding(paddingValues)
      ) {
          LazyColumn(
            modifier = Modifier
              .fillMaxWidth()
              .height(700.dp) // Set a fixed height for the LazyColumn
          ) {
            items(1) {
              MyButtons()
            }
          }
        }
      }
      )
    }
@Composable
fun MyButtons() {
  val context = LocalContext.current
  Column {
    Row(
      modifier = Modifier
        .fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceEvenly
    ) {
      MyCard(
        title = "Purchase Order",
        icon = Icons.Filled.Atm,
        image = painterResource(id = R.drawable.purchaseorder),
        onClick = { val intent = Intent(context, Purchase::class.java)
          context.startActivity(intent)}
      )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row {
      MyCard(
        title = "Sales",
        icon = Icons.Filled.AutoGraph,
        image = painterResource(id = R.drawable.tales),
        onClick = { /* handle button click here */ }
      )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row(
      modifier = Modifier
        .fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceEvenly
    ) {
      MyCard(
        title = "Inventory Management",
        icon = Icons.Filled.Inventory,
        image = painterResource(id = R.drawable.inventorymanagement),
        onClick = {
          val intent = Intent(context, InventoryManagementScreen::class.java)
          context.startActivity(intent)
        }
      )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row {
      MyCard(
        title = "Stock Management",
        icon = Icons.Filled.AddShoppingCart,
        image = painterResource(id = R.drawable.stockmanagement),
        onClick = { /* handle button click here */ }
      )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row(
      modifier = Modifier
        .fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceEvenly
    ) {
      MyCard(
        title = "Receive Goods",
        icon = Icons.Filled.Bolt,
        image = painterResource(id = R.drawable.receivegoods),
        onClick = { /* handle button click here */ }
      )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row {
      MyCard(
        title = "Requisition Form",
        icon = Icons.Filled.Download,
        image = painterResource(id = R.drawable.requisition),
        onClick = { /* handle button click here */ }
      )
    }
  }
}

@Composable
fun MyCard(
  title: String,
  icon: ImageVector,
  image: Painter,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .height(150.dp)
      .padding(8.dp)
      .clickable(onClick = onClick),
    elevation = 8.dp,
    shape = RoundedCornerShape(16.dp),
    backgroundColor = Color.White
  ) {
    Image(painter = image,
      contentDescription =null,
      modifier = Modifier.fillMaxWidth(),contentScale = ContentScale.Crop)
    Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Icon(
        imageVector = icon,
        contentDescription = title,
        modifier = Modifier.size(64.dp)
      )
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = title,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,modifier = Modifier.weight(1f),
        textAlign = TextAlign.Center
      )
    }
  }
}
@Preview
@Composable
fun PreviewInventoryManagementFragment() {
  InventoryManagementFragment()
}
