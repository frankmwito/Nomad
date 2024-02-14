package com.example.nomad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.nomad.ui.theme.NomadTheme

class MangoServices : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      NomadTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          Mangoservices()
        }
      }
    }
  }
}

@Composable
fun Mangoservices(){
  Scaffold(
    topBar = {
      TopAppBar(
        title = { androidx.compose.material.Text(text = "Mango Services", textAlign = TextAlign.Center) },
      )
    },
    content = { paddingValues ->
      Box(
        Modifier
          .fillMaxSize()
          .padding(paddingValues)
      ) {
        Text(text = "Welcome",
          textAlign = TextAlign.Center)
      }
    }
  )
}
