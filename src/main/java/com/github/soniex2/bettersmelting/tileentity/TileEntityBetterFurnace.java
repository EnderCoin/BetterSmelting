package com.github.soniex2.bettersmelting.tileentity;

import com.github.soniex2.bettersmelting.api.utility.FuelHandlerUtility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityBetterFurnace extends TileEntityBS implements ISidedInventory {

    private static final int[] slotsTop = new int[]{0};
    private static final int[] slotsBottom = new int[]{2, 1};
    private static final int[] slotsSides = new int[]{1};
    /**
     * The ItemStacks that hold the items currently being used in the furnace
     */
    // 0 = input, 1 = fuel, 2 = output
    private ItemStack[] furnaceItemStacks = new ItemStack[3];

    private String customName;

    @Override
    public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
        // convert to ForgeDirection
        ForgeDirection side = ForgeDirection.getOrientation(p_94128_1_);
        switch (side) {
            case DOWN:
                return slotsBottom;
            case UP:
                return slotsTop;
            default:
                return slotsSides;
        }
    }

    @Override
    public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
        return this.isItemValidForSlot(p_102007_1_, p_102007_2_);
    }

    @Override
    public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
        return p_102008_3_ != 0 || p_102008_1_ != 1 || p_102008_2_.getItem() == Items.bucket;
    }

    @Override
    public int getSizeInventory() {
        return furnaceItemStacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int p_70301_1_) {
        return furnaceItemStacks[p_70301_1_];
    }

    @Override
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
        if (this.furnaceItemStacks[p_70298_1_] != null) {
            ItemStack itemstack;

            if (this.furnaceItemStacks[p_70298_1_].stackSize <= p_70298_2_) {
                itemstack = this.furnaceItemStacks[p_70298_1_];
                this.furnaceItemStacks[p_70298_1_] = null;
                return itemstack;
            } else {
                itemstack = this.furnaceItemStacks[p_70298_1_].splitStack(p_70298_2_);

                if (this.furnaceItemStacks[p_70298_1_].stackSize == 0) {
                    this.furnaceItemStacks[p_70298_1_] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        // Don't check for null if we don't have to
        ItemStack itemstack = this.furnaceItemStacks[p_70304_1_];
        this.furnaceItemStacks[p_70304_1_] = null;
        return itemstack;
    }

    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
        this.furnaceItemStacks[p_70299_1_] = p_70299_2_;

        if (p_70299_2_ != null && p_70299_2_.stackSize > this.getInventoryStackLimit()) {
            p_70299_2_.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName() {
        return hasCustomInventoryName() ? customName : "container.furnace";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return customName != null && customName.length() > 0;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return (this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this) && (p_70300_1_.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D);
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return !(p_94041_1_ == 1 && FuelHandlerUtility.getFuelStats(p_94041_2_).burnTime <= 0);
    }


}
