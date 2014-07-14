package com.github.soniex2.bettersmelting.api;

import net.minecraft.item.ItemStack;

public interface IBetterSmeltingRecipe {

    /**
     * Returns the input for this recipe. Note: The stackSize matters!
     *
     * @return The input for this recipe.
     */
    ItemStack getRecipeInput();

    /**
     * Returns the output for this recipe. Input-sensitive for use with {@link com.github.soniex2.bettersmelting.api.IBetterSmeltingRecipe#nbtMatches(net.minecraft.item.ItemStack)}
     *
     * @param input The item being smelted.
     * @return The output for this recipe.
     */
    ItemStack getRecipeOutput(ItemStack input);

    int getSmeltingTime();

    int getMinimumHeat();

    int getMaximumHeat();

    float getExperience();

    /**
     * Checks to see if the passed {@link net.minecraft.item.ItemStack} can be used on this recipe, based on its NBT values.
     *
     * @param is The ItemStack
     * @return {@literal true} if the passed {@code ItemStack} can be used on this recipe.
     */
    boolean nbtMatches(ItemStack is);

    /**
     * Checks to see if the passed {@link net.minecraft.item.ItemStack} can be used on this recipe, based on its damage.
     *
     * @param is The ItemStack
     * @return {@literal true} if the passed {@code ItemStack} can be used on this recipe.
     */
    // We have this because I don't like Minecraft's "wildcard damage" system.
    boolean damageMatches(ItemStack is);

}
