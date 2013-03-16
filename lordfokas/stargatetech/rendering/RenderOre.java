package lordfokas.stargatetech.rendering;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import lordfokas.stargatetech.StargateTech;
import lordfokas.stargatetech.common.BaseBlockRenderer;
import lordfokas.stargatetech.util.IconRegistry;

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
		Icon base = IconRegistry.oreBase;
		Icon ore = (block.blockID == StargateTech.naquadahOre.blockID)?
				IconRegistry.naquadahOreGlow : IconRegistry.naquadriaOreGlow;
		r.overrideBlockTexture = base;
		r.renderStandardBlock(block, x, y, z);
		r.overrideBlockTexture = ore;
		block.setLightValue(15);
		r.renderStandardBlock(block, x, y, z);
		block.setLightValue(0);
		r.overrideBlockTexture = null;
		return false;
	}
}
