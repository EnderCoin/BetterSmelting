package com.github.soniex2.bettersmelting.api;

import com.github.soniex2.bettersmelting.api.IBetterSmeltingRecipe;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class SmeltingRegistry {

    private static List<IBetterSmeltingRecipe> recipes = Lists.newArrayList();

    public static void registerRecipe(IBetterSmeltingRecipe recipe) {
        recipes.add(recipe);
    }

    public static IBetterSmeltingRecipe getRecipe(ItemStack input, int heat) {
        if (input == null) return null;
        IBetterSmeltingRecipe recipe = null;
        for (IBetterSmeltingRecipe recipe1 : recipes) {
            ItemStack recipeInput = recipe1.getRecipeInput();
            // compare items
            if (recipeInput.getItem() == input.getItem() && recipe1.damageMatches(input.getItemDamage())) {
                // compare temperature
                if (recipe1.getMaximumHeat() > heat
                        && recipe1.getMinimumHeat() < heat) {
                    // multi-item furnace recipe?
                    if (input.stackSize >= recipeInput.stackSize) {
                        // compare NBT
                        if (recipe1.nbtMatches(input)) {
                            // in BS, recipes that were added later override recipes that were added earlier.
                            // no idea about vanilla behaviour...
                            recipe = recipe1;
                        }
                    }
                }
            }
        }
        return recipe;
    }

}
