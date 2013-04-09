package lordfokas.stargatetech.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import lordfokas.stargatetech.api.IDisintegrable;
import lordfokas.stargatetech.common.BaseItem;
import lordfokas.stargatetech.util.Helper;
import lordfokas.stargatetech.util.UnlocalizedNames;

/**
 * Disintegrates any blocks that implement IDisintegrable.
 * Used for ores and similar blocks.
 * @author LordFokas
 */
public class Disintegrator extends BaseItem {
	public Disintegrator(int id) {
		super(id, UnlocalizedNames.ITEM_DISINTEGRATOR);
		this.setUnlocalizedName("disintegrator");
		this.setMaxDamage(256);
		this.setMaxStackSize(1);
	}
	
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player){
        MovingObjectPosition target = this.getMovingObjectPositionFromPlayer(world, player, true);
        if (target == null) return itemStack;
        if (target.typeOfHit == EnumMovingObjectType.TILE){
            int x = target.blockX;
            int y = target.blockY;
            int z = target.blockZ;
            int k = world.getBlockId(x, y, z);
            if(Helper.getBlockInstance(world, x, y, z) instanceof IDisintegrable){
            	if(((IDisintegrable)Helper.getBlockInstance(world, x, y, z)).disintegrate(world, x, y, z)){
	            	itemStack.damageItem(1, player);
            	}
            }
        }
        return itemStack;
    }
	
	@Override
	public EnumRarity getRarity(ItemStack ignored){
		return EnumRarity.uncommon;
	}
}
