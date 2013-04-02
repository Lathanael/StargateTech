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
	
	public static RenderBusCable instance(){
		return INSTANCE;
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block tile, int modelId, RenderBlocks r){
		BaseBlock block = (BaseBlock) tile;
		boolean hasX = false;
		boolean hasZ = false;
		Icon base = IconRegistry.busCable;
		Icon runX = IconRegistry.busCableX;
		Icon runZ = IconRegistry.busCableZ;
		Icon joint = IconRegistry.busCableJoint;
		Icon[] map = new Icon[]{base, runX, base, base, base, base};
		
		block.overrideTextures(map);
		Block target = Helper.getBlockInstance(world, x+1, y, z);
		if(target instanceof IBusComponent){
			IBusComponent component = (IBusComponent) target;
			if(component.canBusPlugOnSide(world, x, y, z, Helper.dirXNeg)){
				block.setBlockBounds(0.6825F, 0F, 0.3125F, 1F, 0.0625F, 0.6825F);
				r.setRenderBoundsFromBlock(block);
				r.renderStandardBlock(block, x, y, z);
				hasX = true;
			}
		}
		target = Helper.getBlockInstance(world, x-1, y, z);
		if(target instanceof IBusComponent){
			IBusComponent component = (IBusComponent) target;
			if(component.canBusPlugOnSide(world, x, y, z, Helper.dirXPos)){
				block.setBlockBounds(0F, 0F, 0.3125F, 0.3125F, 0.0625F, 0.6825F);
				r.setRenderBoundsFromBlock(block);
				r.renderStandardBlock(block, x, y, z);
				hasX = true;
			}
		}
		
		
		
		
		map[1] = runZ;
		block.overrideTextures(map);
		target = Helper.getBlockInstance(world, x, y, z+1);
		if(target instanceof IBusComponent){
			IBusComponent component = (IBusComponent) target;
			if(component.canBusPlugOnSide(world, x, y, z, Helper.dirZNeg)){
				block.setBlockBounds(0.3125F, 0F, 0.6825F, 0.6825F, 0.0625F, 1F);
				r.setRenderBoundsFromBlock(block);
				r.renderStandardBlock(block, x, y, z);
				hasZ = true;
			}
		}
		target = Helper.getBlockInstance(world, x, y, z-1);
		if(target instanceof IBusComponent){
			IBusComponent component = (IBusComponent) target;
			if(component.canBusPlugOnSide(world, x, y, z, Helper.dirZPos)){
				block.setBlockBounds(0.3125F, 0F, 0F, 0.6825F, 0.0625F, 0.3125F);
				r.setRenderBoundsFromBlock(block);
				r.renderStandardBlock(block, x, y, z);
				hasZ = true;
			}
		}
		
		if(hasX && !hasZ){
			map[1] = runX;
		}else if(hasZ && !hasX){
			map[1] = runZ;
		}else{
			map[1] = joint;
		}
		block.overrideTextures(map);
		block.setBlockBounds(0.3125F, 0F, 0.3125F, 0.6875F, 0.0625F, 0.6825F);
		r.setRenderBoundsFromBlock(block);
		r.renderStandardBlock(block, x, y, z);
		
		return false;
	}
}