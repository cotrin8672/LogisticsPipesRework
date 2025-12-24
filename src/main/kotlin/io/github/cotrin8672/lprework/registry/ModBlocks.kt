package io.github.cotrin8672.lprework.registry

import io.github.cotrin8672.lprework.LogisticsPipesRework
import io.github.cotrin8672.lprework.content.pipe.basic.BasicPipe
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.material.MapColor

object ModBlocks {
    val REGISTRATE = LogisticsPipesRework.registrate()

    val BASIC_PIPE = REGISTRATE
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

            val edgeED = LogisticsPipesRework.of("block/pipe/pipe_edge_ed")
            val edgeEU = LogisticsPipesRework.of("block/pipe/pipe_edge_eu")
            val edgeND = LogisticsPipesRework.of("block/pipe/pipe_edge_nd")
            val edgeNE = LogisticsPipesRework.of("block/pipe/pipe_edge_ne")
            val edgeNU = LogisticsPipesRework.of("block/pipe/pipe_edge_nu")
            val edgeNW = LogisticsPipesRework.of("block/pipe/pipe_edge_nw")
            val edgeSD = LogisticsPipesRework.of("block/pipe/pipe_edge_sd")
            val edgeSE = LogisticsPipesRework.of("block/pipe/pipe_edge_se")
            val edgeSU = LogisticsPipesRework.of("block/pipe/pipe_edge_su")
            val edgeSW = LogisticsPipesRework.of("block/pipe/pipe_edge_sw")
            val edgeWD = LogisticsPipesRework.of("block/pipe/pipe_edge_wd")
            val edgeWU = LogisticsPipesRework.of("block/pipe/pipe_edge_wu")

            val connectorN = LogisticsPipesRework.of("block/pipe/connector/north")
            val connectorE = LogisticsPipesRework.of("block/pipe/connector/east")
            val connectorS = LogisticsPipesRework.of("block/pipe/connector/south")
            val connectorW = LogisticsPipesRework.of("block/pipe/connector/west")
            val connectorU = LogisticsPipesRework.of("block/pipe/connector/up")
            val connectorD = LogisticsPipesRework.of("block/pipe/connector/down")

            // Corner models for 1 connection
            val corner1NWU2N = LogisticsPipesRework.of("block/pipe/corner/1/nwu2n")
            val corner1NWU2U = LogisticsPipesRework.of("block/pipe/corner/1/nwu2u")
            val corner1NWD2N = LogisticsPipesRework.of("block/pipe/corner/1/nwd2n")
            val corner1NEU2N = LogisticsPipesRework.of("block/pipe/corner/1/neu2n")
            val corner1NED2N = LogisticsPipesRework.of("block/pipe/corner/1/ned2n")
            val corner1SWU2U = LogisticsPipesRework.of("block/pipe/corner/1/swu2u")
            val corner1SEU2U = LogisticsPipesRework.of("block/pipe/corner/1/seu2u")
            val corner1NEU2U = LogisticsPipesRework.of("block/pipe/corner/1/neu2u")
            val corner1NWU2W = LogisticsPipesRework.of("block/pipe/corner/1/nwu2w")
            val corner1NWD2W = LogisticsPipesRework.of("block/pipe/corner/1/nwd2w")
            val corner1SWD2W = LogisticsPipesRework.of("block/pipe/corner/1/swd2w")
            val corner1SWU2W = LogisticsPipesRework.of("block/pipe/corner/1/swu2w")

            val cornerNED = LogisticsPipesRework.of("block/pipe/corner/0/ned")
            val cornerNEU = LogisticsPipesRework.of("block/pipe/corner/0/neu")
            val cornerNWD = LogisticsPipesRework.of("block/pipe/corner/0/nwd")
            val cornerNWU = LogisticsPipesRework.of("block/pipe/corner/0/nwu")
            val cornerSED = LogisticsPipesRework.of("block/pipe/corner/0/sed")
            val cornerSEU = LogisticsPipesRework.of("block/pipe/corner/0/seu")
            val cornerSWD = LogisticsPipesRework.of("block/pipe/corner/0/swd")
            val cornerSWU = LogisticsPipesRework.of("block/pipe/corner/0/swu")

            val corner2SEU = LogisticsPipesRework.of("block/pipe/corner/2/seu")

            val b = p.getMultipartBuilder(c.get())

            b
                .part()
                .modelFile(p.models().getExistingFile(corner1SWD2W))
                .addModel()
                .condition(S, false)
                .condition(W, true)
                .condition(D, false)
                .end()

            b
                .part()
                .modelFile(p.models().getExistingFile(corner1SWU2W))
                .addModel()
                .condition(S, false)
                .condition(W, true)
                .condition(U, false)
                .end()

            b
                .part()
                .modelFile(p.models().getExistingFile(corner1NWU2N))
                .addModel()
                .condition(N, true)
                .condition(W, false)
                .condition(U, false)
                .end()

            b
                .part()
                .modelFile(p.models().getExistingFile(corner1NWU2W))
                .addModel()
                .condition(N, false)
                .condition(W, true)
                .condition(U, false)
                .end()

            b
                .part()
                .modelFile(p.models().getExistingFile(corner1NWU2U))
                .addModel()
                .condition(N, false)
                .condition(W, false)
                .condition(U, true)
                .end()

            b
                .part()
                .modelFile(p.models().getExistingFile(corner1SWU2U))
                .addModel()
                .condition(S, false)
                .condition(W, false)
                .condition(U, true)
                .end()

            b
                .part()
                .modelFile(p.models().getExistingFile(corner1SEU2U))
                .addModel()
                .condition(S, false)
                .condition(E, false)
                .condition(U, true)
                .end()

            b
                .part()
                .modelFile(p.models().getExistingFile(corner1NWD2N))
                .addModel()
                .condition(N, true)
                .condition(W, false)
                .condition(D, false)
                .end()

            b
                .part()
                .modelFile(p.models().getExistingFile(corner1NWD2W))
                .addModel()
                .condition(N, false)
                .condition(W, true)
                .condition(D, false)
                .end()

            b
                .part()
                .modelFile(p.models().getExistingFile(corner1NEU2N))
                .addModel()
                .condition(N, true)
                .condition(E, false)
                .condition(U, false)
                .end()

            b
                .part()
                .modelFile(p.models().getExistingFile(corner1NED2N))
                .addModel()
                .condition(N, true)
                .condition(E, false)
                .condition(D, false)
                .end()

            b
                .part()
                .modelFile(p.models().getExistingFile(corner1NEU2U))
                .addModel()
                .condition(N, false)
                .condition(E, false)
                .condition(U, true)
                .end()


            b
                .part()
                .modelFile(p.models().getExistingFile(corner2SEU))
                .addModel()
                .condition(S, true)
                .condition(E, true)
                .condition(U, false)
                .end()

            b
                .part()
                .modelFile(p.models().getExistingFile(cornerNED))
                .addModel()
                .condition(N, false)
                .condition(E, false)
                .condition(D, false)
                .end()

            b
                .part()
                .modelFile(p.models().getExistingFile(cornerNEU))
                .addModel()
                .condition(N, false)
                .condition(E, false)
                .condition(U, false)
                .end()

            b
                .part()
                .modelFile(p.models().getExistingFile(cornerNWD))
                .addModel()
                .condition(N, false)
                .condition(W, false)
                .condition(D, false)
                .end()

            b
                .part()
                .modelFile(p.models().getExistingFile(cornerNWU))
                .addModel()
                .condition(N, false)
                .condition(W, false)
                .condition(U, false)
                .end()

            b
                .part()
                .modelFile(p.models().getExistingFile(cornerSED))
                .addModel()
                .condition(S, false)
                .condition(E, false)
                .condition(D, false)
                .end()

            b
                .part()
                .modelFile(p.models().getExistingFile(cornerSEU))
                .addModel()
                .condition(S, false)
                .condition(E, false)
                .condition(U, false)
                .end()

            b
                .part()
                .modelFile(p.models().getExistingFile(cornerSWD))
                .addModel()
                .condition(S, false)
                .condition(W, false)
                .condition(D, false)
                .end()

            b
                .part()
                .modelFile(p.models().getExistingFile(cornerSWU))
                .addModel()
                .condition(S, false)
                .condition(W, false)
                .condition(U, false)
                .end()

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
