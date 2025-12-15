package io.github.cotrin8672.lprework.registry

import io.github.cotrin8672.lprework.LogisticsPipesRework
import io.github.cotrin8672.lprework.content.pipe.basic.BasicPipe
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.MapColor
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredRegister

object ModBlocks {
    private val BLOCKS: DeferredRegister.Blocks = DeferredRegister.createBlocks(LogisticsPipesRework.ID)

    val BASIC_PIPE: DeferredBlock<BasicPipe> = BLOCKS.registerBlock(
        "basic_pipe",
        ::BasicPipe,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .strength(0.5f)
    )

    fun register(bus: IEventBus) {
        BLOCKS.register(bus)
    }
}
