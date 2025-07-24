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
    fun inferRarityFromName(name: String): String {
        return when {
            name.startsWith("secret") -> "Secret"
            name.startsWith("mythic") -> "Mythic"
            name.startsWith("legendary") -> "Legendary"
            name.startsWith("epic") -> "Epic"
            name.startsWith("rare") -> "Rare"
            else -> "Common"
        }
    }

    // 🔄 NEW: Load collection data from backend
    fun setCollectionFromBackend(data: Map<String, Int>) {
        collectionMap.clear()
        for ((name, count) in data) {
            val rarity = inferRarityFromName(name)
            val image = PNGImage(name = name, count = count, rarity = rarity)
            collectionMap[image] = count
        }
    }


    fun getCollection(): Map<PNGImage, Int> {
        return collectionMap.toMap()
    }

    fun getMap(): Map<PNGImage, Int> {
        return collectionMap.toMap()
    }

    fun getTotalPullCount(): Int {
        return totalPulls
    }

    fun clear() {
        collectionMap.clear()
        totalPulls = 0
    }
}
