package com.example.jdm_gacha_simulator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.jdm_gacha_simulator.ui.navigation.AppNavigation
import com.example.jdm_gacha_simulator.ui.theme.JDMGachaSimulatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JDMGachaSimulatorTheme {
                AppNavigation()
            }
        }
    }
}
