package lordfokas.stargatetech.rendering;

import lordfokas.stargatetech.StargateTech;
import lordfokas.stargatetech.common.BaseBlockRenderer;
import lordfokas.stargatetech.util.IconRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

/**
 * Renders Naquadah and Naquadria compressed ingot blocks.
 * 
 * @author LordFokas
 */
public class RenderMetalBlock extends BaseBlockRenderer {
	private static RenderMetalBlock INSTANCE = new RenderMetalBlock();
	
	public static RenderMetalBlock instance(){
		return INSTANCE;
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks r){
		Icon ore = block.getBlockTextureFromSide(0);
		Tessellator t = Tessellator.instance;
		t.setBrightness(220);
		t.setColorOpaque_I(0xFFFFFF);
		r.renderTopFace(block, x, y, z, ore);
		r.renderBottomFace(block, x, y, z, ore);
		r.renderNorthFace(block, x, y, z, ore);
		r.renderSouthFace(block, x, y, z, ore);
		r.renderEastFace(block, x, y, z, ore);
		r.renderWestFace(block, x, y, z, ore);
		return false;
	}
}