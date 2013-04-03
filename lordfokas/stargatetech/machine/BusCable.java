package lordfokas.stargatetech.machine;

import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import lordfokas.stargatetech.common.BaseBlock;
import lordfokas.stargatetech.networks.bus.BusBlock.IBusComponent;
import lordfokas.stargatetech.rendering.RenderBusCable;
import lordfokas.stargatetech.util.Helper;

public class BusCable extends BaseBlock implements IBusComponent{
	public BusCable(int id) {
		super(id, "busCable", false);
		texturename = "busCableZ";
	}

	@Override
	public boolean isPropagator() {
		return true;
	}

	@Override
	public boolean isConnector() {
		return false;
	}

	@Override
	public boolean canBusPlugOnSide(IBlockAccess w, int x, int y, int z, int side) {
		return true;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World w, int x, int y, int z){
		return null;
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
	public int getRenderType(){
		return RenderBusCable.instance().getRenderId();
	}
	
	private boolean isValidPosition(World w, int x, int y, int z){
		Block bottom = Helper.getBlockInstance(w, x, y-1, z);
		return bottom.isBlockSolidOnSide(w, x, y, z, ForgeDirection.UP);
	}
	
	@Override
	public boolean canBlockStay(World w, int x, int y, int z){
		return isValidPosition(w, x, y, z);
	}
	
	@Override
	public boolean canPlaceBlockAt(World w, int x, int y, int z){
		return isValidPosition(w, x, y, z);
	}
}