package lordfokas.stargatetech.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import lordfokas.stargatetech.common.BaseItem;
import lordfokas.stargatetech.util.TextureIndex;

/**
 * As long as the player has this on his inventory, he'll be immune to most damage.
 * It will wear off eventually though.
 * @author LordFokas
 */
public class PersonalShield extends BaseItem {

	public PersonalShield(int id) {
		super(id, TextureIndex.personalShield);
		this.setItemName("personalShield");
		this.setMaxDamage(1000000);
		this.setMaxStackSize(1);
	}
	
	@Override
	public EnumRarity getRarity(ItemStack ignored){
		return EnumRarity.epic;
	}
}
