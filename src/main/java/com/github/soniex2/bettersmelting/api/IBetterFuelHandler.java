package com.github.soniex2.bettersmelting.api;

import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.item.ItemStack;

public interface IBetterFuelHandler extends IFuelHandler {

    // int getBurnTime(ItemStack fuel);

    int getBurnHeat(ItemStack fuel);

    int getBetterBurnTime(ItemStack fuel);

}
