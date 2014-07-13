package com.github.soniex2.bettersmelting.init;

import com.github.soniex2.bettersmelting.block.BlockBS;
import com.github.soniex2.bettersmelting.block.BlockBetterFurnace;
import com.github.soniex2.bettersmelting.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {

    public static final Block furnace = new BlockBetterFurnace(false);
    public static final Block litFurnace = new BlockBetterFurnace(true);

    public static void init() {
        GameRegistry.registerBlock(furnace, "furnace");
        GameRegistry.registerBlock(litFurnace, "lit_furnace");
    }
}
