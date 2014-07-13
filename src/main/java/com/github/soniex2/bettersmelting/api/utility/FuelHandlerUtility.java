package com.github.soniex2.bettersmelting.api.utility;

import com.github.soniex2.bettersmelting.api.IBetterFuelHandler;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Field;
import java.util.List;

public class FuelHandlerUtility {
    private static List<IFuelHandler> fuelHandlers;

    public static class BetterFuel {

        public final int burnTime, burnHeat;

        private BetterFuel(int burnTime, int burnHeat) {
            this.burnTime = burnTime;
            this.burnHeat = burnHeat;
        }

        private BetterFuel(int burnTime) {
            this(burnTime, 1000);
        }
    }

    private static void init() {
        try {
            Class clazz = GameRegistry.class;
            Field field = clazz.getDeclaredField("fuelHandlers");
            field.setAccessible(true);
            fuelHandlers = (List<IFuelHandler>) field.get(null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static BetterFuel getFuelStats(ItemStack fuel) {
        if (fuelHandlers == null) {
            init();
        }
        boolean isVanilla = true;
        int burnTime = 0;
        int burnHeat = 0;
        for (IFuelHandler handler : fuelHandlers) {
            if (handler instanceof IBetterFuelHandler) {
                int m = ((IBetterFuelHandler) handler).getBetterBurnTime(fuel);
                int n = ((IBetterFuelHandler) handler).getBurnHeat(fuel);
                if (m > 0 && n > 0) {
                    if (isVanilla) {
                        isVanilla = false;
                    }
                    burnTime = m;
                    burnHeat = n;
                }
            } else if (isVanilla) { // IBetterFuelHandler takes precedence over IFuelHandler
                // Consistency with vanilla
                // If I could I would replace this so the latest handler takes precedence...
                burnTime = Math.max(burnTime, handler.getBurnTime(fuel));
            }
        }
        if (isVanilla) {
            return new BetterFuel(burnTime);
        }
        return new BetterFuel(burnTime, burnHeat);
    }
}
