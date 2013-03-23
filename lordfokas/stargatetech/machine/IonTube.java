package lordfokas.stargatetech.machine;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import lordfokas.stargatetech.common.BaseBlock;
import lordfokas.stargatetech.common.IDismantleable;
import lordfokas.stargatetech.networks.ion.IonNetBlock.IIonNetComponent;
import lordfokas.stargatetech.rendering.RenderIonTube;
import lordfokas.stargatetech.util.Helper;
import lordfokas.stargatetech.util.UnlocalizedNames;

public class IonTube extends BaseBlock implements IIonNetComponent, IDismantleable {
	public IonTube(int id) {
		super(id, UnlocalizedNames.BLOCK_TUBE);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z){
		float[] min = {0.25F, 0.25F, 0.25F};
		float[] max = {0.75F, 0.75F, 0.75F};
		if(Block.blocksList[world.getBlockId(x-1, y, z)] instanceof IIonNetComponent){
			if(((IIonNetComponent)Helper.getBlockInstance(world, x-1, y, z)).canTubeConnectOnSide(world, x-1, y, z, Helper.dirXPos))
			min[0] = 0.0F;
		}
		if(Block.blocksList[world.getBlockId(x+1, y, z)] instanceof IIonNetComponent){
			if(((IIonNetComponent)Helper.getBlockInstance(world, x+1, y, z)).canTubeConnectOnSide(world, x+1, y, z, Helper.dirXNeg))
			max[0] = 1.0F;
		}
		if(Block.blocksList[world.getBlockId(x, y-1, z)] instanceof IIonNetComponent){
			if(((IIonNetComponent)Helper.getBlockInstance(world, x, y-1, z)).canTubeConnectOnSide(world, x, y-1, z, Helper.dirYPos))
			min[1] = 0.0F;	
		}
		if(Block.blocksList[world.getBlockId(x, y+1, z)] instanceof IIonNetComponent){
			if(((IIonNetComponent)Helper.getBlockInstance(world, x, y+1, z)).canTubeConnectOnSide(world, x, y+1, z, Helper.dirYNeg))
			max[1] = 1.0F;
		}
		if(Block.blocksList[world.getBlockId(x, y, z-1)] instanceof IIonNetComponent){
			if(((IIonNetComponent)Helper.getBlockInstance(world, x, y, z-1)).canTubeConnectOnSide(world, x, y, z-1, Helper.dirZPos))
			min[2] = 0.0F;
		}
		if(Block.blocksList[world.getBlockId(x, y, z+1)] instanceof IIonNetComponent){
			if(((IIonNetComponent)Helper.getBlockInstance(world, x, y, z+1)).canTubeConnectOnSide(world, x, y, z+1, Helper.dirZNeg))
			max[2] = 1.0F;
		}
		this.setBlockBounds(min[0], min[1], min[2], max[0], max[1], max[2]);
    }
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World w, int x, int y, int z){
		this.setBlockBoundsBasedOnState(w, x, y, z);
        return AxisAlignedBB.getAABBPool().getAABB((double)x + this.minX, (double)y + this.minY, (double)z + this.minZ, (double)x + this.maxX, (double)y + this.maxY, (double)z + this.maxZ);
    }
	
	@Override
	public boolean isOpaqueCube(){
        return false;
    }

	@Override
    public boolean renderAsNormalBlock(){
        return false;
    }

	@Override
    public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4){
        return true;
    }

	@Override
    public int getRenderType(){
    	return RenderIonTube.instance().getRenderId();
    }

	@Override
	public EIonComponentType getIonComponentType() {
		return EIonComponentType.TUBE;
	}

	@Override
	public boolean canTubeConnectOnSide(IBlockAccess w, int x, int y, int z, int side) {
		return true;
	}

	@Override
	public boolean dismantle(World w, int x, int y, int z){
		if(w.isRemote) return false;
		w.spawnEntityInWorld(new EntityItem(w, x, y, z, new ItemStack(this)));
		w.setBlockAndMetadataWithNotify(x, y, z, 0, 0, Helper.SETBLOCK_UPDATE);
		return false;
	}
}
