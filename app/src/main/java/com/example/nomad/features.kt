package com.example.nomad

import androidx.annotation.DrawableRes

data class Features(
  val title:String,
  @DrawableRes val iconId: Int,
  val lightColor: androidx.compose.ui.graphics.Color,
  val mediumColor: androidx.compose.ui.graphics.Color,
  val darkColor: androidx.compose.ui.graphics.Color
)
