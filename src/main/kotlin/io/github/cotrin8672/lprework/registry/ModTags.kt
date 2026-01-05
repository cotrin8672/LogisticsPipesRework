package io.github.cotrin8672.lprework.registry

import io.github.cotrin8672.lprework.LogisticsPipesRework
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block

object ModTags {
    val PIPE_BLOCK: TagKey<Block> = TagKey.create(
        Registries.BLOCK,
        LogisticsPipesRework.asResource("pipe_block")
    )
}
