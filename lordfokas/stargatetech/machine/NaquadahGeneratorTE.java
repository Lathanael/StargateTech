package lordfokas.stargatetech.machine;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import lordfokas.stargatetech.StargateTech;

public class NaquadahGeneratorTE extends TileEntity implements ISidedInventory{
	public static final String ID = "NaquadahGeneratorTE";
	
	/** How much power this generator can store */
	private static final int MAX_POWER = 100000;
	/** How much power is produced every tick */
	private static final int TICK_PROD = 10;
	/** How many ticks a Naquadah Ingot lasts*/
	private static final int MAX_TICKS = 2400;
	
	/** How much power is stored inside */
	public int power = 0;
	/** Overflow Buffer: represents the excess power in this machine, because the 'power' cap can be exceeded in some situations */
	private int buffer = 0;
	/** How many ticks were used from this ingot. Capped to 'MAX_TICKS'. Resets to zero and uses a new ingot when the cap is hit. */
	private int ticks = 0;
	/** Wether or not this machine can run. 'power' must be below 'MAX_POWER'. */
	private boolean enabled = false;
	
	// Inventory
	private ItemStack inventory = null;
	private int ssize;
	private int sid;
	private int sdmg;
	
	@Override
	public void invalidate(){
		super.invalidate();
		if(inventory != null && inventory.stackSize > 0 && !worldObj.isRemote)
			worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord, zCoord, inventory));
	}
	
	public void updateEntity(){
		// Try to empty the overflow buffer
		if(buffer > 0){
			power += buffer;
			if(power > MAX_POWER){
				buffer = power - MAX_POWER;
				power = MAX_POWER;
			}else{
				buffer = 0;
			}
		}
		// Produce power. If an ingot is depleted, the generator is disabled.
		if(enabled){
			if(ticks == MAX_TICKS){
				enabled = false;
			}else{
				ticks++;
				// Add power to the overflow buffer.
				// It will be moved to the main capacitor in the next tick.
				buffer += TICK_PROD;
			}
		}
		// Try to get the generator running again.
		// This means checking the inventory for usable Naquadah.
		if(!enabled){
			if(inventory != null) if(inventory.stackSize < 1) inventory = null;
			if(inventory != null){
				if(inventory.itemID == StargateTech.naquadahIngot.shiftedIndex){
					inventory.stackSize--;
					if(inventory.stackSize < 1) inventory = null;
					enabled = true;
					ticks = 0;
				}
			}
		}
	}
	
	/**
	 * Handle a power request from the PowerNet
	 * @param p How much power was requested.
	 * @return An int from 0 to 'p', the most power this machine can dispense for that request.
	 */
	public int requestPower(int p) {
		if(p > power){
			int ret = power;
			power = 0;
			return ret;
		}else{
			power -= p;
			return p;
		}
	}
	
	/**
	 * Accept power back from a previous request that was only partially used.
	 * @param p How much power put back.
	 */
	public void giveBack(int p){ buffer += p; }
	
	/**
	 * How full is this machine's power capacitor?
	 * Returns 200% so that routing systems use power from the generator
	 * instead of a buffer in the network, reducing lag.
	 * @return 200%. Made in Russia.
	 */
	public float getFill(){ return 200.0F; }
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		buffer = nbt.getInteger("buffer");
		enabled = nbt.getBoolean("enabled");
		power = nbt.getInteger("power");
		sdmg = nbt.getInteger("sdmg");
		sid = nbt.getInteger("sid");
		ssize = nbt.getInteger("ssize");
		ticks = nbt.getInteger("ticks");
		
		// Inventories... should fix them soon
		inventory = (sid != -1) ? new ItemStack(sid, ssize, sdmg) : null;
		//***************************************************************
    }
	
	@Override
    public void writeToNBT(NBTTagCompound nbt){
		// Remove retarded workaround for inventory and own derpiness
		if(inventory != null){
	        sdmg = inventory.getItemDamage();
	        sid = inventory.itemID;
	        ssize = inventory.stackSize;
		}else{
			sid = -1;
		}
		//***********************************
		
		super.writeToNBT(nbt);
		nbt.setInteger("buffer", buffer);
		nbt.setBoolean("enabled", enabled);
		nbt.setInteger("power", power);
		nbt.setInteger("sdmg", sdmg);
		nbt.setInteger("sid", sid);
		nbt.setInteger("ssize", ssize);
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
}
