package lordfokas.stargatetech.machine;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import lordfokas.stargatetech.StargateTech;
import lordfokas.stargatetech.common.BaseBlockContainer;
import lordfokas.stargatetech.common.IDismantleable;
import lordfokas.stargatetech.networks.ion.IIonNetComponent;
import lordfokas.stargatetech.rendering.RenderShieldEmitter;
import lordfokas.stargatetech.util.CoordinateSet;
import lordfokas.stargatetech.util.Helper;
import lordfokas.stargatetech.util.TextureIndex;

public class ShieldEmitter extends BaseBlockContainer implements IIonNetComponent, IDismantleable{
	private int maxShieldRange;
	
	public ShieldEmitter(int id, int maxRange) {
		super(id, TextureIndex.shieldEmitterYFace);
		this.setBlockName("shieldEmitter");
		this.setStepSound(soundMetalFootstep);
		maxShieldRange = maxRange;
	}
	
	@Override
	public int getBlockTextureFromSide(int side){
		if(side < 2) return TextureIndex.shieldEmitterYFace;
		else if(side == 3) return TextureIndex.shieldEmitterSingle;
		else return TextureIndex.blockSingle;
	}
	
	public int getMaxShieldRange(){
		return maxShieldRange;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving living){
		int dir = -1;
		if(living instanceof EntityPlayer){
			dir = Helper.yaw2dir(living.rotationYaw);
			world.setBlockMetadata(x, y, z, dir);
		}
	}
	
	@Override
	public EIonComponentType getIonComponentType() {
		return EIonComponentType.SINK;
	}
	
	@Override
    public int getRenderType(){
    	return RenderShieldEmitter.instance().getRenderId();
    }

	@Override
	public TileEntity createNewTileEntity(World w) {
		return new ShieldEmitterTE();
	}

	@Override
	public boolean canTubeConnectOnSide(IBlockAccess w, int x, int y, int z, int side) {
		return (side > 1 && side != w.getBlockMetadata(x, y, z));
	}
	
	@Override
	public boolean dismantle(World w, int x, int y, int z){
		if(w.isRemote) return false;
		w.spawnEntityInWorld(new EntityItem(w, x, y, z, new ItemStack(this)));
		w.setBlock(x, y, z, 0);
		return false;
	}
}
