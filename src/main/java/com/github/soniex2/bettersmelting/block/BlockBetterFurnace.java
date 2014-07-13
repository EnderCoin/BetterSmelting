package com.github.soniex2.bettersmelting.block;

import com.github.soniex2.bettersmelting.creativetab.CreativeTabBS;
import com.github.soniex2.bettersmelting.reference.Reference;
import com.github.soniex2.bettersmelting.tileentity.TileEntityBetterFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockBetterFurnace extends BlockFurnace implements ITileEntityProvider {

    public BlockBetterFurnace(boolean lit) {
        super(lit);
        this.setBlockName(Reference.MOD_ID + ".furnace");
        this.setStepSound(Block.soundTypePiston);
        this.setHardness(3.5F);
        this.setCreativeTab(CreativeTabBS.BS_TAB);
        if (lit) {
            this.setLightLevel(0.875F);
            this.setCreativeTab(null);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityBetterFurnace();
    }
}
