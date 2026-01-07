package io.github.cotrin8672.lprework.content.pipe.basic

import dev.engine_room.flywheel.api.instance.Instance
import dev.engine_room.flywheel.api.visualization.VisualizationContext
import dev.engine_room.flywheel.lib.instance.InstanceTypes
import dev.engine_room.flywheel.lib.instance.OrientedInstance
import dev.engine_room.flywheel.lib.model.Models
import dev.engine_room.flywheel.lib.visual.AbstractBlockEntityVisual
import dev.engine_room.flywheel.lib.visualization.SimpleBlockEntityVisualizer
import io.github.cotrin8672.lprework.registry.ModPartialModels
import net.minecraft.core.Direction
import net.minecraft.world.level.block.state.BlockState
import java.util.function.Consumer

class BasicPipeVisual(
    ctx: VisualizationContext,
    blockEntity: BasicPipeBlockEntity,
    partialTick: Float,
) : AbstractBlockEntityVisual<BasicPipeBlockEntity>(ctx, blockEntity, partialTick) {
    private val model0 = Models.partial(ModPartialModels.CORNER0)
    private val model1 = Models.partial(ModPartialModels.CORNER1)
    private val model2 = Models.partial(ModPartialModels.CORNER2)
    private val model3 = Models.partial(ModPartialModels.CORNER3)
    private val instancer0 = instancerProvider().instancer(InstanceTypes.ORIENTED, model0)
    private val instancer1 = instancerProvider().instancer(InstanceTypes.ORIENTED, model1)
    private val instancer2 = instancerProvider().instancer(InstanceTypes.ORIENTED, model2)
    private val instancer3 = instancerProvider().instancer(InstanceTypes.ORIENTED, model3)
    private val instanceList = mutableListOf<OrientedInstance>()

    init {
        val nslist = arrayListOf(Direction.NORTH, Direction.SOUTH)
        val ewlist = arrayListOf(Direction.EAST, Direction.WEST)
        val udlist = arrayListOf(Direction.UP, Direction.DOWN)

        for (ns in nslist) for (ew in ewlist) for (ud in udlist) {
            // if (ns != Direction.NORTH || ew != Direction.EAST || ud != Direction.UP) continue
            val instance = createCornerInstance(ns, ew, ud)
            instance.setChanged()
            instanceList.add(instance)
        }
    }

    companion object {
        fun of(): SimpleBlockEntityVisualizer.Factory<BasicPipeBlockEntity> {
            return SimpleBlockEntityVisualizer.Factory { context, blockEntity, partialTick ->
                BasicPipeVisual(context, blockEntity, partialTick)
            }
        }
    }

    override fun update(partialTick: Float) {
        super.update(partialTick)
    }

    override fun _delete() {
        instanceList.forEach {
            it.delete()
        }
    }

    override fun collectCrumblingInstances(consumer: Consumer<Instance?>) {
        instanceList.forEach(consumer::accept)
    }

    override fun updateLight(partialTick: Float) {
        instanceList.forEach(::relight)
    }

    enum class Corner(val ns: Direction, val ew: Direction, val ud: Direction) {
        NEU(Direction.NORTH, Direction.EAST, Direction.UP),
        NWU(Direction.NORTH, Direction.WEST, Direction.UP),
        SEU(Direction.SOUTH, Direction.EAST, Direction.UP),
        SWU(Direction.SOUTH, Direction.WEST, Direction.UP),
        NED(Direction.NORTH, Direction.EAST, Direction.DOWN),
        NWD(Direction.NORTH, Direction.WEST, Direction.DOWN),
        SED(Direction.SOUTH, Direction.EAST, Direction.DOWN),
        SWD(Direction.SOUTH, Direction.WEST, Direction.DOWN);

        fun mask(state: BlockState): Int {
            var m = 0
            if (state.getValue(BasicPipe.getPropByDirection(ns))) m = m or 1 // bit0
            if (state.getValue(BasicPipe.getPropByDirection(ew))) m = m or 2 // bit1
            if (state.getValue(BasicPipe.getPropByDirection(ud))) m = m or 4 // bit2
            return m
        }

        fun count(state: BlockState) = Integer.bitCount(mask(state))
    }

    private fun createCornerInstance(ns: Direction, ew: Direction, ud: Direction): OrientedInstance {
        val cns = blockEntity.blockState.getValue(BasicPipe.getPropByDirection(ns))
        val cew = blockEntity.blockState.getValue(BasicPipe.getPropByDirection(ew))
        val cud = blockEntity.blockState.getValue(BasicPipe.getPropByDirection(ud))

        val count = (if (cns) 1 else 0) + (if (cew) 1 else 0) + (if (cud) 1 else 0)
        val instance = when (count) {
            0 -> instancer0.createInstance()
            1 -> instancer1.createInstance()
            2 -> instancer2.createInstance()
            3 -> instancer3.createInstance()
            else -> throw IllegalStateException("cannot create instance of $count")
        }
        val rotY = when {
            ns == Direction.SOUTH && ew == Direction.EAST -> 90f
            ns == Direction.SOUTH && ew == Direction.WEST -> 180f
            ns == Direction.NORTH && ew == Direction.WEST -> 270f
            else -> 0f
        } - if (ud == Direction.DOWN) 90f else 0f
        val rotX = if (ud == Direction.UP) 0f else 180f

        instance.position(visualPosition)

        if (count == 1) {
            if (cud) {
                if (ud == Direction.UP) {
                    if (ns == Direction.NORTH) {
                        if (ew == Direction.EAST) {
                            instance.rotateDegrees(-90f, Direction.Axis.X)
                            instance.translatePosition(0f, 0.5f, 0f)
                        } else {
                            instance.rotateDegrees(90f, Direction.Axis.Z)
                            instance.translatePosition(0f, 0.5f, 0f)
                        }
                    } else if (ns == Direction.SOUTH) {
                        if (ew == Direction.EAST) {
                            instance.rotateDegrees(-90f, Direction.Axis.Z)
                            instance.translatePosition(0f, 0.5f, 0f)
                        } else {
                            instance.rotateDegrees(90f, Direction.Axis.X)
                            instance.translatePosition(0f, 0.5f, 0f)
                        }
                    }
                } else {
                    if (ns == Direction.NORTH) {
                        if (ew == Direction.EAST) {
                            instance.rotateDegrees(90f, Direction.Axis.Z)
                            instance.translatePosition(0f, -0.5f, 0f)
                        } else {
                            instance.rotateDegrees(90f, Direction.Axis.X)
                            instance.translatePosition(0f, -0.5f, 0f)
                        }
                    } else if (ns == Direction.SOUTH) {
                        if (ew == Direction.EAST) {
                            instance.rotateDegrees(-90f, Direction.Axis.X)
                            instance.translatePosition(0f, -0.5f, 0f)
                        } else {
                            instance.rotateDegrees(-90f, Direction.Axis.Z)
                            instance.translatePosition(0f, -0.5f, 0f)
                        }
                    }
                }
            } else if (cns) {
                if (ns == Direction.NORTH) {
                    if (ew == Direction.EAST && ud == Direction.DOWN) {
                        instance.rotateDegrees(-90f, Direction.Axis.Y)
                        instance.translatePosition(0f, 0f, -0.5f)
                    } else if (ew == Direction.WEST && ud == Direction.UP) {
                        instance.rotateDegrees(90f, Direction.Axis.Y)
                        instance.translatePosition(0f, 0f, -0.5f)
                    }

                } else {
                    if (ew == Direction.EAST && ud == Direction.UP) {
                        instance.rotateDegrees(90f, Direction.Axis.Y)
                        instance.translatePosition(0f, 0f, 0.5f)
                    } else if (ew == Direction.WEST && ud == Direction.DOWN) {
                        instance.rotateDegrees(-90f, Direction.Axis.Y)
                        instance.translatePosition(0f, 0f, 0.5f)
                    }
                }
            } else if (cew) {
                if (ew == Direction.EAST) {
                    if (ns == Direction.NORTH && ud == Direction.UP) {
                        instance.rotateDegrees(90f, Direction.Axis.Y)
                        instance.translatePosition(0.5f, 0f, 0f)
                    } else if (ns == Direction.SOUTH && ud == Direction.DOWN) {
                        instance.rotateDegrees(-90f, Direction.Axis.Y)
                        instance.translatePosition(0.5f, 0f, 0f)
                    }
                } else {
                    if (ns == Direction.NORTH && ud == Direction.DOWN) {
                        instance.rotateDegrees(-90f, Direction.Axis.Y)
                        instance.translatePosition(-0.5f, 0f, 0f)
                    } else if (ns == Direction.SOUTH && ud == Direction.UP) {
                        instance.rotateDegrees(90f, Direction.Axis.Y)
                        instance.translatePosition(-0.5f, 0f, 0f)
                    }
                }
            }
        }

        instance.rotateYDegrees(-rotY)
        instance.rotateXDegrees(rotX)
        return instance
    }
}
