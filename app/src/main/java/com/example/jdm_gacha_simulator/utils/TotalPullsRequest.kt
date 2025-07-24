package com.example.jdm_gacha_simulator.utils

import android.content.Context
import android.widget.Toast
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object TotalPullsRequest {
    private val client = HttpClient(Android) {
        engine {
            sslManager = {
                val trustManager = object : X509TrustManager {
                    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                }

                val context = SSLContext.getInstance("TLS")
                context.init(null, arrayOf<TrustManager>(trustManager), SecureRandom())
                context
            }
        }
    }

    fun fetchTotalPulls(
        context: Context,
        userId: Int,
        onResult: (Int) -> Unit,
        onError: (String) -> Unit
    ) {
        val url = "http://192.168.1.2/Gacha/get_total_pulls.php?user_id=$userId"

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: HttpResponse = client.get(url)
                val body = response.body<String>()
                val json = JSONObject(body)
                if (json.has("total_pulls")) {
                    onResult(json.getInt("total_pulls"))
                } else {
                    showToast(context, "Invalid server response")
                    onError("Invalid server response")
                }
            } catch (e: Exception) {
                showToast(context, "Failed to load pulls: ${e.message}")
                onError("Network error")
            }
        }
    }

    private fun showToast(context: Context, message: String) {
        android.os.Handler(context.mainLooper).post {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}
