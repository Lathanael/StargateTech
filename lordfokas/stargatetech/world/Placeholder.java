package lordfokas.stargatetech.world;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import lordfokas.stargatetech.common.BaseBlock;
import lordfokas.stargatetech.util.IconRegistry;
import lordfokas.stargatetech.util.UnlocalizedNames;

/**
 * Invisible block, that takes space and colides with entities.
 * Used by multi-block structures like Stargates
 * @author LordFokas
 */
public class Placeholder extends BaseBlock {
	public Placeholder(int id) {
		super(id, UnlocalizedNames.BLOCK_INVISIBLE);
		setLightOpacity(0);
	}
	
	@Override public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z){
		return null;
	}
	
	@Override public boolean isOpaqueCube(){
		return false;
	}
	
	@Override
	public boolean canPlaceTorchOnTop(World w, int x, int y, int z){
		return false;
	}
	
	@Override // No more placing torches on placeholders!
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side){
		return false;
	}
	
	@Override
	public void registerIcons(IconRegister register){
		this.blockIcon = register.registerIcon("StargateTech:void");
		IconRegistry.instance.loadAllBlocks(register);
	}
}
