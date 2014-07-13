package com.github.soniex2.bettersmelting.block;

import com.github.soniex2.bettersmelting.creativetab.CreativeTabBS;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBS extends Block {

    protected BlockBS(Material material) {
        super(material);
        this.setCreativeTab(CreativeTabBS.BS_TAB);
    }
}
