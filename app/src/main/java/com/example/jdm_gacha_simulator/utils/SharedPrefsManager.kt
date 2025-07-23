package com.example.jdm_gacha_simulator.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPrefsManager {
    private var prefs: SharedPreferences? = null

    fun init(context: Context) {
        prefs = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveUserId(userId: Int) {
        prefs?.edit()?.putInt(Constants.KEY_USER_ID, userId)?.apply()
    }

    fun getUserId(): Int? {
        return prefs?.getInt(Constants.KEY_USER_ID, -1)?.takeIf { it != -1 }
    }

    fun clearUserId() {
        prefs?.edit()?.remove(Constants.KEY_USER_ID)?.apply()
    }
}
