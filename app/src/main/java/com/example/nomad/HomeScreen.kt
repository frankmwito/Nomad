@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)

package com.example.nomad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Atm
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Money
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
val transaction = listOf(
  Transactions(
    name = "Mpesa",
    balance = 20000,
    icon = Icons.Rounded.AttachMoney
  ),

  Transactions(
    name = "Card",
    balance = 35000,
    icon = Icons.Rounded.Atm
  ),

  Transactions(
    name = "Cash",
    balance = 50000,
    icon = Icons.Rounded.Money
  ),
)

class HomeScreen : ComponentActivity() {
  @OptIn(ExperimentalFoundationApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      Home_screen()
      MainScreen()

    }

  }
}
@ExperimentalFoundationApi
@Composable
fun Home_screen() {
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
      TransactionSection()

      FeatureSection(
        features = listOf(
          Features(
            title = "No of Sales",
            iconId = R.drawable.sales,
            lightColor =Color.Cyan,
            mediumColor = Color.White,
            darkColor = Color.LightGray
          ),
          Features(
            title = "Sales Amount",
            iconId = R.drawable.estimate,
            lightColor = Color.Cyan,
            mediumColor = Color.White,
            darkColor = Color.LightGray
          ),
          Features(
            title = "Paid Amount",
            iconId = R.drawable.paid,
            lightColor = Color.Cyan,
            mediumColor = Color.White,
            darkColor = Color.LightGray
          ),
          Features(
            title = "Bills",
            iconId = R.drawable.bills,
            lightColor = Color.Cyan,
            mediumColor = Color.White,
            darkColor = Color.LightGray
          ),
        )
      )
    }
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
      Image(
        painter = painterResource(id = R.drawable.mango2),
        contentDescription = "null",
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
        text = "Mango systems",
        fontSize = 18.sp,
      )
    }
  }
}
@Composable
fun TransactionSection(
  color: Color = Color.Cyan) {
  var isVisible by remember {
    mutableStateOf(false)
  }
  var iconState by remember {
    mutableStateOf(Icons.Rounded.KeyboardArrowUp)
  }

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
      Column(
        modifier = Modifier
          .clip(RoundedCornerShape(20.dp))
          .background(MaterialTheme.colorScheme.inverseOnSurface)
          .animateContentSize()
      ) {

        Row(
          modifier = Modifier
            .padding(16.dp)
            .animateContentSize()
            .fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {

          Box(modifier = Modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondary)
            .clickable {
              isVisible = !isVisible
              iconState = if (isVisible) {
                Icons.Rounded.KeyboardArrowUp
              } else {
                Icons.Rounded.KeyboardArrowDown
              }
            }
          ) {
            Icon(
              modifier = Modifier.size(25.dp),
              imageVector = iconState,
              contentDescription = "Transactions",
              tint = MaterialTheme.colorScheme.onSecondary
            )
          }

          Spacer(modifier = Modifier.width(20.dp))

          Text(
            text = "Transactions",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontWeight = FontWeight.Bold
          )

        }

        Spacer(
          modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer)
        )

        if (isVisible) {
          BoxWithConstraints(
            modifier = Modifier
              .fillMaxSize()
              .padding(horizontal = 16.dp)
              .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
              .background(MaterialTheme.colorScheme.background)
          ) {

            val boxWithConstraintsScope = this
            val width = boxWithConstraintsScope.maxWidth / 3

            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
            ) {

              Spacer(modifier = Modifier.height(16.dp))

              Row(
                modifier = Modifier.fillMaxWidth()
              ) {

                Text(
                  modifier = Modifier.width(width),
                  text = "Transactions",
                  fontWeight = FontWeight.SemiBold,
                  fontSize = 13.sp,
                  color = MaterialTheme.colorScheme.onBackground
                )


                Text(
                  modifier = Modifier.width(width),
                  text = "Balance",
                  fontWeight = FontWeight.SemiBold,
                  fontSize = 14.sp,
                  color = MaterialTheme.colorScheme.onBackground,
                  textAlign = TextAlign.End
                )

              }

              Spacer(modifier = Modifier.height(16.dp))

              LazyColumn {
                items(transaction.size) { index ->
                  TransactionsItem(
                    index = index,
                    width = width
                  )
                }
              }

            }
          }
        }
      }


  }

}

@Composable
fun TransactionsItem(index: Int, width: Dp) {
  val currency = transaction[index]

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 16.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {

    Row(
      modifier = Modifier.width(width),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(8.dp))
          .padding(4.dp)
      ) {
        Icon(
          modifier = Modifier.size(18.dp),
          imageVector = currency.icon,
          contentDescription =currency.name,
          tint = Color.Black
        )
      }

      Text(
        modifier = Modifier
          .padding(start = 10.dp),
        text = currency.name,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        color = MaterialTheme.colorScheme.onBackground,
      )
    }

    Text(
      modifier = Modifier
        .width(width)
        .padding(start = 10.dp),
      text = "ksh ${currency.balance}",
      fontWeight = FontWeight.Bold,
      fontSize = 15.sp,
      color = MaterialTheme.colorScheme.onBackground,
      textAlign = TextAlign.End
    )
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

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun PreviewHome_Screen(){
  Home_screen()
}