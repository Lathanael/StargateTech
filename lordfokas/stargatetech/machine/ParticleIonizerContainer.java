package lordfokas.stargatetech.machine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import lordfokas.stargatetech.common.BaseContainer;

public class ParticleIonizerContainer extends BaseContainer{
	public ParticleIonizerTE ionizer;
	public ItemStack material = null;
	
	public ParticleIonizerContainer(ParticleIonizerTE te, EntityPlayer player){
		ionizer = te;
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
        iCrafting.sendProgressBarUpdate(this, 0, ionizer.getIonAmount());
        iCrafting.sendProgressBarUpdate(this, 1, ionizer.itemIons);
        iCrafting.sendProgressBarUpdate(this, 2, ionizer.ionsPerTick);
        iCrafting.sendProgressBarUpdate(this, 3, ionizer.iid);
        iCrafting.sendProgressBarUpdate(this, 4, ionizer.dmg);
    }
	
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int field, int value){
		switch(field){
			case 0:
				ionizer.setIonAmount(value);
				break;
			case 1:	
				ionizer.itemIons = value;
				break;
			case 2:
				ionizer.ionsPerTick = value;
				break;
			case 3:
				ionizer.iid = value;
				break;
			case 4:
				ionizer.dmg = value;
				break;
		}
		if(ionizer.iid == 0)
			material = null;
		else{
			material = new ItemStack(Item.itemsList[ionizer.iid]);
			material.setItemDamage(ionizer.dmg);
		}
	}
}
