package lordfokas.stargatetech.machine;

import static net.minecraftforge.common.ForgeDirection.DOWN;
import static net.minecraftforge.common.ForgeDirection.EAST;
import static net.minecraftforge.common.ForgeDirection.NORTH;
import static net.minecraftforge.common.ForgeDirection.SOUTH;
import static net.minecraftforge.common.ForgeDirection.UP;
import static net.minecraftforge.common.ForgeDirection.WEST;
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
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hX, float hY, float hZ, int meta){
		return side;
	}
	
	private boolean isValidPosition(World w, int x, int y, int z){
		boolean yNeg = Helper.isSolid(w, x, y-1, z, Helper.dirYPos);
		boolean yPos = Helper.isSolid(w, x, y+1, z, Helper.dirYNeg);
		boolean zNeg = Helper.isSolid(w, x, y, z-1, Helper.dirZPos);
		boolean zPos = Helper.isSolid(w, x, y, z+1, Helper.dirZNeg);
		boolean xNeg = Helper.isSolid(w, x-1, y, z, Helper.dirXPos);
		boolean xPos = Helper.isSolid(w, x+1, y, z, Helper.dirXNeg);
		return yNeg || yPos || zNeg || zPos || xNeg || xPos;
	}
	
	@Override
	public boolean canBlockStay(World w, int x, int y, int z){
		return isValidPosition(w, x, y, z);
	}
	
	@Override
	public boolean canPlaceBlockAt(World w, int x, int y, int z){
		return isValidPosition(w, x, y, z);
	}
	
	@Override
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side){
		ForgeDirection dir = ForgeDirection.getOrientation(side);
		return	(dir == DOWN  && world.isBlockSolidOnSide(x, y + 1, z, DOWN )) ||
				(dir == UP    && world.isBlockSolidOnSide(x, y - 1, z, UP   )) ||
				(dir == NORTH && world.isBlockSolidOnSide(x, y, z + 1, NORTH)) ||
				(dir == SOUTH && world.isBlockSolidOnSide(x, y, z - 1, SOUTH)) ||
				(dir == WEST  && world.isBlockSolidOnSide(x + 1, y, z, WEST )) ||
				(dir == EAST  && world.isBlockSolidOnSide(x - 1, y, z, EAST ));
	}
}