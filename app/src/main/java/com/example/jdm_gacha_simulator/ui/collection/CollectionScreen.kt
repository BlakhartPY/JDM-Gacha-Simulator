package com.example.jdm_gacha_simulator.ui.collection

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
import kotlin.collections.sortedBy
import kotlin.collections.sortedByDescending
@Composable
fun CollectionScreen(navController: NavController) {
    val context = LocalContext.current
    var sortMode by remember { mutableStateOf("rarity") }
    var ascending by remember { mutableStateOf(true) }

    val rarityOrder = mapOf(
        "Secret" to 0,
        "Mythic" to 1,
        "Legendary" to 2,
        "Epic" to 3,
        "Rare" to 4,
        "Common" to 5
    )

    val rawCollection = SessionCollection.getCollection()
    val rawMap = SessionCollection.getMap() // assuming you create this getter
    val sortedList = remember(sortMode, ascending, rawMap) {
        val entries = rawMap.entries.toList()
        when (sortMode) {
            "count" -> if (ascending) {
                entries.sortedBy { entry -> entry.value }
            } else {
                entries.sortedByDescending { entry -> entry.value }
            }
            else -> if (ascending) {
                entries.sortedBy { entry -> rarityOrder[entry.key.rarity] ?: Int.MAX_VALUE }
            } else {
                entries.sortedByDescending { entry -> rarityOrder[entry.key.rarity] ?: Int.MIN_VALUE }
            }
        }
    }



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
                    contentDescription = "Back"
                )
            }
            Text(
                text = "Your Collection",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
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
                Text("Sort by Rarity")
            }

            Button(onClick = {
                sortMode = "count"
                ascending = !ascending
            }) {
                Text("Sort by Count")
            }
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
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }


        }
    }
}
