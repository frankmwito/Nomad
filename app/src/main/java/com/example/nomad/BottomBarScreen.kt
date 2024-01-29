package com.example.nomad

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.ui.graphics.vector.ImageVector


enum class BottomBarScreen(
  val route: String,
  val title: String,
  val icon: ImageVector,
) {
  HOME("home", "Home", Icons.Default.Home),
  INVENTORY("Inventory", "Inventory", Icons.Default.Inventory),
  MANAGEMENT("management", "Management", Icons.Default.ManageAccounts),
  HELP("help", "Help", Icons.Default.Help); // Semicolon added to close the enum class body
} 