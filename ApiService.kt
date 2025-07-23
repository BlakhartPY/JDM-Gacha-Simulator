package com.driftdynasty.api

import com.driftdynasty.data.PNGImage
import com.driftdynasty.data.User
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // Authentication endpoints
    @POST("login.php")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<User>

    @POST("register.php")
    @FormUrlEncoded
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<User>

    // Pull 10 random cars
    @GET("pull10.php")
    suspend fun pull10(@Query("user_id") userId: Int): Response<List<PNGImage>>

    // Get user's collection
    @GET("collection.php")
    suspend fun getUserCollection(@Query("user_id") userId: Int): Response<List<PNGImage>>

    // Get all available cars with rarity info
    @GET("getAllCars.php")
    suspend fun getAllCars(): Response<List<PNGImage>>

    // Update user collection (add pulled cars)
    @POST("updateCollection.php")
    @FormUrlEncoded
    suspend fun updateUserCollection(
        @Field("user_id") userId: Int,
        @Field("car_ids") carIds: String // JSON array of car IDs
    ): Response<ApiResponse>

    // Get user profile
    @GET("getUserProfile.php")
    suspend fun getUserProfile(@Query("user_id") userId: Int): Response<User>
}
