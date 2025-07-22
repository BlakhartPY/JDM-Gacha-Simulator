package com.example.jdm_gacha_simulator.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPrefsManager {
    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveUserId(userId: String) {
        prefs.edit().putString(Constants.KEY_USER_ID, userId).apply()
    }

    fun getUserId(): String? {
        return prefs.getString(Constants.KEY_USER_ID, null)
    }

    fun clearUserId() {
        prefs.edit().remove(Constants.KEY_USER_ID).apply()
    }
}
