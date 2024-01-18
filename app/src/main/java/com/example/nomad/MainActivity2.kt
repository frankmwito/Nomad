@file:OptIn(ExperimentalFoundationApi::class)

package com.example.nomad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity2 : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      HomeScreen()

    }

  }
}
@ExperimentalFoundationApi
@Composable
fun HomeScreen() {
  Box(
    modifier = Modifier
      .background(White)
      .fillMaxSize()
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
    ) {
      GreetingSection()
      Subscription()

      FeatureSection(
        features = listOf(
          Features(
            title = "No of Sales",
            iconId = R.drawable.sales,
            lightColor = Color.Black,
            mediumColor = Color.White,
            darkColor = Color.Gray
          ),
          Features(
            title = "Sales Amount",
            iconId = R.drawable.estimate,
            lightColor = Color.Black,
            mediumColor = Color.White,
            darkColor = Color.Gray
          ),
          Features(
            title = "Paid Amount",
            iconId = R.drawable.paid,
            lightColor = Color.Black,
            mediumColor = Color.White,
            darkColor = Color.Gray
          ),
          Features(
            title = "Bills",
            iconId = R.drawable.bills,
            lightColor = Color.Black,
            mediumColor = Color.White,
            darkColor = Color.Gray
          ),
        )
      )
    }
    BottomMenu(
      items = listOf(
        BottomMenuContent("Home", R.drawable.home),
        BottomMenuContent("Inventory", R.drawable.inventory),
        BottomMenuContent("Management", R.drawable.management),
        BottomMenuContent("Help", R.drawable.help),
      ),
      modifier = Modifier
        .fillMaxWidth()
        .padding(15.dp)
        .align(alignment = Alignment.BottomCenter)
    )
  }
}

@Composable
fun BottomMenu(
  items: List<BottomMenuContent>,
  modifier: Modifier = Modifier,
  activeHighlightColor: Color = Blue,
  activeTextColor: Color = White,
  inactiveTextColor: Color = Black,
  initialSelectedItemIndex: Int = 0
) {
  var selectedItemIndex by remember {
    mutableStateOf(initialSelectedItemIndex)
  }
  Row(
    horizontalArrangement = Arrangement.SpaceAround,
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
      .fillMaxWidth()
      .background(White)
      .padding(15.dp)
  ) {
    items.forEachIndexed { index, item ->
      BottomMenuItem(
        item = item,
        isSelected = index == selectedItemIndex,
        activeHighlightColor = activeHighlightColor,
        activeTextColor = activeTextColor,
        inactiveTextColor = inactiveTextColor
      ) {
        selectedItemIndex = index
      }
    }
  }
}

@Composable
fun BottomMenuItem(
  item: BottomMenuContent,
  isSelected: Boolean = false,
  activeHighlightColor: Color = Blue,
  activeTextColor: Color = White,
  inactiveTextColor: Color = Color.Cyan,
  onItemClick: () -> Unit
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    modifier = Modifier.clickable {
      onItemClick()
    }
  ) {
    Box(
      contentAlignment = Alignment.Center,
      modifier = Modifier
        .clip(RoundedCornerShape(10.dp))
        .background(if (isSelected) activeHighlightColor else Transparent)
        .padding(10.dp)
    ) {
      Icon(
        painter = painterResource(id = item.iconId),
        contentDescription = item.title,
        tint = if (isSelected) activeTextColor else inactiveTextColor,
        modifier = Modifier.size(20.dp)
      )
    }
    Text(
      text = item.title,
      color = if(isSelected) activeTextColor else inactiveTextColor
    )
  }
}

@Composable
fun GreetingSection() {
  Row(
    horizontalArrangement = Arrangement.Start,
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .padding(15.dp)
  ) {
    Column(
      verticalArrangement = Arrangement.Center
    ) {
      Icon(
        painter = painterResource(id = R.drawable.nomad1 ),
        contentDescription = null,
        modifier = Modifier.size(60.dp, 60.dp)
      )
    }
    val calendar = Calendar.getInstance()
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)

    val greeting = when {
      currentHour in 0..11 -> "Good Morning"
      currentHour in 12..17 -> "Good Afternoon"
      else -> "Good Evening"
    }

    val dateFormat = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault())
    val dateString = dateFormat.format(java.util.Date())

    Column {
      Text(
        text = greeting,
        fontSize = 18.sp,
      )
      Text(
        text = dateString,
        fontSize = 18.sp,
      )
      Text(
        text = "Nomad systems",
        fontSize = 18.sp,
      )
    }
  }
}

@Composable
fun Subscription(
  color: Color = Color.DarkGray) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier
      .padding(15.dp)
      .clip(RoundedCornerShape(10.dp))
      .background(color)
      .padding(horizontal = 15.dp, vertical = 20.dp)
      .fillMaxWidth()
  ) {
    Column {
      Text(
        text = "Premium subscription",
        fontStyle = FontStyle.Italic,
        fontFamily = FontFamily.Default,
        fontSize = 24.sp
      )
      Text(
        text = "There is more.... ",
        fontStyle = FontStyle.Italic,
        fontFamily = FontFamily.Default,
        fontSize = 15.sp
      )
    }
    Box(
      contentAlignment = Alignment.Center,
      modifier = Modifier
        .size(40.dp)
        .clip(CircleShape)
        .background(Transparent)
        .padding(10.dp)
    ) {
      Icon(
        painter = painterResource(id = R.drawable.baseline_play_arrow_24),
        contentDescription = "Play",
        tint = White,
        modifier = Modifier.size(16.dp)
      )
    }
  }
}

@Composable
@ExperimentalFoundationApi
fun FeatureSection(features: List<Features>) {
  Column(modifier = Modifier.fillMaxWidth())
  {
    Text(
      text = "Features", // This should be a String, not AnnotatedString
      modifier = Modifier.padding(15.dp)
    )
    LazyVerticalGrid(
      columns = GridCells.Fixed(2),
      contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, bottom = 7.5.dp),
      modifier = Modifier.fillMaxHeight()
    )
    {
      items(features.size) {
        FeatureItem(features = features[it])
      }
    }
  }
}
@Composable
fun FeatureItem(
  features: Features
) {
  BoxWithConstraints(
    modifier = Modifier
      .padding(7.5.dp)
      .aspectRatio(1f)
      .clip(RoundedCornerShape(10.dp))
      .background(features.darkColor)
  ) {
    val width = constraints.maxWidth
    val height = constraints.maxHeight

    // Medium colored path
    val mediumColoredPoint1 = Offset(0f, height * 0.3f)
    val mediumColoredPoint2 = Offset(width * 0.1f, height * 0.35f)
    val mediumColoredPoint3 = Offset(width * 0.4f, height * 0.05f)
    val mediumColoredPoint4 = Offset(width * 0.75f, height * 0.7f)
    val mediumColoredPoint5 = Offset(width * 1.4f, -height.toFloat())

    val mediumColoredPath = Path().apply {
      moveTo(mediumColoredPoint1.x, mediumColoredPoint1.y)
      standardQuadFromTo(mediumColoredPoint1, mediumColoredPoint2)
      standardQuadFromTo(mediumColoredPoint2, mediumColoredPoint3)
      standardQuadFromTo(mediumColoredPoint3, mediumColoredPoint4)
      standardQuadFromTo(mediumColoredPoint4, mediumColoredPoint5)
      lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
      lineTo(-100f, height.toFloat() + 100f)
      close()
    }

    // Light colored path
    val lightPoint1 = Offset(0f, height * 0.35f)
    val lightPoint2 = Offset(width * 0.1f, height * 0.4f)
    val lightPoint3 = Offset(width * 0.3f, height * 0.35f)
    val lightPoint4 = Offset(width * 0.65f, height.toFloat())
    val lightPoint5 = Offset(width * 1.4f, -height.toFloat() / 3f)

    val lightColoredPath = Path().apply {
      moveTo(lightPoint1.x, lightPoint1.y)
      standardQuadFromTo(lightPoint1, lightPoint2)
      standardQuadFromTo(lightPoint2, lightPoint3)
      standardQuadFromTo(lightPoint3, lightPoint4)
      standardQuadFromTo(lightPoint4, lightPoint5)
      lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
      lineTo(-100f, height.toFloat() + 100f)
      close()
    }
    Canvas(
      modifier = Modifier
        .fillMaxSize()
    ) {
      drawPath(
        path = mediumColoredPath,
        color = features.mediumColor
      )
      drawPath(
        path = lightColoredPath,
        color = features.lightColor
      )
    }
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(15.dp)
    )
    {
      Text(
        text = features.title,
        lineHeight = 26.sp,
        modifier = Modifier.align(Alignment.TopStart)
      )

      Icon(
        painter = painterResource(id = features.iconId),
        contentDescription = features.title,
        tint = White,
        modifier = Modifier.align(Alignment.BottomStart)
      )
      Text(
        text = "Start",
        color = colorResource(id = R.color.white),
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
          .clickable {
            // Handle the click
          }
          .align(Alignment.BottomEnd)
          .clip(RoundedCornerShape(10.dp))
          .background(colorResource(id = R.color.purple_700))
          .padding(vertical = 6.dp, horizontal = 15.dp)
      )
    }
  }
}