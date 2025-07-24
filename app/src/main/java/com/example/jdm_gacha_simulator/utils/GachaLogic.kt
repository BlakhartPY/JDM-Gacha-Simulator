package com.example.jdm_gacha_simulator.utils

import com.example.jdm_gacha_simulator.data.PNGImage
import kotlin.random.Random

object GachaLogic {
    val secretCars = listOf(
        PNGImage("secret1.png", count = 1, rarity = "Secret"),
        PNGImage("secret2.png", count = 1, rarity = "Secret"),
        PNGImage("secret3.png", count = 1, rarity = "Secret"),
        PNGImage("secret4.png", count = 1, rarity = "Secret"),
        PNGImage("secret5.png", count = 1, rarity = "Secret")
    )

    val mythicCars = listOf(
        PNGImage("mythic1.png", 1, "Mythic"),
        PNGImage("mythic2.png", 1, "Mythic"),
        PNGImage("mythic3.png", 1, "Mythic"),
        PNGImage("mythic4.png", 1, "Mythic"),
        PNGImage("mythic5.png", 1, "Mythic")
    )

    val legendaryCars = listOf(
        PNGImage("legendary1.png", 1, "Legendary"),
        PNGImage("legendary2.png", 1, "Legendary"),
        PNGImage("legendary3.png", 1, "Legendary"),
        PNGImage("legendary4.png", 1, "Legendary"),
        PNGImage("legendary5.png", 1, "Legendary")
    )

    val epicCars = listOf(
        PNGImage("epic1.png", 1, "Epic"),
        PNGImage("epic2.png", 1, "Epic"),
        PNGImage("epic3.png", 1, "Epic"),
        PNGImage("epic4.png", 1, "Epic"),
        PNGImage("epic5.png", 1, "Epic")
    )

    val rareCars = listOf(
        PNGImage("rare1.png", 1, "Rare"), PNGImage("rare2.png", 1, "Rare"),
        PNGImage("rare3.png", 1, "Rare"), PNGImage("rare4.png", 1, "Rare"),
        PNGImage("rare5.png", 1, "Rare"), PNGImage("rare6.png", 1, "Rare"),
        PNGImage("rare7.png", 1, "Rare"), PNGImage("rare8.png", 1, "Rare"),
        PNGImage("rare9.png", 1, "Rare"), PNGImage("rare10.png", 1, "Rare")
    )

    val commonCars = listOf(
        PNGImage("common1.png", 1, "Common"), PNGImage("common2.png", 1, "Common"),
        PNGImage("common3.png", 1, "Common"), PNGImage("common4.png", 1, "Common"),
        PNGImage("common5.png", 1, "Common"), PNGImage("common6.png", 1, "Common"),
        PNGImage("common7.png", 1, "Common"), PNGImage("common8.png", 1, "Common"),
        PNGImage("common9.png", 1, "Common"), PNGImage("common10.png", 1, "Common"),
        PNGImage("common11.png", 1, "Common"), PNGImage("common12.png", 1, "Common"),
        PNGImage("common13.png", 1, "Common"), PNGImage("common14.png", 1, "Common"),
        PNGImage("common15.png", 1, "Common"), PNGImage("common16.png", 1, "Common"),
        PNGImage("common17.png", 1, "Common"), PNGImage("common18.png", 1, "Common"),
        PNGImage("common19.png", 1, "Common")
    )

    private val rarityChances = listOf(
        "Secret" to 0.001,     //0.1% chance →  1 in 1,000
        "Mythic" to 0.01,      //1% chance →  1 in 100
        "Legendary" to 0.05,   //5% chance →  1 in 20
        "Epic" to 0.12,        //12% chance →  1 in 8.33
        "Rare" to 0.20,        //20% chance →  1 in 5
        "Common" to 0.619      //61.9% chance →  1 in 1.615
    )

    fun rollOnce(): PNGImage {
        val roll = Random.nextDouble()
        var accumulated = 0.0
        val selectedRarity = rarityChances.first { (rarity, chance) ->
            accumulated += chance
            roll < accumulated
        }.first

        val pool = when (selectedRarity) {
            "Secret" -> secretCars
            "Mythic" -> mythicCars
            "Legendary" -> legendaryCars
            "Epic" -> epicCars
            "Rare" -> rareCars
            else -> commonCars
        }

        return pool.random()
    }
}
