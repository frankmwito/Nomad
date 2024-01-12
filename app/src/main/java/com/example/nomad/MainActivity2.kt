package com.example.nomad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity2 : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
     Dashboard()
        }
    }
  }
@Composable
fun Dashboard(){
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .fillMaxHeight()
      .background(color = Color(android.graphics.Color.parseColor("#ede7f8")))
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  )
  {
    Row (
      modifier = Modifier
        .fillMaxWidth()
    ){
      Image(painter = painterResource(id = R.drawable.nomad1),
        contentDescription = null,
        modifier = Modifier
          .width(100.dp)
          .height(100.dp))
      Column(
        modifier = Modifier
          .height(100.dp)
          .padding(start = 14.dp)
          .weight(0.7f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
      ){
        Text(
          text ="Good Morning",
          color = Color.Black,
          fontSize = 22.sp,
          fontWeight = FontWeight.Bold
        )
        Text(
          text ="Nomad systems",
          color = Color.Black,
          fontSize = 22.sp,
        )
        Text(
          text ="13/01/2024",
          color = Color.Black,
          fontSize = 22.sp,)
      }
      Row (
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 20.dp)
      ){
        Column (modifier = Modifier
          .weight(0.5f)
          .padding(end = 12.dp)
          .background(
            color = Color.Green,
            shape = RoundedCornerShape(20.dp)
          )
          .padding(top = 16.dp),
          horizontalAlignment = Alignment.CenterHorizontally){
          Box (modifier = Modifier
          ){

          }
        }
      }
    }
  }
}


