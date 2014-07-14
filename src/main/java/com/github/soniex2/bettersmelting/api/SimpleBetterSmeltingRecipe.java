package com.github.soniex2.bettersmelting.api;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SimpleBetterSmeltingRecipe implements IBetterSmeltingRecipe {

    private final ItemStack input;
    private final ItemStack output;
    private final float experience;

    // cache `input instanceof ItemBlock` because instanceof checks are expensive
    private final boolean isBlockRecipe;

    public SimpleBetterSmeltingRecipe(ItemStack input, ItemStack output, float experience) {
        this.input = input;
        this.output = output;
        this.experience = experience;
        this.isBlockRecipe = input.getItem() instanceof ItemBlock;
    }

    @Override
    public ItemStack getRecipeInput() {
        return input;
    }

    @Override
    public ItemStack getRecipeOutput(ItemStack input) {
        return output;
    }

    @Override
    public int getSmeltingTime() {
        return 200;
    }

    @Override
    public int getMinimumHeat() {
        return 0;
    }

    @Override
    public int getMaximumHeat() {
        return Integer.MAX_VALUE;
    }

    @Override
    public float getExperience() {
        return experience;
    }

    @Override
    public boolean nbtMatches(ItemStack is) {
        return true; // we don't care about NBT, eh?
    }

    @Override
    public boolean damageMatches(ItemStack is) {
        if (isBlockRecipe) {
            if (input.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
                return true; // we don't care about input's damage
            }
        }
        return input.getItemDamage() == input.getItemDamage();
    }
}
