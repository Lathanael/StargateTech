package lordfokas.stargatetech.machine;

import static net.minecraftforge.common.ForgeDirection.DOWN;
import static net.minecraftforge.common.ForgeDirection.EAST;
import static net.minecraftforge.common.ForgeDirection.NORTH;
import static net.minecraftforge.common.ForgeDirection.SOUTH;
import static net.minecraftforge.common.ForgeDirection.UP;
import static net.minecraftforge.common.ForgeDirection.WEST;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.ObfuscationReflectionHelper;

import lordfokas.stargatetech.api.networks.BusConnection;
import lordfokas.stargatetech.api.networks.BusBlock.IBusComponent;
import lordfokas.stargatetech.api.networks.BusBlock.IBusPropagator;
import lordfokas.stargatetech.common.BaseBlock;
import lordfokas.stargatetech.rendering.RenderBusCable;
import lordfokas.stargatetech.util.CoordinateSet;
import lordfokas.stargatetech.util.Helper;
import lordfokas.stargatetech.util.StargateLogger;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BusCable extends BaseBlock implements IBusPropagator{
	public BusCable(int id) {
		super(id, "busCable", false);
		texturename = "busCableZ";
	}

	@Override
	public boolean canBusPlugOnSide(IBlockAccess w, int x, int y, int z, int side, int cableFace){
		int meta = w.getBlockMetadata(x, y, z);
		return side != Helper.oppositeDirection(meta) && (cableFace == Helper.dirIgnore || meta == cableFace);
	}
	
	@Override
	public List<BusConnection> getConnectionsList(IBlockAccess blockAccess, int x, int y, int z){
		List<BusConnection> links = new ArrayList<BusConnection>(5);
		World w;
		if(blockAccess instanceof World){
			w = (World) blockAccess;
		}else{
			StargateLogger.severe("An unsupported IBlockAccess object was found!");
			StargateLogger.severe("IBlockAccess class: " + blockAccess.getClass().getName());
			return links;
		}
		int meta = w.getBlockMetadata(x, y, z);
		CoordinateSet position = new CoordinateSet(w, x, y, z);
		CoordinateSet support = position.fromDirection(meta);
		Block spt = Helper.getBlockInstance(w, support.x, support.y, support.z);
		if(spt instanceof IBusComponent){
			IBusComponent component = (IBusComponent) spt;
			int targetSide = Helper.oppositeDirection(meta);
			if(component.canBusPlugOnSide(w, support.x, support.y, support.z, targetSide, -1)){
				links.add(new BusConnection(w, support.x, support.y, support.z, meta, targetSide));
			}
		}
		int[] directions = new int[4];
		switch(meta){
			case Helper.dirYNeg:
			case Helper.dirYPos:
				directions = new int[]{Helper.dirZNeg, Helper.dirZPos, Helper.dirXNeg, Helper.dirXPos};
				break;
			case Helper.dirZNeg:
			case Helper.dirZPos:
				directions = new int[]{Helper.dirYNeg, Helper.dirYPos, Helper.dirXNeg, Helper.dirXPos};
				break;
			case Helper.dirXNeg:
			case Helper.dirXPos:
				directions = new int[]{Helper.dirZNeg, Helper.dirZPos, Helper.dirYNeg, Helper.dirYPos};
				break;
		}
		for(int dir : directions){
			boolean cornerIn = false, cornerOut = false;
			CoordinateSet derivate = position.fromDirection(dir);
			Block block = Helper.getBlockInstance(w, derivate.x, derivate.y, derivate.z);
			boolean shouldConnect = true;
			boolean onSide = true;
			boolean goingLow = false;
			
			// If next block is air search below.
			if(block == null || w.isAirBlock(derivate.x, derivate.y, derivate.z)){
				derivate = derivate.fromDirection(meta);
				block = Helper.getBlockInstance(w, derivate.x, derivate.y, derivate.z);
				CoordinateSet holder = position.fromDirection(meta);
				shouldConnect = Helper.isSolid(w, holder.x, holder.y, holder.z, dir);
				cornerIn = false;
				cornerOut = true;
				onSide = false;
				goingLow = true;
			// If next block is solid search above
			}else if(!(block instanceof IBusComponent) && Helper.isSolid(w, derivate.x, derivate.y, derivate.z, Helper.oppositeDirection(dir))){
				int antimeta = Helper.oppositeDirection(meta);
				onSide = false;
				derivate = position.fromDirection(antimeta);
				block = Helper.getBlockInstance(w, derivate.x, derivate.y, derivate.z);
				// If above is air, check diagonal.
				if(block == null || w.isAirBlock(derivate.x, derivate.y, derivate.z)){
					derivate = derivate.fromDirection(dir);
					block = Helper.getBlockInstance(w, derivate.x, derivate.y, derivate.z);
					cornerIn = cornerOut = true;
				// Or try to connect above.
				}else if(block instanceof IBusComponent){
					IBusComponent component = (IBusComponent) block;
					if(component.canBusPlugOnSide(w, derivate.x, derivate.y, derivate.z, antimeta, dir)){
						BusConnection conn = new BusConnection(derivate.w, derivate.x, derivate.y, derivate.z, dir, antimeta);
						conn.cornerIn = true;
						conn.cornerOut = false;
						links.add(conn);
						shouldConnect = false;
					}
				}
			}else{
				CoordinateSet sideSpt = support.fromDirection(dir);
				Block sspt = Helper.getBlockInstance(w, sideSpt.x, sideSpt.y, sideSpt.z);
				if(sspt == null) continue;
				if(sspt.isBlockSolidOnSide(w, sideSpt.x, sideSpt.y, sideSpt.z, ForgeDirection.getOrientation(meta)) == false) continue;
			}
			int cableFace = -1;
			if(cornerIn && cornerOut){
				cableFace = meta;
			}else if(cornerIn && !cornerOut){
				cableFace = dir;
			}else if(!cornerIn && cornerOut){
				cableFace = Helper.oppositeDirection(dir);
			}else{
				cableFace = meta;
			}
			boolean connected = false;
			for(int i = 0; i < 1; i++){
				if(block instanceof IBusComponent && shouldConnect){
					IBusComponent component = (IBusComponent) block;
					int targetSide = Helper.oppositeDirection(meta);
					if(onSide) cableFace = dir;
					if(component.canBusPlugOnSide(w, derivate.x, derivate.y, derivate.z, targetSide, cableFace)){
						BusConnection conn = new BusConnection(derivate.w, derivate.x, derivate.y, derivate.z, dir, targetSide);
						conn.cornerIn = cornerIn;
						conn.cornerOut = cornerOut;
						links.add(conn);
					}else{
						targetSide = Helper.oppositeDirection(dir);
						if(onSide) cableFace = meta;
						if(component.canBusPlugOnSide(w, derivate.x, derivate.y, derivate.z, targetSide, cableFace)){
							BusConnection conn = new BusConnection(derivate.w, derivate.x, derivate.y, derivate.z, dir, targetSide);
							conn.cornerIn = cornerIn;
							conn.cornerOut = cornerOut;
							links.add(conn);
						}
					}
				}
				if(goingLow && !connected){
					i--;
					goingLow = false;
					onSide = true;
				}
			}
		}
		return links;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z){
		final float min = 1F / 16F;
		final float max = 1F - min;
		float x0, x1, y0, y1, z0, z1;
		x0 = y0 = z0 = 0F;
		x1 = y1 = z1 = 1F;
		switch(world.getBlockMetadata(x,  y, z)){
			case Helper.dirYNeg:
				y1 = min;
				break;
			case Helper.dirYPos:
				y0 = max;
				break;
			case Helper.dirZNeg:
				z1 = min;
				break;
			case Helper.dirZPos:
				z0 = max;
				break;
			case Helper.dirXNeg:
				x1 = min;
				break;
			case Helper.dirXPos:
				x0 = max;
				break;
		}
		this.setBlockBounds(x0, y0, z0, x1, y1, z1);
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
		return Helper.oppositeDirection(side);
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
	
	public void onNeighborBlockChange(World world, int x, int y, int z, int ignored){
		int meta = world.getBlockMetadata(x, y, z);
		if(!canPlaceBlockOnSide(world, x, y, z, Helper.oppositeDirection(meta))){
			this.dropBlockAsItem(world, x, y, z, 0, 0);
	        world.setBlockToAir(x, y, z);
		}
	}
}