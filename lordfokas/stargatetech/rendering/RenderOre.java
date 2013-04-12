package lordfokas.stargatetech.rendering;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
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
		r.overrideBlockTexture = null;
		Tessellator t = Tessellator.instance;
		t.setBrightness(220);
		t.setColorOpaque_I(0xFFFFFF);
		r.renderTopFace(block, x, y, z, ore);
		r.renderBottomFace(block, x, y, z, ore);
		r.renderNorthFace(block, x, y, z, ore);
		r.renderSouthFace(block, x, y, z, ore);
		r.renderEastFace(block, x, y, z, ore);
		r.renderWestFace(block, x, y, z, ore);
		r.overrideBlockTexture = null;
		return false;
	}
}