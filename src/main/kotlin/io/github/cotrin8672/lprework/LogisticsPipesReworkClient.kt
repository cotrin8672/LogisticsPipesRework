package io.github.cotrin8672.lprework

import io.github.cotrin8672.lprework.registry.ModPartialModels
import net.neoforged.api.distmarker.Dist
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(value = LogisticsPipesRework.ID, dist = [Dist.CLIENT])
object LogisticsPipesReworkClient {
    init {
        MOD_BUS.addListener<FMLClientSetupEvent> {
            ModPartialModels.register()
        }
    }
}