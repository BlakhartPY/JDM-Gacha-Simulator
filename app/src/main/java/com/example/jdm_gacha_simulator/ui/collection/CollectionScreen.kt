package com.example.jdm_gacha_simulator.ui.collection

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
fun CollectionScreen(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Your Collection")
        Button(
            onClick = {
                // Navigate back to Pull screen
                navController.popBackStack(Routes.PULL, false)
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Back to Pull")
        }
    }
}
