package io.github.cotrin8672.lprework.registry

import com.tterrag.registrate.util.entry.BlockEntry
import io.github.cotrin8672.lprework.LogisticsPipesRework
import io.github.cotrin8672.lprework.content.pipe.basic.BasicPipe
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.material.MapColor

object ModBlocks {
    val REGISTRATE = LogisticsPipesRework.registrate()

    val BASIC_PIPE: BlockEntry<BasicPipe> = REGISTRATE
        .block("basic_pipe", ::BasicPipe)
        .initialProperties { Blocks.STONE_BRICK_WALL }
        .properties {
            it.mapColor(MapColor.METAL)
                .strength(0.5f)
        }
        .blockstate { c, p ->
            val N = BasicPipe.NORTH
            val E = BasicPipe.EAST
            val S = BasicPipe.SOUTH
            val W = BasicPipe.WEST
            val U = BasicPipe.UP
            val D = BasicPipe.DOWN

            val edgeED = LogisticsPipesRework.asResource("block/pipe/pipe_edge_ed")
            val edgeEU = LogisticsPipesRework.asResource("block/pipe/pipe_edge_eu")
            val edgeND = LogisticsPipesRework.asResource("block/pipe/pipe_edge_nd")
            val edgeNE = LogisticsPipesRework.asResource("block/pipe/pipe_edge_ne")
            val edgeNU = LogisticsPipesRework.asResource("block/pipe/pipe_edge_nu")
            val edgeNW = LogisticsPipesRework.asResource("block/pipe/pipe_edge_nw")
            val edgeSD = LogisticsPipesRework.asResource("block/pipe/pipe_edge_sd")
            val edgeSE = LogisticsPipesRework.asResource("block/pipe/pipe_edge_se")
            val edgeSU = LogisticsPipesRework.asResource("block/pipe/pipe_edge_su")
            val edgeSW = LogisticsPipesRework.asResource("block/pipe/pipe_edge_sw")
            val edgeWD = LogisticsPipesRework.asResource("block/pipe/pipe_edge_wd")
            val edgeWU = LogisticsPipesRework.asResource("block/pipe/pipe_edge_wu")

            val connectorN = LogisticsPipesRework.asResource("block/pipe/connector/north")
            val connectorE = LogisticsPipesRework.asResource("block/pipe/connector/east")
            val connectorS = LogisticsPipesRework.asResource("block/pipe/connector/south")
            val connectorW = LogisticsPipesRework.asResource("block/pipe/connector/west")
            val connectorU = LogisticsPipesRework.asResource("block/pipe/connector/up")
            val connectorD = LogisticsPipesRework.asResource("block/pipe/connector/down")

            val b = p.getMultipartBuilder(c.get())

            b
                .part()
                .modelFile(p.models().getExistingFile(edgeNU))
                .addModel()
                .condition(N, false)
                .condition(U, false)
                .end()

            b.part()
                .modelFile(p.models().getExistingFile(edgeNE))
                .addModel()
                .condition(N, false)
                .condition(E, false)
                .end()

            b.part()
                .modelFile(p.models().getExistingFile(edgeEU))
                .addModel()
                .condition(E, false)
                .condition(U, false)
                .end()

            b.part()
                .modelFile(p.models().getExistingFile(edgeND))
                .addModel()
                .condition(N, false)
                .condition(D, false)
                .end()

            b.part()
                .modelFile(p.models().getExistingFile(edgeNW))
                .addModel()
                .condition(N, false)
                .condition(W, false)
                .end()

            b.part()
                .modelFile(p.models().getExistingFile(edgeED))
                .addModel()
                .condition(E, false)
                .condition(D, false)
                .end()

            b.part()
                .modelFile(p.models().getExistingFile(edgeSD))
                .addModel()
                .condition(S, false)
                .condition(D, false)
                .end()

            b.part()
                .modelFile(p.models().getExistingFile(edgeSE))
                .addModel()
                .condition(S, false)
                .condition(E, false)
                .end()

            b.part()
                .modelFile(p.models().getExistingFile(edgeSU))
                .addModel()
                .condition(S, false)
                .condition(U, false)
                .end()

            b.part()
                .modelFile(p.models().getExistingFile(edgeSW))
                .addModel()
                .condition(S, false)
                .condition(W, false)
                .end()

            b.part()
                .modelFile(p.models().getExistingFile(edgeWD))
                .addModel()
                .condition(W, false)
                .condition(D, false)
                .end()

            b.part()
                .modelFile(p.models().getExistingFile(edgeWU))
                .addModel()
                .condition(W, false)
                .condition(U, false)
                .end()

            b.part()
                .modelFile(p.models().getExistingFile(connectorN))
                .addModel()
                .condition(N, true)
                .end()

            b.part()
                .modelFile(p.models().getExistingFile(connectorE))
                .addModel()
                .condition(E, true)
                .end()

            b.part()
                .modelFile(p.models().getExistingFile(connectorS))
                .addModel()
                .condition(S, true)
                .end()

            b.part()
                .modelFile(p.models().getExistingFile(connectorW))
                .addModel()
                .condition(W, true)
                .end()

            b.part()
                .modelFile(p.models().getExistingFile(connectorU))
                .addModel()
                .condition(U, true)
                .end()

            b.part()
                .modelFile(p.models().getExistingFile(connectorD))
                .addModel()
                .condition(D, true)
                .end()
        }
        .tag(ModTags.PIPE_BLOCK)
        .simpleItem()
        .register()

    fun register() {}
}
