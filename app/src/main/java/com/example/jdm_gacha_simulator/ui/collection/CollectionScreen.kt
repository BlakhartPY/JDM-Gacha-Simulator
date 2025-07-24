package com.example.jdm_gacha_simulator.ui.collection

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.jdm_gacha_simulator.data.PNGImage
import com.example.jdm_gacha_simulator.ui.navigation.Routes
import com.example.jdm_gacha_simulator.utils.SessionCollection
import com.example.jdm_gacha_simulator.utils.getDrawableResIdByName
import androidx.navigation.NavController
import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.ui.draw.scale
import kotlin.collections.sortedBy
import kotlin.collections.sortedByDescending

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.jdm_gacha_simulator.R
import com.example.jdm_gacha_simulator.utils.SessionManager
import com.example.jdm_gacha_simulator.utils.TotalPullsRequest

@Composable
fun CollectionScreen(navController: NavController) {
    val context = LocalContext.current
    var sortMode by remember { mutableStateOf("rarity") }
    var ascending by remember { mutableStateOf(true) }
    var totalPulls by remember { mutableStateOf(0) }

    val userId = SessionManager.currentUserId

    LaunchedEffect(true) {
        if (userId != -1) {
            TotalPullsRequest.fetchTotalPulls(
                context,
                userId,
                onResult = { pulls -> totalPulls = pulls },
                onError = { error ->
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }


    val rarityOrder = mapOf(
        "Secret" to 0,
        "Mythic" to 1,
        "Legendary" to 2,
        "Epic" to 3,
        "Rare" to 4,
        "Common" to 5
    )

    val rawMap = SessionCollection.getMap() // assuming you create this getter
    val sortedList = run {
        val entries = rawMap.entries.toList()
        for ((img, _) in entries) {
            println("DEBUG: ${img.name} has rarity '${img.rarity}'")
        }
        when (sortMode) {
            "count" -> if (ascending) {
                entries.sortedBy { entry -> entry.value }
            } else {
                entries.sortedByDescending { entry -> entry.value }
            }
            else -> if (ascending) {
                entries.sortedBy { entry ->
                    val rarity = entry.key.rarity?.trim() ?: "Common"
                    rarityOrder[rarity] ?: Int.MAX_VALUE
                }
            } else {
                entries.sortedByDescending { entry ->
                    val rarity = entry.key.rarity?.trim() ?: "Common"
                    rarityOrder[rarity] ?: Int.MIN_VALUE
                }
            }
        }
    }



    Box(modifier = Modifier.fillMaxSize()) {
        // 🔹 Background image
        Image(
            painter = painterResource(id = com.example.jdm_gacha_simulator.R.drawable.background02),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Your Collection",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    sortMode = "rarity"
                    ascending = !ascending
                }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.width(80.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.rarity), // Replace with actual image
                            contentDescription = "Sort by Rarity Icon",
                            modifier = Modifier


                                .scale(3.1f)

                        )

                    }
                }

                Button(onClick = {
                    sortMode = "count"
                    ascending = !ascending

                }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.width(80.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.countbutton), // Replace with actual image
                            contentDescription = "Sort by Count Icon",
                            modifier = Modifier


                                .scale(3.1f)
                        )

                    }
                }
                Spacer(modifier = Modifier.height(8.dp))



            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Total Pulls: $totalPulls",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            }


            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(sortedList) { entry ->
                    val item = entry.key
                    val count = entry.value
                    val imageRes = getDrawableResIdByName(context, item.name)
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = item.name,
                            modifier = Modifier.height(175.dp)
                        )
                        Text(
                            text = "x$count",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }


            }
        }
    }
    }
