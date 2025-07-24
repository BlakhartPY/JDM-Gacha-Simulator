package com.example.jdm_gacha_simulator.utils

object SessionManager {
    var currentUserId: Int = -1
    var currentCollection: Map<String, Int> = emptyMap()

    fun logout() {
        currentUserId = -1
        currentCollection = emptyMap()
    }
}
