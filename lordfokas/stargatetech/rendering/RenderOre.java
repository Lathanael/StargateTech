package lordfokas.stargatetech.rendering;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import lordfokas.stargatetech.StargateTech;
import lordfokas.stargatetech.common.BaseBlockRenderer;
import lordfokas.stargatetech.util.TextureIndex;

/**
 * Renders Naquadah and Naquadria ore blocks.
 * It may have some issues. See issue list on GitHub.
 * @author LordFokas
 */
public class RenderOre extends BaseBlockRenderer {
	private static RenderOre INSTANCE = new RenderOre();
	
	public static RenderOre instance(){
		return INSTANCE;
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks r){
		int base = TextureIndex.oreBase;
		int ore = (block.blockID == StargateTech.naquadahOre.blockID) ? TextureIndex.naquadahGlow : TextureIndex.naquadriaGlow;
		r.overrideBlockTexture = base;
		r.renderStandardBlock(block, x, y, z);
		r.overrideBlockTexture = -1;
		renderBlock(r, x, y, z, ore);
		return false;
	}
}
