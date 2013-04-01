package lordfokas.stargatetech.machine;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import lordfokas.stargatetech.common.BaseTileEntity;
import lordfokas.stargatetech.items.AddressMemoryCrystal;
import lordfokas.stargatetech.networks.stargate.Address;
import lordfokas.stargatetech.networks.stargate.Symbol;

public class DialingComputerTE extends BaseTileEntity implements IInventory {
	public static final String ID = "DialingComputerTE";
	private ItemStack inventory = null;
	
	public boolean[] used = new boolean[39];
	public Symbol[] addr = new Symbol[9];
	public int count = 0;
	public boolean canDial = false;
	
	@Override public boolean canUpdate(){ return false; }
	
	public void onButtonClick(byte type, byte button){
		if(type == 0){
			if(!used[button - 1] && count < 9){
				used[button - 1] = true;
				addr[count] = Symbol.symbols[button];
				count++;
				canDial = count > 6;
			}
		}else{
			switch(button){
			case 0:
				dial();
				break;
			case 1:
				reset();
				break;
			case 2:
				save();
				break;
			case 3:
				load();
				break;
			}
		}
		updateClients();
	}
	
	public void dial(){
		Address address = new Address(addr);
		if(address.isValid()){
			TileEntity te = worldObj.getBlockTileEntity(xCoord, yCoord-2, zCoord-10);
			if(te != null && te instanceof StargateTE){
				StargateTE stargate = (StargateTE) te;
				stargate.dial(address);
			}
		}
	}

	public void reset(){
		used = new boolean[39];
		addr = new Symbol[9];
		count = 0;
		canDial = false;
	}
	
	public void save(){
		if(inventory != null && inventory.getItem() instanceof AddressMemoryCrystal){
			Address address = new Address(addr);
			if(address.isValid()){
				AddressMemoryCrystal.setAddress(inventory, address);
			}
		}
	}
	
	public void load(){
		if(inventory != null && inventory.getItem() instanceof AddressMemoryCrystal){
			Address address = AddressMemoryCrystal.getAddr(inventory);
			if(address != null && address.isValid()){
				addr = address.getSymbols();
				count = 0;
				used = new boolean[39];
				for(Symbol s : addr){
					if(s != null && s.getID() != 0){
						used[s.getID() - 1] = true;
						count++;
					}
				}
				canDial = true;
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		if(nbt.hasKey("inventory")){
			inventory = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("inventory"));
		}else{
			inventory = null;
		}
		for(int i = 0; i < 9; i++){
    		short sym = nbt.getShort("sym" + i);
    		addr[i] = (sym == -1 ? null : Symbol.symbols[sym]);
    	}
    	for(int i = 0; i < 39; i++){
    		used[i] = nbt.getBoolean("used" + i);
    	}
    	canDial = nbt.getBoolean("canDial");
    	count = nbt.getInteger("count");
	}

    @Override
    public void writeToNBT(NBTTagCompound nbt){
    	super.writeToNBT(nbt);
    	NBTTagCompound inventoryNBT = new NBTTagCompound();
		if(inventory != null){
			inventory.writeToNBT(inventoryNBT);
			nbt.setCompoundTag("inventory", inventoryNBT);
		}
    	for(int i = 0; i < 9; i++){
    		short sym;
    		if(addr[i] == null){
    			sym = -1;
    		}else{
    			sym = addr[i].getID();
    		}
    		nbt.setShort("sym" + i, sym);
    	}
    	for(int i = 0; i < 39; i++){
    		nbt.setBoolean("used" + i, used[i]);
    	}
    	nbt.setBoolean("canDial", canDial);
    	nbt.setInteger("count", count);
    }
	
	// ***************************************************************************************
	// * IInventory stuff 
	@Override public int getSizeInventory() { return 1; }
	@Override public ItemStack getStackInSlot(int s) { return inventory; }
	@Override public ItemStack decrStackSize(int s, int q) { return inventory.splitStack(q); }
	@Override public ItemStack getStackInSlotOnClosing(int s) { return inventory; }
	@Override public void setInventorySlotContents(int s, ItemStack stack) { inventory = stack; }
	@Override public String getInvName() { return "Dialing Computer"; }
	@Override public int getInventoryStackLimit() { return 64; }
	@Override public boolean isUseableByPlayer(EntityPlayer player) { return true; }
	@Override public void openChest(){}
	@Override public void closeChest(){}
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