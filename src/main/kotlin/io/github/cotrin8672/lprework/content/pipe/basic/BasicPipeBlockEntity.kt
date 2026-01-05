package io.github.cotrin8672.lprework.content.pipe.basic

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class BasicPipeBlockEntity(
    type: BlockEntityType<BasicPipeBlockEntity>,
    pos: BlockPos,
    blockState: BlockState,

    ) : BlockEntity(type, pos, blockState) {
    
}