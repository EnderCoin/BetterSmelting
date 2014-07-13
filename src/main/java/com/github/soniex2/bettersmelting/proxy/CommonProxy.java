package com.github.soniex2.bettersmelting.proxy;

import com.github.soniex2.bettersmelting.tileentity.TileEntityBetterFurnace;
import cpw.mods.fml.common.registry.GameRegistry;

public abstract class CommonProxy implements IProxy {

    @Override
    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityBetterFurnace.class, "tile.bettersmelting.furnace");
    }
}
