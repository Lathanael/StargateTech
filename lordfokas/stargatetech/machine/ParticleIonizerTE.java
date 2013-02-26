package lordfokas.stargatetech.machine;

import lordfokas.stargatetech.common.BaseTileEntity;
import lordfokas.stargatetech.common.ParticleIonizerRecipes;
import lordfokas.stargatetech.common.ParticleIonizerRecipes.IonizerRecipe;
import lordfokas.stargatetech.networks.power.PowerNetStaticRouter;
import lordfokas.stargatetech.util.CoordinateSet;
import lordfokas.stargatetech.util.NBTAutomation;
import lordfokas.stargatetech.util.NBTAutomation.NBTField;
import lordfokas.stargatetech.util.NBTAutomation.NBTFieldCompound;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

@NBTFieldCompound
public class ParticleIonizerTE extends BaseTileEntity implements ISidedInventory{
	public static final String ID = "ParticleIonizerTE";
	private static final int PRODUCTION_TICKS = 2400;
	private static final int SEARCH_TICKS = 20;
	private static final int BASE_POWER = 500;
	
	// Inventory
	private ItemStack inventory = null;
	@NBTField private int ssize;
	@NBTField private int sid;
	@NBTField private int sdmg;
	
	// GUI
	@NBTField public int iid = 0;
	@NBTField public int dmg = 0;
	
	// IonNet
	@NBTField private int overflowBuffer = 0;
	@NBTField private int ionStorage = 10000;
	@NBTField public int ionAmount = 0;
	@NBTField public int itemIons = 0;
	@NBTField public int ionsPerTick = 0;
	@NBTField private int productionTick = PRODUCTION_TICKS;
	@NBTField private boolean canProduce = false;
	
	// PowerNet
	@NBTField private int itemPowerNeeds = 1;
	@NBTField private int bufferSize = 10000;
	@NBTField private int power = 0;
	@NBTField private int searchTicks = 0;
	
	@Override
	public void invalidate(){
		super.invalidate();
		if(inventory != null && inventory.stackSize > 0 && !worldObj.isRemote)
			worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord, zCoord, inventory));
	}
	
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
		if(ionAmount < ionStorage){
			ionAmount += overflowBuffer;
			overflowBuffer = 0;
			checkOverflow();
		}
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
	public String getID()
		{ return ID; }
	
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
	@Override public String getInvName() { return "Particle Ionizer"; }
	@Override public int getInventoryStackLimit() { return 64; }
	@Override public boolean isUseableByPlayer(EntityPlayer player) { return true; }
	@Override public void openChest(){}
	@Override public void closeChest(){}
	@Override public int getStartInventorySide(ForgeDirection side){ return side == ForgeDirection.UP ? 0 : -1; }
	@Override public int getSizeInventorySide(ForgeDirection side) { return side == ForgeDirection.UP ? 1 : 0; }
	@Override public void onInventoryChanged(){}
}
