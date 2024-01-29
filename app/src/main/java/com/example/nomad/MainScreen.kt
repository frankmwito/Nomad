package com.example.nomad

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen() {
  val navController = rememberNavController()
  Scaffold(
    bottomBar = { BottomBar(navController = navController) }
  ) { innerPadding -> // Use the provided innerPadding
    BottomNavGraph(navController = navController, contentPadding = innerPadding)
  }
}

@Composable
fun BottomBar(navController: NavController){
  val screens = listOf(
    BottomBarScreen.HOME,
    BottomBarScreen.INVENTORY,
    BottomBarScreen.MANAGEMENT,
    BottomBarScreen.HELP,
  )
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentDestination = navBackStackEntry?.destination

  BottomNavigation(modifier = Modifier.background(Color.White)) {
    screens.forEach { screen ->
      AddItem(
        screen = screen,
        currentDestination = currentDestination,
        navController = navController as NavHostController
      )
    }
  }
}

@Composable
fun RowScope.AddItem(
  screen: BottomBarScreen,
  currentDestination: NavDestination?,
  navController: NavHostController
) {
  BottomNavigationItem(
    label = {
      Text(text = screen.title)
    },
    icon = {
      Icon(
        imageVector = screen.icon,
        contentDescription = "Navigation Icon")
    },
    selected = currentDestination?.hierarchy?.any {
      it.route == screen.route
    } == true,
    unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
    onClick = {
      navController.navigate(screen.route) {
        popUpTo(navController.graph.findStartDestination().id)
        launchSingleTop = true
      }
    }
  )
}
