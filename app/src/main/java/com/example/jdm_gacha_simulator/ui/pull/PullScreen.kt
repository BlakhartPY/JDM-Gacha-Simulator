package com.example.jdm_gacha_simulator.ui.pull

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jdm_gacha_simulator.R
import com.example.jdm_gacha_simulator.data.PNGImage
import com.example.jdm_gacha_simulator.ui.navigation.Routes
import com.example.jdm_gacha_simulator.utils.*

@Composable
fun PullCard(item: PNGImage, context: Context) {
    val imageRes = getDrawableResIdByName(context, item.name)
    val bgColor = when (item.rarity) {
        "Secret" -> Color(0xFFB71C1C)
        "Mythic" -> Color(0xFFF06292)
        "Legendary" -> Color(0xFFFFD700)
        "Epic" -> Color(0xFF7E57C2)
        "Rare" -> Color(0xFF42A5F5)
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    Image(
        painter = painterResource(id = imageRes),
        contentDescription = item.name,
        modifier = Modifier.height(175.dp)
    )
}

@Composable
fun PullScreen(navController: NavController) {
    val context = LocalContext.current
    var pullResults by remember { mutableStateOf<List<PNGImage>>(emptyList()) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.background02),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Foreground content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
        ) {
            Text(
                text = "Pull 10 PNGs!",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            if (pullResults.isNotEmpty()) {
                Text(
                    text = "Pulled Results",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                val groups = listOf(
                    pullResults.take(3),
                    pullResults.drop(3).take(2),
                    pullResults.drop(5).take(3),
                    pullResults.drop(8).take(2)
                )

                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    for (row in groups) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally)
                        ) {
                            for (item in row) {
                                PullCard(item = item, context = context)
                            }
                        }
                    }
                }
            }
        }

        // Bottom content: Total + Buttons
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = "Total Pulled: ${SessionCollection.getTotalPullCount()}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Button(
                onClick = {
                    val userId = SessionManager.currentUserId
                    if (userId == -1) {
                        Toast.makeText(context, "User not logged in!", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val results = List(10) { GachaLogic.rollOnce() }
                    pullResults = results
                    SessionCollection.addPulledItems(results)

                    for (card in results) {
                        InsertCardRequest.insertCard(
                            context = context,
                            userId = userId,
                            cardName = card.name,
                            onSuccess = { response ->
                                android.os.Handler(android.os.Looper.getMainLooper()).post {
                                    Toast.makeText(context, "Saved: $response", Toast.LENGTH_SHORT).show()
                                }
                            },
                            onError = { error ->
                                android.os.Handler(android.os.Looper.getMainLooper()).post {
                                    Toast.makeText(context, "Error: $error", Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                    }
                },
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, start = 16.dp, end = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.pullbutton),
                        contentDescription = "Pull Icon",
                        modifier = Modifier.scale(1.6f)
                    )
                }
            }

            OutlinedButton(
                onClick = {
                    navController.navigate(Routes.COLLECTION)
                },
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, start = 16.dp, end = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.viewcollectionbutton),
                        contentDescription = "Collection Icon",
                        modifier = Modifier.scale(1.6f)
                    )
                }
            }
        }
    }
}
