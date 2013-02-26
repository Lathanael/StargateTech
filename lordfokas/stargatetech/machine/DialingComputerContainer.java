package lordfokas.stargatetech.machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import lordfokas.stargatetech.common.BaseContainer;

public class DialingComputerContainer extends BaseContainer {
	public DialingComputerTE computer;
	
	public DialingComputerContainer(DialingComputerTE computer, EntityPlayer player){
		this.computer = computer;
		bindInventory(player, 33, 61);
		addSlotToContainer(new Slot(computer, 36, 105, 35));
	}
}
