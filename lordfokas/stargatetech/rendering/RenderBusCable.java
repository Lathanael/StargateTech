package lordfokas.stargatetech.rendering;

import java.util.List;

import lordfokas.stargatetech.common.BaseBlock;
import lordfokas.stargatetech.common.BaseBlockRenderer;
import lordfokas.stargatetech.machine.BusCable;
import lordfokas.stargatetech.networks.bus.BusConnection;
import lordfokas.stargatetech.util.Helper;
import lordfokas.stargatetech.util.IconRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

public class RenderBusCable extends BaseBlockRenderer {
	private static RenderBusCable INSTANCE = new RenderBusCable();
	private Icon base, joint, runX, runY, runZ;
	private Icon[] vmap;
	
	public static RenderBusCable instance(){
		return INSTANCE;
	}
	
	@Override
	public boolean shouldRender3DInInventory(){
		return false;
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks r){
		int direction = world.getBlockMetadata(x, y, z);
		BusCable cable = (BusCable) block;
		base = IconRegistry.busCable;
		runX = IconRegistry.busCableX;
		runY = IconRegistry.busCableY;
		runZ = IconRegistry.busCableZ;
		joint = IconRegistry.busCableJoint;
		List<BusConnection> links = cable.getConnectionsList(world, x, y, z);
		switch(direction){
			case Helper.dirYNeg:
				renderYNeg(world, x, y, z, cable, r, links);
				break;
			case Helper.dirYPos:
				renderYPos(world, x, y, z, cable, r, links);
				break;
			case Helper.dirZNeg:
				renderZNeg(world, x, y, z, cable, r, links);
				break;
			case Helper.dirZPos:
				renderZPos(world, x, y, z, cable, r, links);
				break;
			case Helper.dirXNeg:
				renderXNeg(world, x, y, z, cable, r, links);
				break;
			case Helper.dirXPos:
				renderXPos(world, x, y, z, cable, r, links);
				break;
		}
		return false;
	}
	
	private void renderYNeg(IBlockAccess world, int x, int y, int z, BaseBlock block, RenderBlocks r, List<BusConnection> links){
		boolean hasYNeg = false;
		boolean hasZ = false;
		boolean hasX = false;
		Icon[] map = new Icon[]{base, base, base, base, base, base};
		for(BusConnection conn : links){
			if(conn.sourceSide == Helper.dirYNeg){
				hasYNeg = true;
				break;
			}else if(conn.sourceSide == Helper.dirXNeg || conn.sourceSide == Helper.dirXPos){
				hasX = true;
			}else if(conn.sourceSide == Helper.dirZNeg || conn.sourceSide == Helper.dirZPos){
				hasZ = true;
			}
		}
		if(hasYNeg || links.size() < 2){
			map[1] = joint;
		}else if(hasZ && !hasX){
			map[1] = runZ;
		}else if(hasX && !hasZ){
			map[1] = runX;
		}else{
			map[1] = joint;
		}
		block.overrideTextures(map);
		block.setBlockBounds(0.3125F, 0F, 0.3125F, 0.6875F, 0.0625F, 0.6875F);
		r.setRenderBoundsFromBlock(block);
		r.renderStandardBlock(block, x, y, z);
		for(BusConnection conn : links){
			switch(conn.sourceSide){
				case Helper.dirZNeg:
					block.setBlockBounds(0.3125F, 0F, 0F, 0.6875F, 0.0625F, 0.3125F);
					map[1] = runZ;
					break;
				case Helper.dirZPos:
					block.setBlockBounds(0.3125F, 0F, 0.6875F, 0.6875F, 0.0625F, 1F);
					map[1] = runZ;
					break;
				case Helper.dirXNeg:
					block.setBlockBounds(0F, 0F, 0.3125F, 0.3125F, 0.0625F, 0.6875F);
					map[1] = runX;
					break;
				case Helper.dirXPos:
					block.setBlockBounds(0.6875F, 0F, 0.3125F, 1F, 0.0625F, 0.6875F);
					map[1] = runX;
					break;
			}
			block.overrideTextures(map);
			r.setRenderBoundsFromBlock(block);
			r.renderStandardBlock(block, x, y, z);
			if(conn.cornerIn){
				vmap = new Icon[]{base, base, base, base, base, base};
				switch(conn.sourceSide){
					case Helper.dirZNeg:
						block.setBlockBounds(0.3125F, 0.0625F, 0F, 0.6875F, 1F, 0.0625F);
						vmap[3] = runZ;
						break;
					case Helper.dirZPos:
						block.setBlockBounds(0.3125F, 0.0625F, 0.9375F, 0.6875F, 1F, 1F);
						vmap[2] = runY;
						break;
					case Helper.dirXNeg:
						block.setBlockBounds(0F, 0.0625F, 0.3125F, 0.0625F, 1F, 0.6875F);
						vmap[5] = runZ;
						break;
					case Helper.dirXPos:
						block.setBlockBounds(0.9375F, 0.0625F, 0.3125F, 1F, 1F, 0.6875F);
						vmap[4] = runY;
						break;
				}
				block.overrideTextures(vmap);
				r.setRenderBoundsFromBlock(block);
				r.renderStandardBlock(block, x, y, z);
			}
		}
		block.restoreTextures();
	}
	
	private void renderYPos(IBlockAccess world, int x, int y, int z, BaseBlock block, RenderBlocks r, List<BusConnection> links) {
		boolean hasYPos = false;
		boolean hasZ = false;
		boolean hasX = false;
		Icon[] map = new Icon[]{base, base, base, base, base, base};
		for(BusConnection conn : links){
			if(conn.sourceSide == Helper.dirYPos){
				hasYPos = true;
				break;
			}else if(conn.sourceSide == Helper.dirXNeg || conn.sourceSide == Helper.dirXPos){
				hasX = true;
			}else if(conn.sourceSide == Helper.dirZNeg || conn.sourceSide == Helper.dirZPos){
				hasZ = true;
			}
		}
		if(hasYPos || links.size() < 2){
			map[0] = joint;
		}else if(hasZ && !hasX){
			map[0] = runZ;
		}else if(hasX && !hasZ){
			map[0] = runX;
		}else{
			map[0] = joint;
		}
		block.overrideTextures(map);
		block.setBlockBounds(0.3125F, 0.9375F, 0.3125F, 0.6875F, 1F, 0.6875F);
		r.setRenderBoundsFromBlock(block);
		r.renderStandardBlock(block, x, y, z);
		for(BusConnection conn : links){
			switch(conn.sourceSide){
				case Helper.dirZNeg:
					block.setBlockBounds(0.3125F, 0.9375F, 0F, 0.6875F, 1F, 0.3125F);
					map[0] = runZ;
					break;
				case Helper.dirZPos:
					block.setBlockBounds(0.3125F, 0.9375F, 0.6875F, 0.6875F, 1F, 1F);
					map[0] = runZ;
					break;
				case Helper.dirXNeg:
					block.setBlockBounds(0F, 0.9375F, 0.3125F, 0.3125F, 1F, 0.6875F);
					map[0] = runX;
					break;
				case Helper.dirXPos:
					block.setBlockBounds(0.6875F, 0.9375F, 0.3125F, 1F, 1F, 0.6875F);
					map[0] = runX;
					break;
			}
			block.overrideTextures(map);
			r.setRenderBoundsFromBlock(block);
			r.renderStandardBlock(block, x, y, z);
			if(conn.cornerIn){
				vmap = new Icon[]{base, base, base, base, base, base};
				switch(conn.sourceSide){
					case Helper.dirZNeg:
						block.setBlockBounds(0.3125F, 0F, 0F, 0.6875F, 0.9375F, 0.0625F);
						vmap[3] = runZ;
						break;
					case Helper.dirZPos:
						block.setBlockBounds(0.3125F, 0F, 0.9375F, 0.6875F, 0.9375F, 1F);
						vmap[2] = runY;
						break;
					case Helper.dirXNeg:
						block.setBlockBounds(0F, 0F, 0.3125F, 0.0625F, 0.9375F, 0.6875F);
						vmap[5] = runZ;
						break;
					case Helper.dirXPos:
						block.setBlockBounds(0.9375F, 0F, 0.3125F, 0.9375F, 1F, 0.6875F);
						vmap[4] = runY;
						break;
				}
				block.overrideTextures(vmap);
				r.setRenderBoundsFromBlock(block);
				r.renderStandardBlock(block, x, y, z);
			}
		}
		block.restoreTextures();
	}
	
	private void renderZNeg(IBlockAccess world, int x, int y, int z, BaseBlock block, RenderBlocks r, List<BusConnection> links) {
		boolean hasZNeg = false;
		boolean hasY = false;
		boolean hasX = false;
		Icon[] map = new Icon[]{base, base, base, base, base, base};
		for(BusConnection conn : links){
			if(conn.sourceSide == Helper.dirZNeg){
				hasZNeg = true;
				break;
			}else if(conn.sourceSide == Helper.dirXNeg || conn.sourceSide == Helper.dirXPos){
				hasX = true;
			}else if(conn.sourceSide == Helper.dirYNeg || conn.sourceSide == Helper.dirYPos){
				hasY = true;
			}
		}
		if(hasZNeg || links.size() < 2){
			map[3] = joint;
		}else if(hasY && !hasX){
			map[3] = runZ;
		}else if(hasX && !hasY){
			map[3] = runX;
		}else{
			map[3] = joint;
		}
		block.overrideTextures(map);
		block.setBlockBounds(0.3125F, 0.3125F, 0F, 0.6875F, 0.6875F, 0.0625F);
		r.setRenderBoundsFromBlock(block);
		r.renderStandardBlock(block, x, y, z);
		for(BusConnection conn : links){
			switch(conn.sourceSide){
				case Helper.dirYNeg:
					block.setBlockBounds(0.3125F, 0F, 0F, 0.6875F, 0.3125F, 0.0625F);
					map[3] = runZ;
					break;
				case Helper.dirYPos:
					block.setBlockBounds(0.3125F, 0.6875F, 0F, 0.6875F, 1F, 0.0625F);
					map[3] = runZ;
					break;
				case Helper.dirXNeg:
					block.setBlockBounds(0F, 0.3125F, 0F, 0.3125F, 0.6875F, 0.0625F);
					map[3] = runX;
					break;
				case Helper.dirXPos:
					block.setBlockBounds(0.6875F, 0.3125F, 0F, 1F, 0.6875F, 0.0625F);
					map[3] = runX;
					break;
			}
			block.overrideTextures(map);
			r.setRenderBoundsFromBlock(block);
			r.renderStandardBlock(block, x, y, z);
			if(conn.cornerIn){
				vmap = new Icon[]{base, base, base, base, base, base};
				switch(conn.sourceSide){
					case Helper.dirYNeg:
						block.setBlockBounds(0.3125F, 0F, 0.0625F, 0.6875F, 0.0625F, 1F);
						vmap[1] = runZ;
						break;
					case Helper.dirYPos:
						block.setBlockBounds(0.3125F, 0.9375F, 0.0625F, 0.6875F, 1F, 1F);
						vmap[0] = runZ;
						break;
					case Helper.dirXNeg:
						block.setBlockBounds(0F, 0.3125F, 0.0625F, 0.0625F, 0.6875F, 1F);
						vmap[5] = runX;
						break;
					case Helper.dirXPos:
						block.setBlockBounds(0.9375F, 0.3125F, 0.0625F, 1F, 0.6875F, 1F);
						vmap[4] = runX;
						break;
				}
				block.overrideTextures(vmap);
				r.setRenderBoundsFromBlock(block);
				r.renderStandardBlock(block, x, y, z);
			}
		}
		block.restoreTextures();
	}
	
	private void renderZPos(IBlockAccess world, int x, int y, int z, BaseBlock block, RenderBlocks r, List<BusConnection> links) {
		boolean hasZPos = false;
		boolean hasY = false;
		boolean hasX = false;
		Icon[] map = new Icon[]{base, base, base, base, base, base};
		for(BusConnection conn : links){
			if(conn.sourceSide == Helper.dirZPos){
				hasZPos = true;
				break;
			}else if(conn.sourceSide == Helper.dirXNeg || conn.sourceSide == Helper.dirXPos){
				hasX = true;
			}else if(conn.sourceSide == Helper.dirYNeg || conn.sourceSide == Helper.dirYPos){
				hasY = true;
			}
		}
		if(hasZPos || links.size() < 2){
			map[2] = joint;
		}else if(hasY && !hasX){
			map[2] = runY;
		}else if(hasX && !hasY){
			map[2] = runX;
		}else{
			map[2] = joint;
		}
		block.overrideTextures(map);
		block.setBlockBounds(0.3125F, 0.3125F, 0.9375F, 0.6875F, 0.6875F, 1F);
		r.setRenderBoundsFromBlock(block);
		r.renderStandardBlock(block, x, y, z);
		for(BusConnection conn : links){
			switch(conn.sourceSide){
				case Helper.dirYNeg:
					block.setBlockBounds(0.3125F, 0F, 0.9375F, 0.6875F, 0.3125F, 1F);
					map[2] = runY;
					break;
				case Helper.dirYPos:
					block.setBlockBounds(0.3125F, 0.6875F, 0.9375F, 0.6875F, 1F, 1F);
					map[2] = runY;
					break;
				case Helper.dirXNeg:
					block.setBlockBounds(0F, 0.3125F, 0.9375F, 0.3125F, 0.6875F, 1F);
					map[2] = runX;
					break;
				case Helper.dirXPos:
					block.setBlockBounds(0.6875F, 0.3125F, 0.9375F, 1F, 0.6875F, 1F);
					map[2] = runX;
					break;
			}
			block.overrideTextures(map);
			r.setRenderBoundsFromBlock(block);
			r.renderStandardBlock(block, x, y, z);
			if(conn.cornerIn){
				vmap = new Icon[]{base, base, base, base, base, base};
				switch(conn.sourceSide){
					case Helper.dirYNeg:
						block.setBlockBounds(0.3125F, 0F, 0F, 0.6875F, 0.0625F, 0.9375F);
						vmap[1] = runZ;
						break;
					case Helper.dirYPos:
						block.setBlockBounds(0.3125F, 0.9375F, 0F, 0.6875F, 1F, 0.9375F);
						vmap[0] = runZ;
						break;
					case Helper.dirXNeg:
						block.setBlockBounds(0F, 0.3125F, 0F, 0.0625F, 0.6875F, 0.9375F);
						vmap[5] = runX;
						break;
					case Helper.dirXPos:
						block.setBlockBounds(0.9375F, 0.3125F, 0F, 1F, 0.6875F, 0.9375F);
						vmap[4] = runX;
						break;
				}
				block.overrideTextures(vmap);
				r.setRenderBoundsFromBlock(block);
				r.renderStandardBlock(block, x, y, z);
			}
		}
		block.restoreTextures();
	}
	
	private void renderXNeg(IBlockAccess world, int x, int y, int z, BaseBlock block, RenderBlocks r, List<BusConnection> links) {
		boolean hasXNeg = false;
		boolean hasY = false;
		boolean hasZ = false;
		Icon[] map = new Icon[]{base, base, base, base, base, base};
		for(BusConnection conn : links){
			if(conn.sourceSide == Helper.dirXNeg){
				hasXNeg = true;
				break;
			}else if(conn.sourceSide == Helper.dirZNeg || conn.sourceSide == Helper.dirZPos){
				hasZ = true;
			}else if(conn.sourceSide == Helper.dirYNeg || conn.sourceSide == Helper.dirYPos){
				hasY = true;
			}
		}
		if(hasXNeg || links.size() < 2){
			map[5] = joint;
		}else if(hasY && !hasZ){
			map[5] = runZ;
		}else if(hasZ && !hasY){
			map[5] = runX;
		}else{
			map[5] = joint;
		}
		block.overrideTextures(map);
		block.setBlockBounds(0F, 0.3125F, 0.3125F, 0.0625F, 0.6875F, 0.6875F);
		r.setRenderBoundsFromBlock(block);
		r.renderStandardBlock(block, x, y, z);
		for(BusConnection conn : links){
			switch(conn.sourceSide){
				case Helper.dirYNeg:
					block.setBlockBounds(0F, 0F, 0.3125F, 0.0625F, 0.3125F, 0.6875F);
					map[5] = runZ;
					break;
				case Helper.dirYPos:
					block.setBlockBounds(0F, 0.6875F, 0.3125F, 0.0625F, 1F, 0.6875F);
					map[5] = runZ;
					break;
				case Helper.dirZNeg:
					block.setBlockBounds(0F, 0.3125F, 0F, 0.0625F, 0.6875F, 0.3125F);
					map[5] = runX;
					break;
				case Helper.dirZPos:
					block.setBlockBounds(0F, 0.3125F, 0.6875F, 0.0625F, 0.6875F, 1F);
					map[5] = runX;
					break;
			}
			block.overrideTextures(map);
			r.setRenderBoundsFromBlock(block);
			r.renderStandardBlock(block, x, y, z);
			if(conn.cornerIn){
				vmap = new Icon[]{base, base, base, base, base, base};
				switch(conn.sourceSide){
					case Helper.dirYNeg:
						block.setBlockBounds(0.0625F, 0F, 0.3125F, 1F, 0.0625F, 0.6875F);
						vmap[1] = runX;
						break;
					case Helper.dirYPos:
						block.setBlockBounds(0.0625F, 0.9375F, 0.3125F, 1F, 1F, 0.6875F);
						vmap[0] = runX;
						break;
					case Helper.dirZNeg:
						block.setBlockBounds(0.0625F, 0.3125F, 0F, 1F, 0.6875F, 0.0625F);
						vmap[3] = runX;
						break;
					case Helper.dirZPos:
						block.setBlockBounds(0.0625F, 0.3125F, 0.9375F, 1F, 0.6875F, 1F);
						vmap[2] = runX;
						break;
				}
				block.overrideTextures(vmap);
				r.setRenderBoundsFromBlock(block);
				r.renderStandardBlock(block, x, y, z);
			}
		}
		block.restoreTextures();
	}
	
	private void renderXPos(IBlockAccess world, int x, int y, int z, BaseBlock block, RenderBlocks r, List<BusConnection> links) {
		boolean hasXPos = false;
		boolean hasY = false;
		boolean hasZ = false;
		Icon[] map = new Icon[]{base, base, base, base, base, base};
		for(BusConnection conn : links){
			if(conn.sourceSide == Helper.dirXPos){
				hasXPos = true;
				break;
			}else if(conn.sourceSide == Helper.dirZNeg || conn.sourceSide == Helper.dirZPos){
				hasZ = true;
			}else if(conn.sourceSide == Helper.dirYNeg || conn.sourceSide == Helper.dirYPos){
				hasY = true;
			}
		}
		if(hasXPos || links.size() < 2){
			map[4] = joint;
		}else if(hasY && !hasZ){
			map[4] = runY;
		}else if(hasZ && !hasY){
			map[4] = runX;
		}else{
			map[4] = joint;
		}
		block.overrideTextures(map);
		block.setBlockBounds(0.9375F, 0.3125F, 0.3125F, 1F, 0.6875F, 0.6875F);
		r.setRenderBoundsFromBlock(block);
		r.renderStandardBlock(block, x, y, z);
		for(BusConnection conn : links){
			switch(conn.sourceSide){
				case Helper.dirYNeg:
					block.setBlockBounds(0.9375F, 0F, 0.3125F, 1F, 0.3125F, 0.6875F);
					map[4] = runY;
					break;
				case Helper.dirYPos:
					block.setBlockBounds(0.9375F, 0.6875F, 0.3125F, 1F, 1F, 0.6875F);
					map[4] = runY;
					break;
				case Helper.dirZNeg:
					block.setBlockBounds(0.9375F, 0.3125F, 0F, 1F, 0.6875F, 0.3125F);
					map[4] = runX;
					break;
				case Helper.dirZPos:
					block.setBlockBounds(0.9375F, 0.3125F, 0.6875F, 1F, 0.6875F, 1F);
					map[4] = runX;
					break;
			}
			block.overrideTextures(map);
			r.setRenderBoundsFromBlock(block);
			r.renderStandardBlock(block, x, y, z);
			if(conn.cornerIn){
				vmap = new Icon[]{base, base, base, base, base, base};
				switch(conn.sourceSide){
					case Helper.dirYNeg:
						block.setBlockBounds(0F, 0F, 0.3125F, 0.9375F, 0.0625F, 0.6875F);
						vmap[1] = runX;
						break;
					case Helper.dirYPos:
						block.setBlockBounds(0F, 0.9375F, 0.3125F, 0.9375F, 1F, 0.6875F);
						vmap[0] = runX;
						break;
					case Helper.dirZNeg:
						block.setBlockBounds(0F, 0.3125F, 0F, 0.9375F, 0.6875F, 0.0625F);
						vmap[3] = runX;
						break;
					case Helper.dirZPos:
						block.setBlockBounds(0F, 0.3125F, 0.9375F, 0.9375F, 0.6875F, 1F);
						vmap[2] = runX;
						break;
				}
				block.overrideTextures(vmap);
				r.setRenderBoundsFromBlock(block);
				r.renderStandardBlock(block, x, y, z);
			}
		}
		block.restoreTextures();
	}
}