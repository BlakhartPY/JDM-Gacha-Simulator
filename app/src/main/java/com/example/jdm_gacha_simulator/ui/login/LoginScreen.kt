package com.example.jdm_gacha_simulator.ui.login

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jdm_gacha_simulator.ui.navigation.Routes

@Composable
fun LoginScreen(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Welcome to JDM Gacha Simulator!")
        Button(
            onClick = {
                // Navigate to Pull screen when "Start" button is clicked
                navController.navigate(Routes.PULL)
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Start")
        }
    }
}
