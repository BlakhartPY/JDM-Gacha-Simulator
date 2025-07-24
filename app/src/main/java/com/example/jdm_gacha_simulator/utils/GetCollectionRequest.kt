package com.example.jdm_gacha_simulator.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager
import javax.net.ssl.TrustManager
import io.ktor.client.request.forms.*
import io.ktor.util.*

object GetCollectionRequest {
    private const val URL = "http://192.168.1.2/Gacha/get_collection.php"

    private val client = HttpClient(Android) {
        engine {
            sslManager = { _ ->
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                })
                SSLContext.getInstance("TLS").apply {
                    init(null, trustAllCerts, SecureRandom())
                }
            }
        }
    }

    fun fetchCollection(
        context: Context,
        userId: Int,
        onSuccess: (List<Pair<String, Int>>) -> Unit,
        onError: (String) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: HttpResponse = client.post(URL) {
                    setBody(FormDataContent(Parameters.build {
                        append("user_id", userId.toString())
                    }))
                }

                val body = response.bodyAsText()

                if (response.status.isSuccess() && body.isNotEmpty()) {
                    val jsonArray = JSONArray(body)
                    val list = mutableListOf<Pair<String, Int>>()

                    for (i in 0 until jsonArray.length()) {
                        val obj = jsonArray.getJSONObject(i)
                        val name = obj.getString("card_name")
                        val count = obj.getInt("count")
                        list.add(Pair(name, count))
                    }

                    launch(Dispatchers.Main) {
                        onSuccess(list)
                    }
                } else {
                    val msg = "Failed with status: ${response.status.value}"
                    Log.e("GetCollectionRequest", msg)
                    showToast(context, msg)
                    onError(msg)
                }
            } catch (e: Exception) {
                Log.e("GetCollectionRequest", "Network error", e)
                showToast(context, "Network error: ${e.message}")
                onError(e.message ?: "Network error")
            }
        }
    }

    private fun showToast(context: Context, message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}
