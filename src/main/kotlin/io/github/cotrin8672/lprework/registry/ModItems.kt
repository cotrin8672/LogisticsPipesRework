package io.github.cotrin8672.lprework.registry

import io.github.cotrin8672.lprework.LogisticsPipesRework
import net.minecraft.world.item.Item

object ModItems {
    private val REGISTRATE = LogisticsPipesRework.registrate()

    val TEST = REGISTRATE.item("test", ::Item)
        .register()

    fun register() {}
}
