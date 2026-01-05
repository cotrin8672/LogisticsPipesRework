package io.github.cotrin8672.lprework.registry

import com.tterrag.registrate.util.entry.BlockEntityEntry
import io.github.cotrin8672.lprework.LogisticsPipesRework
import io.github.cotrin8672.lprework.content.pipe.basic.BasicPipeBlockEntity
import io.github.cotrin8672.lprework.content.pipe.basic.BasicPipeVisual
import io.github.cotrin8672.lprework.util.visual

object ModBlockEntityTypes {
    private val REGISTRATE = LogisticsPipesRework.registrate()

    val BASIC_PIPE: BlockEntityEntry<BasicPipeBlockEntity> = REGISTRATE
        .blockEntity("basic_pipe", ::BasicPipeBlockEntity)
        .visual(false, BasicPipeVisual.of())
        .validBlocks(ModBlocks.BASIC_PIPE)
        .register()

    fun register() {}
}