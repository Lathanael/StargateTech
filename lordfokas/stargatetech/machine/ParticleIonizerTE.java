package lordfokas.stargatetech.machine;

import lordfokas.stargatetech.common.ParticleIonizerRecipes;
import lordfokas.stargatetech.common.ParticleIonizerRecipes.IonizerRecipe;
import lordfokas.stargatetech.networks.BasePowerSinkIonGeneratorTE;
import lordfokas.stargatetech.networks.power.StaticPowerNetRouter;
import lordfokas.stargatetech.util.CoordinateSet;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class ParticleIonizerTE extends BasePowerSinkIonGeneratorTE implements ISidedInventory{
	public static final String ID = "ParticleIonizerTE";
	private static final int PRODUCTION_TICKS = 2400;
	private static final int BASE_POWER = 5;
	
	// Inventory
	private ItemStack inventory = null;
	
	// GUI
	/** The itemID of what's in the slot for display in the GUI */
	public int iid = 0;
	/** The item Damage of what's in the slot for display in the GUI */
	public int dmg = 0;
	
	// Generator
	/** How many Ions this item has left */
	public int itemIons = 0;
	/** How many ions this item produces per tick */
	public int ionsPerTick = 0;
	/** How many ticks have we run */
	private int productionTick = PRODUCTION_TICKS;
	/** Can the machine produce ions? Depends on a lot of things */
	private boolean canProduce = false;
	/** How much power do we need to use every tick to get ions from this item */
	private int itemPowerNeeds = 1;
	
	public ParticleIonizerTE() {
		super(15000, 100000);
	}
	
	@Override
	public void invalidate(){
		super.invalidate();
		if(inventory != null && inventory.stackSize > 0 && !worldObj.isRemote)
			worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord, zCoord, inventory));
	}
	
	public void updateEntity(){
		drainIonOverflow();
		refillPowerBuffer();
		if(ionOverflow == 0 && powerAmount >= itemPowerNeeds && canProduce){
			ionAmount += ionsPerTick;
			itemIons -= ionsPerTick;
			productionTick--;
			powerAmount -= itemPowerNeeds;
			if(productionTick == 0){
				productionTick = PRODUCTION_TICKS;
				iid = dmg = 0;
				canProduce = false;
				useItem();
			}
		}
		// if the ionizer is stopped, try to use another item.
		if(!canProduce){
			if(inventory != null){
				useItem();
			}
		}
	}
	
	private void useItem(){
		if(inventory == null || ionOverflow != 0) return;
		if(inventory.stackSize < 1){
			canProduce = false;
			inventory = null;
			return;
		}
		IonizerRecipe ir = ParticleIonizerRecipes.get(inventory);
		if(ir == null){
			canProduce = false;
			iid = 0;
			dmg = 0;
			return;
		}
		iid = inventory.itemID;
		dmg = inventory.getItemDamage();
		itemPowerNeeds = ((int)(ir.power / 100)) * BASE_POWER;
		ionsPerTick = ir.ionsPerTick;
		inventory.stackSize -= 1;
		if(inventory.stackSize < 1) inventory = null;
		canProduce = true;
	}
	
	// This is a small workaround.
	@Deprecated
	public void setIonAmount(int ions){
		ionAmount = ions;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		canProduce = nbt.getBoolean("canProduce");
		dmg = nbt.getInteger("dmg");
		iid = nbt.getInteger("iid");
		ionsPerTick = nbt.getInteger("ionsPerTick");
		itemIons = nbt.getInteger("itemIons");
		itemPowerNeeds = nbt.getInteger("itemPowerNeeds");
		productionTick = nbt.getInteger("productionTick");
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
		nbt.setBoolean("canProduce", canProduce);
		nbt.setInteger("dmg", dmg);
		nbt.setInteger("iid", iid);
		nbt.setInteger("ionsPerTick", ionsPerTick);
		nbt.setInteger("itemIons", itemIons);
		nbt.setInteger("itemPowerNeeds", itemPowerNeeds);
		nbt.setInteger("productionTick", productionTick);
    }
	
	// ***************************************************************************************
	// * ISidedInventory stuff
	@Override public int getSizeInventory() { return 1; }
	@Override public ItemStack getStackInSlot(int s) { return inventory; }
	@Override public ItemStack decrStackSize(int s, int q) { return inventory.splitStack(q); }
	@Override public ItemStack getStackInSlotOnClosing(int s) { return inventory; }
	@Override public void setInventorySlotContents(int s, ItemStack stack) { inventory = stack; }
	@Override public String getInvName() { return "Particle Ionizer"; }
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
