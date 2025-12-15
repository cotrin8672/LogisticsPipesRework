package io.github.cotrin8672.lprework

import com.tterrag.registrate.Registrate
import io.github.cotrin8672.lprework.datagen.ModDatagen
import io.github.cotrin8672.lprework.registry.ModBlocks
import net.minecraft.resources.ResourceLocation
import net.neoforged.bus.api.EventPriority
import net.neoforged.fml.common.Mod
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(LogisticsPipesRework.ID)
object LogisticsPipesRework {
    const val ID: String = "logisticspipesrework"

    val REGISTRATE = Registrate.create(ID)

    init {
        // REGISTRATE.registerEventListeners(MOD_BUS)
        ModBlocks.register(MOD_BUS)
        MOD_BUS.addListener(EventPriority.LOWEST, ModDatagen::gatherData)
    }

    fun of(id: String): ResourceLocation {
        return ResourceLocation.fromNamespaceAndPath(ID, id)
    }
}
