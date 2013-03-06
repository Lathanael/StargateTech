package lordfokas.stargatetech.machine;

import lordfokas.stargatetech.common.ParticleIonizerRecipes;
import lordfokas.stargatetech.common.ParticleIonizerRecipes.IonizerRecipe;
import lordfokas.stargatetech.networks.power.PowerNetStaticRouter;
import lordfokas.stargatetech.util.CoordinateSet;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class ParticleIonizerTE extends TileEntity implements ISidedInventory{
	public static final String ID = "ParticleIonizerTE";
	private static final int PRODUCTION_TICKS = 2400;
	private static final int SEARCH_TICKS = 20;
	private static final int BASE_POWER = 500;
	
	// Inventory
	private ItemStack inventory = null;
	private int ssize;
	private int sid;
	private int sdmg;
	
	// GUI
	/** The itemID of what's in the slot for display in the GUI */
	public int iid = 0;
	/** The item Damage of what's in the slot for display in the GUI */
	public int dmg = 0;
	
	// IonNet
	/** How many excess ions do we have */
	private int overflowBuffer = 0;
	/** How many Ions we can handle */
	private int ionStorage = 10000;
	/** How many Ions we have */
	public int ionAmount = 0;
	/** How many Ions this item has left */
	public int itemIons = 0;
	/** How many ions this item produces per tick */
	public int ionsPerTick = 0;
	/** How many ticks have we run */
	private int productionTick = PRODUCTION_TICKS;
	/** Can the machine produce ions? Depends on a lot of things */
	private boolean canProduce = false;
	
	// PowerNet
	/** How much power do we need to use every tick to get ions from this item */
	private int itemPowerNeeds = 1;
	/** How much power can be stored internally */
	private int bufferSize = 10000;
	/** How much power is stored internally */
	private int power = 0;
	/** How many ticks since we last searched for power */
	private int searchTicks = 0;
	
	@Override
	public void invalidate(){
		super.invalidate();
		if(inventory != null && inventory.stackSize > 0 && !worldObj.isRemote)
			worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord, zCoord, inventory));
	}
	
	/**
	 * Handle network Ion requests.
	 * @param ions How many ions were requested
	 * @return An int from 0 to 'ions', the most ions this machine can supply right now.
	 */
	public int requestIons(int ions) {
		if(ions > ionAmount){
			int ret = ionAmount;
			ionAmount = 0;
			return ret;
		}else{
			ionAmount -= ions;
			return ions;
		}
	}
	
	public void giveBack(int ions){ overflowBuffer += ions; }
	public float getFill(){ return ionAmount / ionStorage; }
	
	public void updateEntity(){
		// put excess ions in the overflow buffer
		if(ionAmount < ionStorage){
			ionAmount += overflowBuffer;
			overflowBuffer = 0;
			checkOverflow();
		}
		// try to produce ions
		if(ionAmount < ionStorage && power > 0 && canProduce){
			ionAmount += ionsPerTick;
			itemIons -= ionsPerTick;
			productionTick--;
			power -= itemPowerNeeds;
			checkOverflow();
			if(productionTick == 0){
				productionTick = PRODUCTION_TICKS;
				iid = dmg = 0;
				canProduce = false;
				useItem();
			}
		}
		// if power buffer is under 10%, request more power from the network.
		if(power < (bufferSize/10)){
			if(searchTicks == 0){
				CoordinateSet pos = new CoordinateSet(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
				power += PowerNetStaticRouter.route(bufferSize - power, pos, true);
			}
			searchTicks++;
			if(searchTicks == SEARCH_TICKS){
				searchTicks = 0;
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
		if(inventory == null) return;
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
		itemPowerNeeds = (int) (ir.power / 100) * BASE_POWER;
		ionsPerTick = ir.ionsPerTick;
		inventory.stackSize -= 1;
		if(inventory.stackSize < 1) inventory = null;
		canProduce = true;
	}
	
	private void checkOverflow(){
		if(ionAmount > ionStorage){
			int ov = ionAmount - ionStorage;
			ionAmount -= ov;
			overflowBuffer += ov;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		canProduce = nbt.getBoolean("canProduce");
		dmg = nbt.getInteger("dmg");
		iid = nbt.getInteger("iid");
		ionAmount = nbt.getInteger("ionAmount");
		ionsPerTick = nbt.getInteger("ionsPerTick");
		itemIons = nbt.getInteger("itemIons");
		itemPowerNeeds = nbt.getInteger("itemPowerNeeds");
		overflowBuffer = nbt.getInteger("overflowBuffer");
		power = nbt.getInteger("power");
		productionTick = nbt.getInteger("productionTick");
		sdmg = nbt.getInteger("sdmg");
		searchTicks = nbt.getInteger("searchTicks");
		sid = nbt.getInteger("sid");
		ssize = nbt.getInteger("ssize");
		
		// We should move to itemstack's NBT methods.
		inventory = (sid != -1) ? new ItemStack(sid, ssize, sdmg) : null;
		//***************************************************************
    }
	
	@Override
    public void writeToNBT(NBTTagCompound nbt){
		// yep, we should definetelly move to the stack's methods.
		// I tried it, but there were errors I couldn't understand, so I created a workaround.
		// This will eventually be removed though...
		if(inventory != null){
	        sdmg = inventory.getItemDamage();
	        sid = inventory.itemID;
	        ssize = inventory.stackSize;
		}else{
			sid = -1;
		}
		//***********************************
		
		super.writeToNBT(nbt);
		nbt.setBoolean("canProduce", canProduce);
		nbt.setInteger("dmg", dmg);
		nbt.setInteger("iid", iid);
		nbt.setInteger("ionAmount", ionAmount);
		nbt.setInteger("ionsPerTick", ionsPerTick);
		nbt.setInteger("itemIons", itemIons);
		nbt.setInteger("itemPowerNeeds", itemPowerNeeds);
		nbt.setInteger("overflowBuffer", overflowBuffer);
		nbt.setInteger("power", power);
		nbt.setInteger("productionTick", productionTick);
		nbt.setInteger("sdmg", sdmg);
		nbt.setInteger("searchTicks", searchTicks);
		nbt.setInteger("sid", sid);
		nbt.setInteger("ssize", ssize);
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
}
