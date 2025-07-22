package com.example.jdm_gacha_simulator.api

import com.example.jdm_gacha_simulator.data.PNGImage
import com.example.jdm_gacha_simulator.data.User
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("login.php")
    fun login(@Field("username") username: String): Call<User>

    @FormUrlEncoded
    @POST("pull10.php")
    fun pullTen(@Field("user_id") userId: String): Call<List<PNGImage>>

    @GET("collection.php")
    fun getCollection(@Query("user_id") userId: String): Call<List<PNGImage>>
}
