package com.example.jdm_gacha_simulator.ui.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jdm_gacha_simulator.api.ApiService
import com.example.jdm_gacha_simulator.data.User
import com.example.jdm_gacha_simulator.ui.navigation.Routes
import com.example.jdm_gacha_simulator.utils.SharedPrefsManager

import androidx.compose.foundation.Image
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import com.example.jdm_gacha_simulator.R
import androidx.compose.ui.graphics.Color

//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import com.example.jdm_gacha_simulator.api.RetrofitClient

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") } // can be removed later if unused
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize(),

        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.background01),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {


            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username", color = Color.White) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.White) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )


            Button(
                onClick = {
                    if (username.isNotBlank() && password.isNotBlank()) {
                        val fakeUserId = "mock_${username}_123"
                        SharedPrefsManager.saveUserId(fakeUserId)
                        navController.navigate(Routes.PULL)
                    } else {
                        Toast.makeText(context, "Please enter both username and password", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .padding(top = 24.dp, start = 16.dp, end = 16.dp)


                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.button01),
                        contentDescription = "Login Icon",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .scale(1.6f)
                    )

                }
            }


            //    onClick = {
            //        if (username.isNotBlank() && password.isNotBlank()) {
            //            isLoading = true
            //            val api = RetrofitClient.instance.create(ApiService::class.java)
            //            api.login(username).enqueue(object : Callback<User> {
            //                override fun onResponse(call: Call<User>, response: Response<User>) {
            //                    isLoading = false
            //                    if (response.isSuccessful) {
            //                        val user = response.body()
            //                        if (user != null) {
            //                            SharedPrefsManager.saveUserId(user.id)
            //                            navController.navigate(Routes.PULL)
            //                        } else {
            //                            Toast.makeText(context, "Login failed: empty user", Toast.LENGTH_SHORT).show()
            //                        }
            //                    } else {
            //                        Toast.makeText(context, "Invalid response", Toast.LENGTH_SHORT).show()
            //                    }
            //                }
            //
            //                override fun onFailure(call: Call<User>, t: Throwable) {
            //                    isLoading = false
            //                    Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show()
            //                }
            //            })
            //        } else {
            //            Toast.makeText(context, "Please enter both username and password", Toast.LENGTH_SHORT).show()
            //        }
            //    },
            //    modifier = Modifier
            //        .padding(top = 24.dp)
            //        .fillMaxWidth()
            //) {
            //    Text(if (isLoading) "Logging in..." else "Start")
            //}
        }
    }
}
