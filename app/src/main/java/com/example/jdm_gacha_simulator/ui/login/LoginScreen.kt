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
import android.content.Context
import com.example.jdm_gacha_simulator.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.util.Log
import com.example.jdm_gacha_simulator.utils.GetCollectionRequest
import com.example.jdm_gacha_simulator.utils.SessionCollection

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.engine.cio.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.forms.*
import io.ktor.http.ContentType.Application.Json


//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import com.example.jdm_gacha_simulator.api.RetrofitClient

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Welcome to JDM Gacha Simulator!",
                style = MaterialTheme.typography.titleLarge
            )

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        KTORlogin(context, username, password, navController)
                    }
                },
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(),
                enabled = username.isNotBlank() && password.isNotBlank()
            ) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(8.dp))

            val context = LocalContext.current
            val coroutineScope = rememberCoroutineScope()

            Button(
                onClick = {
                    coroutineScope.launch {
                        registerUser(context, username, password)
                    }
                },
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                enabled = username.isNotBlank() && password.isNotBlank()
            ) {
                Text("Register")
            }

        }

    }
}
suspend fun KTORlogin(context: Context, username: String, password: String, navController: NavController) {
    val client = HttpClient(CIO)
    try {
        val response = client.post("http://192.168.1.2/Gacha/login.php") {
            setBody(FormDataContent(Parameters.build {
                append("username", username)
                append("password", password)
            }))
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded)
            }
        }

        val body = response.bodyAsText().trim()

        withContext(Dispatchers.Main) {
            Log.d("KTORlogin", "Response: $body")

            if (body.startsWith("Login successful")) {
                val parts = body.split("|")
                val userId = parts.getOrNull(1)?.toIntOrNull()

                if (userId != null && userId != -1) {
                    SessionManager.currentUserId = userId
                    SharedPrefsManager.saveUserId(userId)

                    Toast.makeText(context, "Login successful (ID: $userId)", Toast.LENGTH_SHORT).show()

                    // ✅ Fetch collection and safely navigate on main thread
                    GetCollectionRequest.fetchCollection(
                        context,
                        userId = userId,
                        onSuccess = { collectionList ->
                            collectionList.forEach { (name, count) ->
                                Log.d("Card", "$name x$count")
                            }

                            // ✅ Set the collection into session storage
                            SessionCollection.setCollectionFromBackend(collectionList.toMap())

                            CoroutineScope(Dispatchers.Main).launch {
                                navController.navigate(Routes.PULL) {
                                    popUpTo(Routes.LOGIN) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        },
                        onError = {
                            CoroutineScope(Dispatchers.Main).launch {
                                Toast.makeText(context, "Error fetching collection: $it", Toast.LENGTH_SHORT).show()
                                navController.navigate(Routes.PULL) {
                                    popUpTo(Routes.LOGIN) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        }
                    )
                } else {
                    Toast.makeText(context, "Failed to parse user ID", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Login failed: $body", Toast.LENGTH_SHORT).show()
            }
        }
    } catch (e: Exception) {
        withContext(Dispatchers.Main) {
            Log.e("KTORlogin", "Exception: ${e.message}", e)
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    } finally {
        client.close()
    }
}




suspend fun registerUser(context: Context, username: String, password: String) {
    val client = HttpClient(CIO)
    try {
        val response: HttpResponse = client.get("http://192.168.1.2/Gacha/register.php") {
            url {
                parameters.append("username", username)
                parameters.append("password", password)
            }
        }
        val result = response.bodyAsText()
        context.toast(result)
    } catch (e: Exception) {
        context.toast("Error: ${e.localizedMessage}")
    } finally {
        client.close()
    }
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
