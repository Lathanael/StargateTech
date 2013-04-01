package lordfokas.stargatetech.machine;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import lordfokas.stargatetech.StargateTech;
import lordfokas.stargatetech.networks.power.BasePowerNetGeneratorTE;

public class NaquadahGeneratorTE extends BasePowerNetGeneratorTE implements ISidedInventory{
	public static final String ID = "NaquadahGeneratorTE";
	
	/** How much power is produced every tick */
	private static final int TICK_PROD = 10;
	/** How many ticks a Naquadah Ingot lasts*/
	private static final int MAX_TICKS = 2400;
	
	/** How many ticks were used from this ingot. Capped to 'MAX_TICKS'. Resets to zero and uses a new ingot when the cap is hit. */
	private int ticks = 0;
	/** Wether or not this machine can run. 'power' must be below 'MAX_POWER'. */
	private boolean enabled = false;
	
	/** The inventory slot */
	private ItemStack inventory = null;
	
	public NaquadahGeneratorTE() {
		super(100000);
	}
	
	@Override
	public void invalidate(){
		super.invalidate();
		if(inventory != null && inventory.stackSize > 0 && !worldObj.isRemote)
			worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord, zCoord, inventory));
	}
	
	public void updateEntity(){
		drainPowerOverflow();
		// Produce power. If an ingot is depleted, the generator is disabled.
		if(enabled){
			if(ticks == MAX_TICKS || powerOverflow > 0){
				enabled = false;
			}else{
				ticks++;
				// Add power to the overflow buffer.
				// It will be moved to the main capacitor in the next tick.
				powerOverflow += TICK_PROD;
			}
		}
		// Try to get the generator running again.
		// This means checking the inventory for usable Naquadah.
		if(!enabled){
			if(inventory != null) if(inventory.stackSize < 1) inventory = null;
			if(inventory != null && powerOverflow == 0 && inventory.itemID == StargateTech.naquadahIngot.itemID){
				inventory.stackSize--;
				if(inventory.stackSize < 1) inventory = null;
				enabled = true;
				ticks = 0;
			}
		}
	}
	
	// This is a small workaround, I hope I can remove this soon.
	@Deprecated
	public void setPower(int power){
		powerAmount = power;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		enabled = nbt.getBoolean("enabled");
		ticks = nbt.getInteger("ticks");
		if(nbt.hasKey("inventory")){
			inventory = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("inventory"));
		}else{
			inventory = null;
		}
    }
	
	@Override
    public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		NBTTagCompound inventoryNBT = new NBTTagCompound();
		if(inventory != null){
			inventory.writeToNBT(inventoryNBT);
			nbt.setCompoundTag("inventory", inventoryNBT);
		}
		nbt.setBoolean("enabled", enabled);
		nbt.setInteger("ticks", ticks);
    }
	
	// ***************************************************************************************
	// * ISidedInventory stuff 
	@Override public int getSizeInventory() { return 1; }
	@Override public ItemStack getStackInSlot(int s) { return inventory; }
	@Override public ItemStack decrStackSize(int s, int q) { return inventory.splitStack(q); }
	@Override public ItemStack getStackInSlotOnClosing(int s) { return inventory; }
	@Override public void setInventorySlotContents(int s, ItemStack stack) { inventory = stack; }
	@Override public String getInvName() { return "Naquadah Generator"; }
	@Override public int getInventoryStackLimit() { return 64; }
	@Override public boolean isUseableByPlayer(EntityPlayer player) { return true; }
	@Override public void openChest(){}
	@Override public void closeChest(){}
	@Override public int getStartInventorySide(ForgeDirection side){ return side == ForgeDirection.UP ? 0 : -1; }
	@Override public int getSizeInventorySide(ForgeDirection side) { return side == ForgeDirection.UP ? 1 : 0; }
	@Override public void onInventoryChanged(){}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return false;
	}
}