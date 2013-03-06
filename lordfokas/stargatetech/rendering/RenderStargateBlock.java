package lordfokas.stargatetech.rendering;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import lordfokas.stargatetech.StargateTech;
import lordfokas.stargatetech.common.BaseBlockRenderer;
import lordfokas.stargatetech.util.TextureIndex;

/**
 * Sits back and relaxes. Stargate blocks are not to be rendered.
 * Makes the Stargate Item render in 2D.
 * @author LordFokas
 */
public class RenderStargateBlock extends BaseBlockRenderer {
	private static RenderStargateBlock INSTANCE = new RenderStargateBlock();
	
	public static RenderStargateBlock instance(){
		return INSTANCE;
	}
	
	@Override
	public boolean shouldRender3DInInventory(){
		return false;
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks r){
		return false;
	}
}
