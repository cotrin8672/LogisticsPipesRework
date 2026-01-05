package io.github.cotrin8672.lprework.registry

import dev.engine_room.flywheel.lib.model.baked.PartialModel
import io.github.cotrin8672.lprework.LogisticsPipesRework

object ModPartialModels {
    val CORNER0: PartialModel = PartialModel.of(LogisticsPipesRework.asResource("block/pipe/corner/0"))
    val CORNER1: PartialModel = PartialModel.of(LogisticsPipesRework.asResource("block/pipe/corner/1"))
    val CORNER2: PartialModel = PartialModel.of(LogisticsPipesRework.asResource("block/pipe/corner/2"))
    val CORNER3: PartialModel = PartialModel.of(LogisticsPipesRework.asResource("block/pipe/corner/3"))

    fun register() {}
}
