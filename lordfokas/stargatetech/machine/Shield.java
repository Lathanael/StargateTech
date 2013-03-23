package lordfokas.stargatetech.machine;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import lordfokas.stargatetech.common.BaseBlock;
import lordfokas.stargatetech.rendering.RenderIonTube;
import lordfokas.stargatetech.util.Helper;
import lordfokas.stargatetech.util.UnlocalizedNames;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Shield extends BaseBlock {
	/** Equals (BLOCK_PLAYER | BLOCK_MOBS | BLOCK_FRIENDLY | BLOCK_NONLIVING) */
	public static final int BLOCK_ALL		= 0xF; // 0b1111
	/** Binary metadata flag to block Player entities */
	public static final int BLOCK_PLAYER	= 0x8; // 0b1000
	/** Binary metadata flag to block Mob entities */
	public static final int BLOCK_MOBS		= 0x4; // 0b0100
	/** Binary metadata flag to block any Living entity that's not a Mob or a Player */
	public static final int BLOCK_FRIENDLY	= 0x2; // 0b0010
	
	public Shield(int id) {
		super(id, UnlocalizedNames.BLOCK_SHIELD);
		this.setLightValue(1.0F);
	}
	
	@Override
	public void addCollisionBoxesToList(World w, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity){
		boolean collide = false;
		int meta = w.getBlockMetadata(x, y, z);
		
		// Determine if this entity should be collided with
		if(entity instanceof EntityLiving){
			if(entity instanceof EntityPlayer){
				collide = (meta & BLOCK_PLAYER) != 0;
			}else if(entity instanceof EntityMob){
				collide = (meta & BLOCK_MOBS) != 0;
			}else{
				collide = (meta & BLOCK_FRIENDLY) != 0;
			}
		}else{
			// There used to be a BLOCK_NONLIVING flag, but it's currently impossible to support
			// that kind of behavior in current minecraft / forge.
			// As a fallback, all non-living entities collide with the shields in all the situations.
			collide = true;
		}
		
		// Collide with the entity according to the check above
		if(collide == true){
			super.addCollisionBoxesToList(w, x, y, z, aabb, list, entity);
		}
    }
	
	// Shields can't be destroyed by players, even in creative mode.
	@Override public void onBlockDestroyedByPlayer(World w, int x, int y, int z, int meta){
		w.setBlockAndMetadataWithNotify(x, y, z, this.blockID, meta, Helper.SETBLOCK_UPDATE);
	}
	
	// Disallow the player from getting a shield block, even in creative.
	@Override public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z){ return null; }
	// No dropping shields either.
	@Override public int quantityDropped(Random par1Random){ return 0; }
	@Override @SideOnly(Side.CLIENT) public int getRenderBlockPass(){ return 1; }
    @Override public boolean isOpaqueCube(){ return false; }
    @Override public boolean renderAsNormalBlock(){ return false; }
}