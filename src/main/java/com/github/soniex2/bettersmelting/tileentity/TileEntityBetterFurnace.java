package com.github.soniex2.bettersmelting.tileentity;

import com.github.soniex2.bettersmelting.api.IBetterSmeltingRecipe;
import com.github.soniex2.bettersmelting.api.utility.FuelHandlerUtility;
import com.github.soniex2.bettersmelting.api.SmeltingRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
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

    private int burnTime;
    private int burnHeat;
    private int currentItemBurnTime;

    private int smeltTime;
    private int currentItemSmeltTime;

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
        return !(p_94041_1_ == 1 && FuelHandlerUtility.isItemFuel(p_94041_2_));
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("BurnTime", this.burnTime);
        compound.setInteger("CookTime", this.smeltTime);

        NBTTagList inventory = new NBTTagList();

        for (int i = 0; i < this.furnaceItemStacks.length; ++i) {
            if (this.furnaceItemStacks[i] != null) {
                NBTTagCompound slot = new NBTTagCompound();
                slot.setByte("Slot", (byte) i);
                this.furnaceItemStacks[i].writeToNBT(slot);
                inventory.appendTag(slot);
            }
        }

        compound.setTag("Items", inventory);

        if (this.hasCustomInventoryName()) {
            compound.setString("CustomName", this.customName);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        NBTTagList inventory = compound.getTagList("Items", 10);
        this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < inventory.tagCount(); ++i) {
            NBTTagCompound slot = inventory.getCompoundTagAt(i);
            byte b0 = slot.getByte("Slot");

            if (b0 >= 0 && b0 < this.furnaceItemStacks.length) {
                this.furnaceItemStacks[b0] = ItemStack.loadItemStackFromNBT(slot);
            }
        }

        this.burnTime = compound.getShort("BurnTime");
        this.smeltTime = compound.getShort("CookTime");
        this.currentItemBurnTime = FuelHandlerUtility.getFuelStats(this.furnaceItemStacks[1]).burnTime;
        this.burnHeat = FuelHandlerUtility.getFuelStats(this.furnaceItemStacks[1]).burnHeat;
        IBetterSmeltingRecipe recipe = SmeltingRegistry.getRecipe(furnaceItemStacks[0], burnHeat);
        if (recipe != null) {
            this.currentItemSmeltTime = recipe.getSmeltingTime();
        }

        if (compound.hasKey("CustomName", Constants.NBT.TAG_STRING)) {
            this.customName = compound.getString("CustomName");
        }
    }

}
