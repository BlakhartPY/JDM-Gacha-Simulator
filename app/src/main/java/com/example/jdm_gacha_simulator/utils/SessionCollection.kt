package com.example.jdm_gacha_simulator.utils

import com.example.jdm_gacha_simulator.data.PNGImage

object SessionCollection {
    private val collectionMap = mutableMapOf<PNGImage, Int>()
    private var totalPulls = 0

    fun addPulledItems(items: List<PNGImage>) {
        for (item in items) {
            val key = collectionMap.keys.find { it.name == item.name }
            if (key != null) {
                collectionMap[key] = collectionMap.getOrDefault(key, 0) + item.count
            } else {
                collectionMap[item.copy()] = item.count
            }
        }
        totalPulls += items.size
    }

    fun getCollection(): Map<PNGImage, Int> {
        return collectionMap.toMap()
    }

    fun getTotalPullCount(): Int {
        return totalPulls
    }
    fun getMap(): Map<PNGImage, Int> {
        return collectionMap.toMap()
    }


    fun clear() {
        collectionMap.clear()
        totalPulls = 0
    }

}
