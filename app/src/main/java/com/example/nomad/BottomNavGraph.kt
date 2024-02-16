@file:OptIn(ExperimentalMaterialApi::class)

package com.example.nomad


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun BottomNavGraph(navController: NavHostController, contentPadding: PaddingValues) {
  NavHost(
    navController = navController,
    startDestination = BottomBarScreen.HOME.route
  ){
    composable(route = BottomBarScreen.HOME.route){
      Home_screen()
    }
    composable(route = BottomBarScreen.INVENTORY.route){
      InventoryManagementFragment()
    }
    composable(route = BottomBarScreen.MANAGEMENT.route){
      EmployeesManagement()
    }
    composable(route =BottomBarScreen.HELP.route){
     HelpFragment()
    }
  }
}