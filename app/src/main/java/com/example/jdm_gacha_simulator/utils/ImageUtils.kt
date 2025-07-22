package com.example.jdm_gacha_simulator.utils

import android.content.Context

fun getDrawableResIdByName(context: Context, name: String): Int {
    return context.resources.getIdentifier(
        name.substringBeforeLast("."),
        "drawable",
        context.packageName
    )
}

