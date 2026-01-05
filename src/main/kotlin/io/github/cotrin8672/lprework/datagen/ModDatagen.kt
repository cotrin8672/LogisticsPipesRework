package io.github.cotrin8672.lprework.datagen

import io.github.cotrin8672.lprework.LogisticsPipesRework
import io.github.cotrin8672.lprework.registry.ModBlocks
import net.minecraft.data.PackOutput
import net.neoforged.neoforge.client.model.generators.BlockStateProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper
import net.neoforged.neoforge.data.event.GatherDataEvent

object ModDatagen {
    @JvmStatic
    fun gatherData(event: GatherDataEvent) {
        val generator = event.generator
        if (event.includeClient()) {
            generator.addProvider(true, ModBlockStates(generator.packOutput, event.existingFileHelper))
        }
    }

    private class ModBlockStates(output: PackOutput, exFileHelper: ExistingFileHelper) :
        BlockStateProvider(output, LogisticsPipesRework.ID, exFileHelper) {
        override fun registerStatesAndModels() {
            simpleBlock(
                ModBlocks.BASIC_PIPE.get(),
                models().getExistingFile(LogisticsPipesRework.asResource("block/basic_pipe"))
            )
        }
    }
}
