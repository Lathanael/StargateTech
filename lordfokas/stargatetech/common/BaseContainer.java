package lordfokas.stargatetech.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class BaseContainer extends Container {
	@Override public boolean canInteractWith(EntityPlayer var1){ return true; }
	
	public void bindInventory(EntityPlayer player, int yOffset){
		bindInventory(player, 8, yOffset);
	}
	
	public void bindInventory(EntityPlayer player, int xOffset, int yOffset){
		IInventory inventoryPlayer = player.inventory;
		for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                    addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                                    xOffset + j * 18, 84 + yOffset + i * 18));
            }
	    }
	    for (int i = 0; i < 9; i++) {
	            addSlotToContainer(new Slot(inventoryPlayer, i, xOffset + i * 18, 142 + yOffset));
	    }
	}
}
