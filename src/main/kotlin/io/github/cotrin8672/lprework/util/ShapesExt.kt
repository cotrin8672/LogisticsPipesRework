package io.github.cotrin8672.lprework.util

import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

fun VoxelShape.orIf(shape: VoxelShape, condition: Boolean): VoxelShape {
    return if (condition) {
        Shapes.or(shape, this)
    } else {
        this
    }
}
