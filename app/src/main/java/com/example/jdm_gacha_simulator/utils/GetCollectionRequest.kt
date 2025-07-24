package com.example.jdm_gacha_simulator.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import okhttp3.*
import org.json.JSONArray
import java.io.IOException

object GetCollectionRequest {
    private const val URL = "http://192.168.1.2/Gacha/get_collection.php"
    private val client = OkHttpClient()

    fun fetchCollection(
        context: Context,
        userId: Int,
        onSuccess: (List<Pair<String, Int>>) -> Unit,
        onError: (String) -> Unit
    ) {
        val requestBody = FormBody.Builder()
            .add("user_id", userId.toString())
            .build()

        val request = Request.Builder()
            .url(URL)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("GetCollectionRequest", "Network error: ${e.message}", e)
                showToast(context, "Network error: ${e.message}")
                onError(e.message ?: "Network error")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                if (response.isSuccessful && !body.isNullOrEmpty()) {
                    try {
                        Log.d("GetCollectionRequest", "Response: $body")
                        val jsonArray = JSONArray(body)
                        val list = mutableListOf<Pair<String, Int>>()

                        for (i in 0 until jsonArray.length()) {
                            val obj = jsonArray.getJSONObject(i)
                            val name = obj.getString("card_name")
                            val count = obj.getInt("count")
                            list.add(Pair(name, count))
                        }

                        onSuccess(list)
                    } catch (e: Exception) {
                        Log.e("GetCollectionRequest", "JSON parsing error", e)
                        showToast(context, "Invalid response format")
                        onError("Invalid response format")
                    }
                } else {
                    Log.e("GetCollectionRequest", "Failed with response code: ${response.code}")
                    showToast(context, "Failed to fetch collection: ${response.code}")
                    onError("Failed to fetch collection: ${response.code}")
                }
            }
        })
    }

    private fun showToast(context: Context, message: String) {
        android.os.Handler(context.mainLooper).post {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}
