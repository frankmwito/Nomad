package com.example.nomad

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.ai.client.generativeai.BuildConfig

class HelpScreen : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val context = LocalContext.current
      HelpFragment()
    }
  }
}
@Composable
fun HelpFragment() {
  val coroutineScope = rememberCoroutineScope()
  val ctx = LocalContext.current
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    horizontalAlignment = Alignment.Start
  ) {
    Text(
      text = "Help",
      style = MaterialTheme.typography.h6,
      fontSize = 24.sp
    )
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .height(250.dp)
        .padding(16.dp),
      elevation = 4.dp,
      backgroundColor = MaterialTheme.colors.surface
    ) {
      Column(
        modifier = Modifier.fillMaxSize().weight(1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          text = "About Mango",
          fontSize = 18.sp,
          modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        SettingsItem(
          title = "Mango services",
          icon = Icons.Default.ArrowForwardIos,
          onClick = { val intent = Intent(ctx, MangoServices::class.java)
            ctx.startActivity(intent)}
        )
        SettingsItem(
          title = "Frequently Asked Questions",
          icon = Icons.Default.ArrowForwardIos,
          onClick = {val intent = Intent(ctx, FAQ::class.java)
            ctx.startActivity(intent)}
        )
        SettingsItem(
          title = "Privacy Policy",
          icon = Icons.Default.ArrowForwardIos,
          onClick = { val intent = Intent(ctx, Privacy::class.java)
            ctx.startActivity(intent) }
        )
      }
    }
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .height(250.dp)
        .padding(16.dp),
      elevation = 4.dp,
      backgroundColor = MaterialTheme.colors.surface
    ) {
      Column(
        modifier = Modifier.fillMaxSize().weight(1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          text = "Settings",
          fontSize = 18.sp,
          modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.height(10.dp))
        SettingsItem(
          title = "Report system failure",
          icon = Icons.Default.ArrowForwardIos,
          onClick = {
            // Open the dial pad with the phone number already dialed
            val phoneNumber = "0794080082" // Replace with the actual phone number
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            ctx.startActivity(intent)
          }
        )
        SettingsItem(
          title = "App version used",
          icon = Icons.Default.ArrowForwardIos,
          onClick = {
            // Print the version of the app
            val versionName = BuildConfig.VERSION_NAME // Replace with the actual version name
            Toast.makeText(ctx, "Version: $versionName", Toast.LENGTH_SHORT).show()
          }
        )
        SettingsItem(
          title = "Clear cache",
          icon = Icons.Default.ArrowForwardIos,
          onClick = {
            // Clear the cache size
            val cacheDir = ctx.cacheDir
            if (cacheDir != null) {
              val files = cacheDir.listFiles()
              if (files != null) {
                for (file in files) {
                  file.deleteRecursively()
                }
              }
              Toast.makeText(ctx, "Cache cleared", Toast.LENGTH_SHORT).show()
            } else {
              Toast.makeText(ctx, "Cache directory not found", Toast.LENGTH_SHORT).show()
            }
          }
        )
      }
    }
  }
}

@Composable
    fun SettingsItem(title: String, icon: ImageVector, onClick: () -> Unit) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp)
          .clickable { onClick() }
          .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
      ) {
        Icon(
          imageVector = icon,
          contentDescription = title,
          modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
          text = title,
          fontSize = 16.sp,
          modifier = Modifier.weight(1f)
        )
      }
    }
@Preview(showBackground = true)
@Composable
fun PreviewHelpFragment() {
  HelpFragment()
}
