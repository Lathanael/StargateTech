package lordfokas.stargatetech.machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import lordfokas.stargatetech.common.BaseContainer;

public class DialingComputerContainer extends BaseContainer {
	public DialingComputerTE computer;
	
	public DialingComputerContainer(DialingComputerTE computer, EntityPlayer player){
		this.computer = computer;
		bindInventory(player, 33, 61);
		addSlotToContainer(new Slot(computer, 36, 105, 35));
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int sid){
		Slot slot = (Slot) inventorySlots.get(sid);
		ItemStack stack = null;
		ItemStack stackInSlot = null;
		if (slot != null && slot.getHasStack()) {
			stackInSlot = slot.getStack();
			stack = stackInSlot.copy();
			if(sid == 36){ // Clicked TE Slot
				if(!mergeItemStack(stackInSlot, 0, 36, true)) return null;
			}else{ // Clicked Player Inv Slot
				if(!mergeItemStack(stackInSlot, 36, 37, true)) return null;
			}
			if(stackInSlot.stackSize == 0) slot.putStack(null);
	        else slot.onSlotChanged();
	        if (stackInSlot.stackSize == stack.stackSize) return null;
	        slot.onPickupFromSlot(player, stackInSlot);
		}
		return stack;
	}
}
