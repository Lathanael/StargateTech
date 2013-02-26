package lordfokas.stargatetech.machine;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import lordfokas.stargatetech.StargateTech;
import lordfokas.stargatetech.common.BaseTileEntity;
import lordfokas.stargatetech.util.NBTAutomation.NBTField;
import lordfokas.stargatetech.util.NBTAutomation.NBTFieldCompound;

@NBTFieldCompound
public class NaquadahGeneratorTE extends BaseTileEntity implements ISidedInventory{
	public static final String ID = "NaquadahGeneratorTE";
	private static final int MAX_POWER = 100000;
	private static final int TICK_PROD = 10;
	private static final int MAX_TICKS = 2400;
	
	@NBTField public int power = 0;
	@NBTField private int buffer = 0;
	@NBTField private int ticks = 0;
	@NBTField private boolean enabled = false;
	
	// Inventory
	private ItemStack inventory = null;
	@NBTField private int ssize;
	@NBTField private int sid;
	@NBTField private int sdmg;
	
	@Override public String getID(){ return ID; }
	
	@Override
	public void invalidate(){
		super.invalidate();
		if(inventory != null && inventory.stackSize > 0 && !worldObj.isRemote)
			worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord, zCoord, inventory));
	}
	
	public void updateEntity(){
		if(buffer > 0){
			power += buffer;
			if(power > MAX_POWER){
				buffer = power - MAX_POWER;
				power = MAX_POWER;
			}else{
				buffer = 0;
			}
		}
		if(enabled){
			if(ticks == MAX_TICKS){
				enabled = false;
			}else{
				ticks++;
				buffer += TICK_PROD;
			}
		}
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

	public void giveBack(int p){ buffer += p; }
	public float getFill(){ return 200.0F; }
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		inventory = (sid != -1) ? new ItemStack(sid, ssize, sdmg) : null;
    }
	
	@Override
    public void writeToNBT(NBTTagCompound nbt){
		if(inventory != null){
	        sdmg = inventory.getItemDamage();
	        sid = inventory.itemID;
	        ssize = inventory.stackSize;
		}else{
			sid = -1;
		}
		super.writeToNBT(nbt);
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
