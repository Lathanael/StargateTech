package lordfokas.stargatetech.machine;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import lordfokas.stargatetech.ClientProxy;
import lordfokas.stargatetech.common.BaseBlock;
import lordfokas.stargatetech.rendering.RenderIonTube;
import lordfokas.stargatetech.util.TextureIndex;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Shield extends BaseBlock {
	public static int id;
	
	public Shield(int id) {
		super(id, TextureIndex.shield);
		this.id = id;
		this.setBlockName("shield");
		this.setLightValue(1.0F);
	}
	
	@Override
	public void addCollidingBlockToList(World w, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity){
		if(!(entity instanceof EntityPlayer)){
			super.addCollidingBlockToList(w, x, y, z, aabb, list, entity);
		}
    }
	
	@Override public void onBlockDestroyedByPlayer(World w, int x, int y, int z, int meta){ w.setBlock(x, y, z, id); }
	@Override public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z){ return null; }
	@Override public int quantityDropped(Random par1Random){ return 0; }
	@Override @SideOnly(Side.CLIENT) public int getRenderBlockPass(){ return 1; }
    @Override public boolean isOpaqueCube(){ return false; }
    @Override public boolean renderAsNormalBlock(){ return false; }
}