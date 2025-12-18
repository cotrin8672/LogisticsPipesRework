package io.github.cotrin8672.lprework.content.pipe.basic

import io.github.cotrin8672.lprework.registry.ModTags
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class BasicPipe(properties: Properties) : Block(properties) {
    companion object {
        val UP: BooleanProperty = BooleanProperty.create("up")
        val DOWN: BooleanProperty = BooleanProperty.create("down")
        val NORTH: BooleanProperty = BooleanProperty.create("north")
        val EAST: BooleanProperty = BooleanProperty.create("east")
        val SOUTH: BooleanProperty = BooleanProperty.create("south")
        val WEST: BooleanProperty = BooleanProperty.create("west")
        val WATERLOGGED: BooleanProperty = BlockStateProperties.WATERLOGGED

        private val CENTER_SHAPE: VoxelShape = box(3.0, 3.0, 3.0, 13.0, 13.0, 13.0)
    }

    init {
        registerDefaultState(
            stateDefinition
                .any()
                .setValue(UP, false)
                .setValue(DOWN, false)
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(WATERLOGGED, false)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block?, BlockState?>) {
        super.createBlockStateDefinition(builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST, WATERLOGGED))
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        val state = super.getStateForPlacement(context) ?: defaultBlockState()
        return updateConnections(state, context.level, context.clickedPos)
    }

    override fun updateShape(
        state: BlockState,
        direction: Direction,
        neighborState: BlockState,
        level: LevelAccessor,
        pos: BlockPos,
        neighborPos: BlockPos,
    ): BlockState {
        val updated = super.updateShape(state, direction, neighborState, level, pos, neighborPos)
        return updateConnections(updated, level, pos)
    }

    private fun updateConnections(state: BlockState, level: LevelAccessor, pos: BlockPos): BlockState {
        var s = state

        fun connected(dir: Direction) = level.getBlockState(pos.relative(dir)).`is`(ModTags.PIPE_BLOCK)

        s = s.setValue(UP, connected(Direction.UP))
        s = s.setValue(DOWN, connected(Direction.DOWN))
        s = s.setValue(NORTH, connected(Direction.NORTH))
        s = s.setValue(EAST, connected(Direction.EAST))
        s = s.setValue(SOUTH, connected(Direction.SOUTH))
        s = s.setValue(WEST, connected(Direction.WEST))

        return s
    }

    override fun getShape(
        state: BlockState,
        level: BlockGetter,
        pos: BlockPos,
        context: CollisionContext,
    ): VoxelShape = CENTER_SHAPE

    override fun getCollisionShape(
        state: BlockState,
        level: BlockGetter,
        pos: BlockPos,
        context: CollisionContext,
    ): VoxelShape = CENTER_SHAPE

}
