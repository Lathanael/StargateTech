package lordfokas.stargatetech.machine;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import lordfokas.stargatetech.items.AddressMemoryCrystal;
import lordfokas.stargatetech.networks.stargate.Address;
import lordfokas.stargatetech.networks.stargate.Symbol;

public class DialingComputerTE extends TileEntity implements IInventory {
	public static final String ID = "DialingComputerTE";
	private ItemStack inventory = null;
	
	public boolean[] used = new boolean[39];
	public Symbol[] addr = new Symbol[9];
	public int count = 0;
	public boolean canDial = false;
	
	@Override public boolean canUpdate(){ return false; }
	
	// called from the GUI. Will use packets later.
	public void load(){
		
	}
	
	// called from the GUI. Will use packets later.
	// also, it doesn't feel like working. Not sure what's wrong.
	public void save(){
		if(inventory != null && inventory.getItem() instanceof AddressMemoryCrystal){
			ArrayList<Symbol> tmp = new ArrayList<Symbol>(9);
			for(Symbol sym : addr){
				if(sym != null && sym != Symbol.NONE)
					tmp.add(sym);
			}
			int l = tmp.size();
			Symbol[] shortAddress = new Symbol[l];
			for(int i = 0; i < l; i++){
				shortAddress[i] = tmp.get(i);
			}
			
			Address attempt = new Address(shortAddress);
			AddressMemoryCrystal.setAddress(inventory, attempt);
			
			Address address = AddressMemoryCrystal.getAddr(inventory);
			if(address != null) System.out.println("SAVED ADDR: " + address.getName());
			else System.out.println("FAILED TO SAVE: " + attempt.getName());
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		// Nothing yet
	}

    @Override
    public void writeToNBT(NBTTagCompound nbt){
    	super.writeToNBT(nbt);
    	// Nothing yet
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
}
