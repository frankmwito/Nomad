@file:OptIn(ExperimentalMaterial3Api::class)

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity3 : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MyApp()
    }
  }
}

@Composable
fun MyApp() {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Inventory Management") },
        Modifier.background( MaterialTheme.colorScheme.primary)
      )
    },
    content = {paddingValues ->
    ScrollableButtonGrid(paddingValues)
    }
  )
}

@Composable
fun ScrollableButtonGrid(paddingValues: PaddingValues) {
  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(16.dp) // Space between rows
  ) {
    items(3) { row ->
      Row( // Use Row instead of LazyRow for horizontal arrangement
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp) // Space on sides
      ) {
        this@LazyColumn.items(2){ column ->
          Button(
            onClick = { /* Handle button click */ },
            modifier = Modifier.weight(1f) // Now within a RowScope
          ) {
            Text("Button ${row * 2 + column + 1}")
          }
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyApp() {
  MyApp()
}
