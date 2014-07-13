package com.github.soniex2.bettersmelting.creativetab;

import com.github.soniex2.bettersmelting.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CreativeTabBS {

    public static final CreativeTabs BS_TAB = new CreativeTabs(Reference.MOD_ID) {
        @Override
        public Item getTabIconItem() {
            return Items.coal;
        }
    };
}
