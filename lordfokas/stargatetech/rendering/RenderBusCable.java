package lordfokas.stargatetech.rendering;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import lordfokas.stargatetech.common.BaseBlock;
import lordfokas.stargatetech.common.BaseBlockRenderer;
import lordfokas.stargatetech.networks.bus.BusBlock.IBusComponent;
import lordfokas.stargatetech.util.Helper;
import lordfokas.stargatetech.util.IconRegistry;

public class RenderBusCable extends BaseBlockRenderer {
	private static RenderBusCable INSTANCE = new RenderBusCable();
	private Icon base, joint, runX, runY, runZ;
	
	public static RenderBusCable instance(){
		return INSTANCE;
	}
	
	@Override
	public boolean shouldRender3DInInventory(){
		return false;
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block tile, int modelID, RenderBlocks r){
		int direction = Helper.oppositeDirection(world.getBlockMetadata(x, y, z));
		BaseBlock block = (BaseBlock) tile;
		base = IconRegistry.busCable;
		runX = IconRegistry.busCableX;
		runY = IconRegistry.busCableY;
		runZ = IconRegistry.busCableZ;
		joint = IconRegistry.busCableJoint;
		switch(direction){
			case Helper.dirYNeg:
				renderYNeg(world, x, y, z, block, r);
				break;
			case Helper.dirYPos:
				renderYPos(world, x, y, z, block, r);
				break;
			case Helper.dirZNeg:
				renderZNeg(world, x, y, z, block, r);
				break;
			case Helper.dirZPos:
				renderZPos(world, x, y, z, block, r);
				break;
			case Helper.dirXNeg:
				renderXNeg(world, x, y, z, block, r);
				break;
			case Helper.dirXPos:
				renderXPos(world, x, y, z, block, r);
				break;
		}
		return false;
	}

	private void renderYNeg(IBlockAccess world, int x, int y, int z, BaseBlock block, RenderBlocks r){
		boolean hasX = false;
		boolean hasZ = false;
		int sideConn = 0;
		Icon[] map = new Icon[]{base, runX, base, base, base, base};
		block.overrideTextures(map);
		Block target = Helper.getBlockInstance(world, x+1, y, z);
		if(target instanceof IBusComponent){
			IBusComponent component = (IBusComponent) target;
			if(component.canBusPlugOnSide(world, x+1, y, z, Helper.dirXNeg)){
				block.setBlockBounds(0.6875F, 0F, 0.3125F, 1F, 0.0625F, 0.6875F);
				r.setRenderBoundsFromBlock(block);
				r.renderStandardBlock(block, x, y, z);
				hasX = true;
				sideConn++;
			}
		}
		target = Helper.getBlockInstance(world, x-1, y, z);
		if(target instanceof IBusComponent){
			IBusComponent component = (IBusComponent) target;
			if(component.canBusPlugOnSide(world, x-1, y, z, Helper.dirXPos)){
				block.setBlockBounds(0F, 0F, 0.3125F, 0.3125F, 0.0625F, 0.6875F);
				r.setRenderBoundsFromBlock(block);
				r.renderStandardBlock(block, x, y, z);
				hasX = true;
				sideConn++;
			}
		}
		map[1] = runZ;
		block.overrideTextures(map);
		target = Helper.getBlockInstance(world, x, y, z+1);
		if(target instanceof IBusComponent){
			IBusComponent component = (IBusComponent) target;
			if(component.canBusPlugOnSide(world, x, y, z+1, Helper.dirZNeg)){
				block.setBlockBounds(0.3125F, 0F, 0.6875F, 0.6875F, 0.0625F, 1F);
				r.setRenderBoundsFromBlock(block);
				r.renderStandardBlock(block, x, y, z);
				hasZ = true;
				sideConn++;
			}
		}
		target = Helper.getBlockInstance(world, x, y, z-1);
		if(target instanceof IBusComponent){
			IBusComponent component = (IBusComponent) target;
			if(component.canBusPlugOnSide(world, x, y, z-1, Helper.dirZPos)){
				block.setBlockBounds(0.3125F, 0F, 0F, 0.6875F, 0.0625F, 0.3125F);
				r.setRenderBoundsFromBlock(block);
				r.renderStandardBlock(block, x, y, z);
				hasZ = true;
				sideConn++;
			}
		}
		boolean hasBelow = false;
		target = Helper.getBlockInstance(world, x, y-1, z);
		if(target instanceof IBusComponent){
			IBusComponent component = (IBusComponent) target;
			hasBelow = component.canBusPlugOnSide(world, x, y-1, z, Helper.dirYPos);
		}
		if(sideConn < 2 || hasBelow){
			map[1] = joint;
		}else if(hasX && !hasZ){
			map[1] = runX;
		}else if(hasZ && !hasX){
			map[1] = runZ;
		}else{
			map[1] = joint;
		}
		block.overrideTextures(map);
		block.setBlockBounds(0.3125F, 0F, 0.3125F, 0.6875F, 0.0625F, 0.6875F);
		r.setRenderBoundsFromBlock(block);
		r.renderStandardBlock(block, x, y, z);
		block.restoreTextures();
	}
	
	private void renderYPos(IBlockAccess world, int x, int y, int z, BaseBlock block, RenderBlocks r) {
		// TODO Auto-generated method stub
	}

	private void renderZNeg(IBlockAccess world, int x, int y, int z, BaseBlock block, RenderBlocks r) {
		Icon[] map = new Icon[]{base, base, base, runZ, base, base};
		block.overrideTextures(map);
		block.setBlockBounds(0.3125F, 0F, 0F, 0.6875F, 1F, 0.0625F);
		r.setRenderBoundsFromBlock(block);
		r.renderStandardBlock(block, x, y, z);
		block.restoreTextures();
	}

	private void renderZPos(IBlockAccess world, int x, int y, int z, BaseBlock block, RenderBlocks r) {
		Icon[] map = new Icon[]{base, base, runY, base, base, base};
		block.overrideTextures(map);
		block.setBlockBounds(0.3125F, 0F, 0.9375F, 0.6875F, 1F, 1F);
		r.setRenderBoundsFromBlock(block);
		r.renderStandardBlock(block, x, y, z);
		block.restoreTextures();
	}

	private void renderXNeg(IBlockAccess world, int x, int y, int z, BaseBlock block, RenderBlocks r) {
		Icon[] map = new Icon[]{base, base, base, base, base, runZ};
		block.overrideTextures(map);
		block.setBlockBounds(0F, 0F, 0.3125F, 0.0625F, 1F, 0.6875F);
		r.setRenderBoundsFromBlock(block);
		r.renderStandardBlock(block, x, y, z);
		block.restoreTextures();
	}

	private void renderXPos(IBlockAccess world, int x, int y, int z, BaseBlock block, RenderBlocks r) {
		Icon[] map = new Icon[]{base, base, base, base, runY, base};
		block.overrideTextures(map);
		block.setBlockBounds(0.9375F, 0F, 0.3125F, 1F, 1F, 0.6875F);
		r.setRenderBoundsFromBlock(block);
		r.renderStandardBlock(block, x, y, z);
		block.restoreTextures();
	}
}