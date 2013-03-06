package lordfokas.stargatetech.world;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import lordfokas.stargatetech.common.BaseBlock;
import lordfokas.stargatetech.util.TextureIndex;

/**
 * Invisible block, that takes space and colides with entities.
 * Used by multi-block structures like Stargates
 * @author LordFokas
 */
public class Placeholder extends BaseBlock {
	public Placeholder(int id) {
		super(id, TextureIndex.voidTexture);
		setBlockName("placeholder");
		setLightOpacity(0);
	}
	
	@Override public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z){ return null; }
	@Override public boolean isOpaqueCube(){ return false; }
}
