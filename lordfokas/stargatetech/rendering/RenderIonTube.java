package lordfokas.stargatetech.rendering;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import lordfokas.stargatetech.StargateTech;
import lordfokas.stargatetech.common.BaseBlockRenderer;
import lordfokas.stargatetech.networks.ion.IIonNetComponent;
import lordfokas.stargatetech.util.Helper;
import lordfokas.stargatetech.util.TextureIndex;

public class RenderIonTube  extends BaseBlockRenderer{
	private static RenderIonTube INSTANCE = new RenderIonTube();
	
	public static RenderIonTube instance(){
		return INSTANCE;
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer){
		float min = 0.5F - 0.1875F;
		float max = 0.5F + 0.1875F;
		block.setBlockBounds(min, 0.0F, min, max, 1.0F, max);
		renderer.func_83018_a(block);
		renderAsItem(block, renderer);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer){
		float coremin = 0.5F - 0.1875F;
		float coremax = 0.5F + 0.1875F;
		float bordermin = 0.0F;
		float bordermax = 1.0F;
		int joints = 0;
		
		if(Helper.getBlockInstance(world, x-1, y, z) instanceof IIonNetComponent){
			if(((IIonNetComponent)Helper.getBlockInstance(world, x-1, y, z)).canTubeConnectOnSide(world, x-1, y, z, Helper.dirXPos)){
				block.setBlockBounds(bordermin, coremin, coremin, coremin, coremax, coremax);
				renderer.func_83018_a(block);
				renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, 1.0F, 1.0F, 1.0F);
				joints++;
			}
		}
		if(Helper.getBlockInstance(world, x+1, y, z) instanceof IIonNetComponent){
			if(((IIonNetComponent)Helper.getBlockInstance(world, x+1, y, z)).canTubeConnectOnSide(world, x+1, y, z, Helper.dirXNeg)){
				block.setBlockBounds(coremax, coremin, coremin, bordermax, coremax, coremax);
				renderer.func_83018_a(block);
				renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, 1.0F, 1.0F, 1.0F);
				joints++;
			}
		}
		if(Helper.getBlockInstance(world, x, y-1, z) instanceof IIonNetComponent){
			if(((IIonNetComponent)Helper.getBlockInstance(world, x, y-1, z)).canTubeConnectOnSide(world, x, y-1, z, Helper.dirYPos)){
				block.setBlockBounds(coremin, bordermin, coremin, coremax, coremin, coremax);
				renderer.func_83018_a(block);
				renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, 1.0F, 1.0F, 1.0F);
				joints++;
			}
		}
		if(Helper.getBlockInstance(world, x, y+1, z) instanceof IIonNetComponent){
			if(((IIonNetComponent)Helper.getBlockInstance(world, x, y+1, z)).canTubeConnectOnSide(world, x, y+1, z, Helper.dirYNeg)){
				block.setBlockBounds(coremin, coremax, coremin, coremax, bordermax, coremax);
				renderer.func_83018_a(block);
				renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, 1.0F, 1.0F, 1.0F);
				joints++;
			}
		}
		if(Helper.getBlockInstance(world, x, y, z-1) instanceof IIonNetComponent){
			if(((IIonNetComponent)Helper.getBlockInstance(world, x, y, z-1)).canTubeConnectOnSide(world, x, y, z-1, Helper.dirZPos)){
				block.setBlockBounds(coremin, coremin, bordermin, coremax, coremax, coremin);
				renderer.func_83018_a(block);
				renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, 1.0F, 1.0F, 1.0F);
				joints++;
			}
		}
		if(Helper.getBlockInstance(world, x, y, z+1) instanceof IIonNetComponent){
			if(((IIonNetComponent)Helper.getBlockInstance(world, x, y, z+1)).canTubeConnectOnSide(world, x, y, z+1, Helper.dirZNeg)){
				block.setBlockBounds(coremin, coremin, coremax, coremax, coremax, bordermax);
				renderer.func_83018_a(block);
				renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, 1.0F, 1.0F, 1.0F);
				joints++;
			}
		}
		if(joints > 2){
			coremin -= 0.0625F;
			coremax += 0.0625F;
			block.setBlockBounds(coremin, coremin, coremin, coremax, coremax, coremax);
			renderer.func_83018_a(block);
			renderer.renderNorthFace(block, x, y, z, TextureIndex.ionTubeJoint);
			renderer.renderSouthFace(block, x, y, z, TextureIndex.ionTubeJoint);
			renderer.renderWestFace(block, x, y, z, TextureIndex.ionTubeJoint);
			renderer.renderEastFace(block, x, y, z, TextureIndex.ionTubeJoint);
			renderer.renderTopFace(block, x, y, z, TextureIndex.ionTubeJoint);
			renderer.renderBottomFace(block, x, y, z, TextureIndex.ionTubeJoint);
		}else{
			block.setBlockBounds(coremin, coremin, coremin, coremax, coremax, coremax);
			renderer.func_83018_a(block);
			renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, 1.0F, 1.0F, 1.0F);
		}
		block.setBlockBoundsBasedOnState(world, x, y, z);
		return true;
	}
}