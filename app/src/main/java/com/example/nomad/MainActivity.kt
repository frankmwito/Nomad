package com.example.nomad

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nomad.ui.theme.NomadTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      Surface(color = Color(0xFFFFFFFF),modifier = Modifier.fillMaxSize()){
        Navigation()
      }
    }
    }
  }
@SuppressLint("SuspiciousIndentation")
@Composable
fun Navigation(){
  val navController = rememberNavController()
  NavHost(navController = navController, startDestination = "splash_screen" ){
    composable("splash_screen") {
      SplashScreen(navController = navController)
    }
    composable("main_screen") {
      Box(modifier = Modifier.fillMaxSize()) {
        Column(
          modifier = Modifier
            .fillMaxSize(),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Image(
            painter = painterResource(id = R.drawable.nomad1),
            contentDescription = "ADLIB",
            contentScale = ContentScale.Inside,
            modifier = Modifier.padding(bottom = 20.dp)
          )
          Spacer(modifier = Modifier.height(20.dp))

            var username by remember {
              mutableStateOf("")
            }
          var isInvalidFormat by remember {
            mutableStateOf(false)
          }
            OutlinedTextField(
              value = username,
              onValueChange = { if (it.matches("^[a-zA-Z0-9]*$".toRegex())) { // Allow letters and numbers
                username = it
                isInvalidFormat = false
              } else {
                isInvalidFormat = true
              }
              },
              label = {
                Text(text = "Username")
              },
              isError = isInvalidFormat,
              placeholder = {Text(text = "Enter your username")},
              supportingText = { Text(text = "Use characters and digits format.")},
              singleLine = true,
              keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
              leadingIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                  Icon(imageVector = Icons.Filled.Person, contentDescription = "PERSON ICON")
                }
              },
            )
          Spacer(modifier = Modifier.height(20.dp))
            var password by remember {
              mutableStateOf("")
            }
          var passwordVisibility by remember {
            mutableStateOf(false)
          }
          val mContext = LocalContext.current
          val icon = if(passwordVisibility)
            painterResource(id = R.drawable.visibility)
          else
            painterResource(id = R.drawable.visibilityoff)
            OutlinedTextField(
              value = password,
              onValueChange = { if (it.matches("^[0-9]*$".toRegex())) { // Validate only numbers
                password = it
                isInvalidFormat = false
              } else {
                isInvalidFormat = true
              }
              },
              label = {
                Text(text = "password")
              },
              isError = isInvalidFormat,
              placeholder = {Text(text = "Enter your password")},
              supportingText = { Text(text =  "Use digits format.")},
              singleLine = true,
              keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
              leadingIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                  Icon(imageVector = Icons.Filled.Lock, contentDescription = "PERSON ICON")
                }
              },
              trailingIcon = {
                IconButton(onClick = {
                  passwordVisibility = !passwordVisibility
                }) {
                  Icon(painter = icon,
                    contentDescription = "visibility logo")
                }
              },
              visualTransformation = if(passwordVisibility) VisualTransformation.None
              else PasswordVisualTransformation()
            )
          Spacer(modifier = Modifier.height(20.dp))
            TextButton(onClick = {mContext.startActivity(Intent(mContext, HomeScreen::class.java)) }) {
              Text(text = "Login")
            }
          }
        }
      }
    }
  }
@Composable
fun SplashScreen(navController: NavController){
  val scale = remember {
    Animatable(0f)
  }
  LaunchedEffect(key1 = true){
    scale.animateTo(
    targetValue = 1.0f,
      animationSpec = tween(
       durationMillis = 500,
       easing = {
         OvershootInterpolator(2f).getInterpolation(it)
       }
     )
    )
    delay(3000)
    navController.navigate("main_screen")
  }
  Box (contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
Image(painter = painterResource(id = R.drawable.nomad),
  contentDescription =  "logo",
  modifier = Modifier.scale(scale.value)
)
  }
}