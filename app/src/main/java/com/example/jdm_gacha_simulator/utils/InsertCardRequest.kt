package com.example.jdm_gacha_simulator.utils

import android.content.Context
import okhttp3.*
import java.io.IOException

object InsertCardRequest {
    private const val INSERT_CARD_URL = "http://192.168.1.2/Gacha/insert_card.php"

    fun insertCard(
        context: Context,
        userId: Int,
        cardName: String,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        val client = OkHttpClient()
        val requestBody = FormBody.Builder()
            .add("user_id", userId.toString())
            .add("card_name", cardName)
            .build()

        val request = Request.Builder()
            .url(INSERT_CARD_URL)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onError(e.message ?: "Unknown error")
            }

            override fun onResponse(call: Call, response: Response) {
                response.use { res ->
                    val bodyString = res.body?.string() ?: "Success"
                    if (res.isSuccessful) {
                        onSuccess(bodyString)
                    } else {
                        onError("Failed to insert card. HTTP ${res.code}")
                    }
                }
            }
        })
    }
}
