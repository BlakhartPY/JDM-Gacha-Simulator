package com.driftdynasty.api

import com.driftdynasty.data.PNGImage
import com.driftdynasty.data.User
import retrofit2.Response

class ApiRepository {
    private val apiService = RetrofitClient.instance

    suspend fun login(username: String, password: String): Response<User> {
        return apiService.login(username, password)
    }

    suspend fun register(username: String, password: String): Response<User> {
        return apiService.register(username, password)
    }

    suspend fun pull10Cars(userId: Int): Response<List<PNGImage>> {
        return apiService.pull10(userId)
    }

    suspend fun getUserCollection(userId: Int): Response<List<PNGImage>> {
        return apiService.getUserCollection(userId)
    }

    suspend fun getAllCars(): Response<List<PNGImage>> {
        return apiService.getAllCars()
    }

    suspend fun updateUserCollection(userId: Int, carIds: List<Int>): Response<ApiResponse> {
        val carIdsJson = carIds.joinToString(",", "[", "]")
        return apiService.updateUserCollection(userId, carIdsJson)
    }

    suspend fun getUserProfile(userId: Int): Response<User> {
        return apiService.getUserProfile(userId)
    }
}