package com.example.jdm_gacha_simulator.ui.pull

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
fun PullScreen(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Pull 10 PNGs!")
        Button(
            onClick = {
                // Simulate pulling and navigate to the Collection screen
                navController.navigate(Routes.COLLECTION)
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "10 Pull")
        }
    }
}
