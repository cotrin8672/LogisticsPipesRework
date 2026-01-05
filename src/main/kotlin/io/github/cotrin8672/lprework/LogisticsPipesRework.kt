package io.github.cotrin8672.lprework

import com.tterrag.registrate.Registrate
import io.github.cotrin8672.lprework.registry.ModBlockEntityTypes
import io.github.cotrin8672.lprework.registry.ModBlocks
import net.minecraft.resources.ResourceLocation
import net.neoforged.fml.common.Mod

@Mod(LogisticsPipesRework.ID)
object LogisticsPipesRework {
    const val ID: String = "logisticspipesrework"

    private val REGISTRATE = Registrate.create(ID)

    init {
        ModBlocks.register()
        ModBlockEntityTypes.register()
    }

    fun asResource(id: String): ResourceLocation {
        return ResourceLocation.fromNamespaceAndPath(ID, id)
    }

    fun registrate(): Registrate = REGISTRATE
}
