package lordfokas.stargatetech.machine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import lordfokas.stargatetech.common.BaseContainer;

public class NaquadahGeneratorContainer extends BaseContainer{
	public NaquadahGeneratorTE generator;
	
	public NaquadahGeneratorContainer(NaquadahGeneratorTE te, EntityPlayer player){
		generator = te;
		bindInventory(player, -6);
		addSlotToContainer(new Slot(te, 36, 134, 32));
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
	
	@Override
	public void addCraftingToCrafters(ICrafting iCrafting)
    {
        super.addCraftingToCrafters(iCrafting);
        iCrafting.sendProgressBarUpdate(this, 0, generator.getPowerAmount());
    }
	
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int field, int value){
		switch(field){
			case 0:
				generator.setPower(value);
				break;
		}
	}
}
