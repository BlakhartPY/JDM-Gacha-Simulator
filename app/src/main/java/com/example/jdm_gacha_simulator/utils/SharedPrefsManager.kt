package com.example.jdm_gacha_simulator.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefsManager(context: Context) {

    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)

    fun saveUserId(userId: String) {
        sharedPrefs.edit().putString(Constants.KEY_USER_ID, userId).apply()
    }

    fun getUserId(): String? {
        return sharedPrefs.getString(Constants.KEY_USER_ID, null)
    }

    fun clearUserId() {
        sharedPrefs.edit().remove(Constants.KEY_USER_ID).apply()
    }
}
