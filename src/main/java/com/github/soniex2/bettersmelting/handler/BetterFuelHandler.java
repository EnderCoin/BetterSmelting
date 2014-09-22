package com.github.soniex2.bettersmelting.handler;

import com.github.soniex2.bettersmelting.api.IBetterFuelHandler;
import com.github.soniex2.bettersmelting.reference.Reference;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.Optional;
import net.minecraft.item.ItemStack;

@Optional.Interface(iface = "com.github.soniex2.bettersmelting.api.IBetterFuelHandler", modid = Reference.MOD_ID)
public class BetterFuelHandler implements IFuelHandler, IBetterFuelHandler {
    @Override
    @Optional.Method(modid = Reference.MOD_ID)
    public int getBurnHeat(ItemStack fuel) {
        return 0;
    }

    @Override
    @Optional.Method(modid = Reference.MOD_ID)
    public int getBetterBurnTime(ItemStack fuel) {
        return 0;
    }

    @Override
    public int getBurnTime(ItemStack fuel) {
        return 0;
    }
}
