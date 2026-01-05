package io.github.cotrin8672.lprework.util

import com.tterrag.registrate.builders.BlockEntityBuilder
import dev.engine_room.flywheel.api.visual.BlockEntityVisual
import dev.engine_room.flywheel.api.visualization.BlockEntityVisualizer
import dev.engine_room.flywheel.api.visualization.VisualizationContext
import dev.engine_room.flywheel.api.visualization.VisualizerRegistry
import dev.engine_room.flywheel.lib.visualization.SimpleBlockEntityVisualizer
import net.minecraft.world.level.block.entity.BlockEntity
import net.neoforged.fml.loading.FMLLoader

fun <T : BlockEntity, P> BlockEntityBuilder<T, P>.visual(
    renderNormally: Boolean,
    factory: (VisualizationContext, T, Float) -> BlockEntityVisual<T>,
): BlockEntityBuilder<T, P> {
    return this.apply {
        onRegister { type ->
            if (FMLLoader.getDist().isClient) {
                val visualizer = object : BlockEntityVisualizer<T> {
                    override fun createVisual(
                        ctx: VisualizationContext,
                        blockEntity: T,
                        partialTick: Float,
                    ): BlockEntityVisual<in T> = factory(ctx, blockEntity, partialTick)

                    override fun skipVanillaRender(blockEntity: T?) = !renderNormally
                }
                VisualizerRegistry.setVisualizer(type, visualizer)
            }
        }
    }
}

fun <T : BlockEntity, P> BlockEntityBuilder<T, P>.visual(
    renderNormally: Boolean,
    factory: SimpleBlockEntityVisualizer.Factory<T>,
): BlockEntityBuilder<T, P> {
    return this.apply {
        onRegister { type ->
            if (FMLLoader.getDist().isClient) {
                val visualizer = object : BlockEntityVisualizer<T> {
                    override fun createVisual(
                        ctx: VisualizationContext,
                        blockEntity: T,
                        partialTick: Float,
                    ): BlockEntityVisual<in T> = factory.create(ctx, blockEntity, partialTick)

                    override fun skipVanillaRender(blockEntity: T?) = !renderNormally
                }
                VisualizerRegistry.setVisualizer(type, visualizer)
            }
        }
    }
}
