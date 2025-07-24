package com.example.jdm_gacha_simulator.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import io.ktor.client.call.*


object InsertCardRequest {
    private const val INSERT_CARD_URL = "http://192.168.1.2/Gacha/insert_card.php"

    private val client = HttpClient(Android) {
        install(HttpTimeout) {
            requestTimeoutMillis = 15000
        }
        engine {
            // Trust all certificates (for dev purposes only)
            sslManager = { sslContext ->
                val trustManager = object : javax.net.ssl.X509TrustManager {
                    override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {}
                    override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {}
                    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> = arrayOf()
                }

                val context = javax.net.ssl.SSLContext.getInstance("TLS")
                context.init(null, arrayOf(trustManager), java.security.SecureRandom())
                context
            }
        }
    }

    fun insertCard(
        context: Context,
        userId: Int,
        cardName: String,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = client.post(INSERT_CARD_URL) {
                    setBody(
                        FormDataContent(Parameters.build {
                            append("user_id", userId.toString())
                            append("card_name", cardName)
                        })
                    )
                }

                val responseText = response.body<String>()


                Handler(Looper.getMainLooper()).post {
                    onSuccess(responseText)
                }
            } catch (e: Exception) {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(context, "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                    onError(e.localizedMessage ?: "Unknown error")
                }
            }
        }
    }
}
