package lordfokas.stargatetech.common;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

/**
 * A base for all the custom block renderers we're using.
 * @author LordFokas
 */
public abstract class BaseBlockRenderer implements ISimpleBlockRenderingHandler{
	protected int rid;
	
	protected BaseBlockRenderer()
		{ rid = RenderingRegistry.getNextAvailableRenderId(); }
	
	@Override public int getRenderId()
		{ return rid; }
	
	@Override public boolean shouldRender3DInInventory()
		{ return true; }
	
	//**********  ITEM RENDERING  ****************************************************************//
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer){
		renderAsItem(block, renderer);
	}
	
	protected static void renderAsItem(Block block, RenderBlocks renderer){
		int[] tmap = new int[6];
		for(int i = 0; i < 6; i++){
			tmap[i] = block.getBlockTextureFromSide(i);
		}
		renderAsItem(block, renderer, tmap);
	}
	
	protected static void renderAsItem(Block block, RenderBlocks renderer, int[] tmap){
		Tessellator tessellator = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F); // Set angled view
		
		// Y-
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderBottomFace(block, 0, 0, 0, tmap[0]);
        tessellator.draw();
        
        // Y+
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderTopFace(block, 0, 0, 0, tmap[1]);
        tessellator.draw();
        
        // Z-
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderEastFace(block, 0, 0, 0, tmap[2]);
        tessellator.draw();
        
        // Z+
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderWestFace(block, 0, 0, 0, tmap[3]);
        tessellator.draw();
        
        // X-
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderNorthFace(block, 0, 0, 0, tmap[4]);
        tessellator.draw();
        
        // X+
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderSouthFace(block, 0, 0, 0, tmap[5]);
        tessellator.draw();
        
        GL11.glTranslatef(0.5F, 0.5F, 0.5F); // Reset angled view
	}
	
	//**********  BLOCK RENDERING  ***************************************************************//
	public void renderBlock(RenderBlocks renderer, double x, double y, double z, int texture){
		renderYNeg(renderer, x, y, z, texture);
		renderYPos(renderer, x, y, z, texture);
		renderZNeg(renderer, x, y, z, texture);
		renderZPos(renderer, x, y, z, texture);
		renderXNeg(renderer, x, y, z, texture);
		renderXPos(renderer, x, y, z, texture);
	}
	
	public void renderBlock(RenderBlocks renderer, double x, double y, double z, int[] tmap){
		renderYNeg(renderer, x, y, z, tmap[0]);
		renderYPos(renderer, x, y, z, tmap[1]);
		renderZNeg(renderer, x, y, z, tmap[2]);
		renderZPos(renderer, x, y, z, tmap[3]);
		renderXNeg(renderer, x, y, z, tmap[4]);
		renderXPos(renderer, x, y, z, tmap[5]);
	}
	
	public void renderYNeg(RenderBlocks renderer, double x, double y, double z, int texture){
		Tessellator tessellator = Tessellator.instance;
        int var10 = (texture & 15) << 4;
        int var11 = texture & 240;
        double var12 = ((double)var10 + renderer.field_83021_g * 16.0D) / 256.0D;
        double var14 = ((double)var10 + renderer.field_83026_h * 16.0D - 0.01D) / 256.0D;
        double var16 = ((double)var11 + renderer.field_83025_k * 16.0D) / 256.0D;
        double var18 = ((double)var11 + renderer.field_83022_l * 16.0D - 0.01D) / 256.0D;
        if (renderer.field_83021_g < 0.0D || renderer.field_83026_h > 1.0D){
            var12 = (double)(((float)var10 + 0.0F) / 256.0F);
            var14 = (double)(((float)var10 + 15.99F) / 256.0F);
        }
        if (renderer.field_83025_k < 0.0D || renderer.field_83022_l > 1.0D){
            var16 = (double)(((float)var11 + 0.0F) / 256.0F);
            var18 = (double)(((float)var11 + 15.99F) / 256.0F);
        }
        double var20 = var14;
        double var22 = var12;
        double var24 = var16;
        double var26 = var18;
        double var28 = x + renderer.field_83021_g;
        double var30 = x + renderer.field_83026_h;
        double var32 = y + renderer.field_83027_i;
        double var34 = z + renderer.field_83025_k;
        double var36 = z + renderer.field_83022_l;
        tessellator.setBrightness(0xFF);
        tessellator.setColorOpaque_F(1F, 1F, 1F);
        tessellator.addVertexWithUV(var28, var32, var36, var22, var26);
        tessellator.addVertexWithUV(var28, var32, var34, var12, var16);
        tessellator.addVertexWithUV(var30, var32, var34, var20, var24);
        tessellator.addVertexWithUV(var30, var32, var36, var14, var18);
    }
	
	public void renderYPos(RenderBlocks renderer, double x, double y, double z, int texture){
		Tessellator tessellator = Tessellator.instance;
        int var10 = (texture & 15) << 4;
        int var11 = texture & 240;
        double var12 = ((double)var10 + renderer.field_83021_g * 16.0D) / 256.0D;
        double var14 = ((double)var10 + renderer.field_83026_h * 16.0D - 0.01D) / 256.0D;
        double var16 = ((double)var11 + renderer.field_83025_k * 16.0D) / 256.0D;
        double var18 = ((double)var11 + renderer.field_83022_l * 16.0D - 0.01D) / 256.0D;
        if (renderer.field_83021_g < 0.0D || renderer.field_83026_h > 1.0D){
            var12 = (double)(((float)var10 + 0.0F) / 256.0F);
            var14 = (double)(((float)var10 + 15.99F) / 256.0F);
        }
        if (renderer.field_83025_k < 0.0D || renderer.field_83022_l > 1.0D){
            var16 = (double)(((float)var11 + 0.0F) / 256.0F);
            var18 = (double)(((float)var11 + 15.99F) / 256.0F);
        }
        double var20 = var14;
        double var22 = var12;
        double var24 = var16;
        double var26 = var18;
        double var28 = x + renderer.field_83021_g;
        double var30 = x + renderer.field_83026_h;
        double var32 = y + renderer.field_83024_j;
        double var34 = z + renderer.field_83025_k;
        double var36 = z + renderer.field_83022_l;
        tessellator.setBrightness(0xFF);
        tessellator.setColorOpaque_F(1F, 1F, 1F);
        tessellator.addVertexWithUV(var30, var32, var36, var14, var18);
        tessellator.addVertexWithUV(var30, var32, var34, var20, var24);
        tessellator.addVertexWithUV(var28, var32, var34, var12, var16);
        tessellator.addVertexWithUV(var28, var32, var36, var22, var26);
    }
	
	public void renderZNeg(RenderBlocks renderer, double x, double y, double z, int texture){
		Tessellator tessellator = Tessellator.instance;
        int var10 = (texture & 15) << 4;
        int var11 = texture & 240;
        double var12 = ((double)var10 + renderer.field_83021_g * 16.0D) / 256.0D;
        double var14 = ((double)var10 + renderer.field_83026_h * 16.0D - 0.01D) / 256.0D;
        double var16 = ((double)(var11 + 16) - renderer.field_83024_j * 16.0D) / 256.0D;
        double var18 = ((double)(var11 + 16) - renderer.field_83027_i * 16.0D - 0.01D) / 256.0D;
        double var20;
        if (renderer.flipTexture){
            var20 = var12;
            var12 = var14;
            var14 = var20;
        }
        if (renderer.field_83021_g < 0.0D || renderer.field_83026_h > 1.0D){
            var12 = (double)(((float)var10 + 0.0F) / 256.0F);
            var14 = (double)(((float)var10 + 15.99F) / 256.0F);
        }
        if (renderer.field_83027_i < 0.0D || renderer.field_83024_j > 1.0D){
            var16 = (double)(((float)var11 + 0.0F) / 256.0F);
            var18 = (double)(((float)var11 + 15.99F) / 256.0F);
        }
        var20 = var14;
        double var22 = var12;
        double var24 = var16;
        double var26 = var18;
        double var28 = x + renderer.field_83021_g;
        double var30 = x + renderer.field_83026_h;
        double var32 = y + renderer.field_83027_i;
        double var34 = y + renderer.field_83024_j;
        double var36 = z + renderer.field_83025_k;
        tessellator.setBrightness(0xFF);
        tessellator.setColorOpaque_F(1F, 1F, 1F);
        tessellator.addVertexWithUV(var28, var34, var36, var20, var24);
        tessellator.addVertexWithUV(var30, var34, var36, var12, var16);
        tessellator.addVertexWithUV(var30, var32, var36, var22, var26);
        tessellator.addVertexWithUV(var28, var32, var36, var14, var18);
    }
	
	public void renderZPos(RenderBlocks renderer, double x, double y, double z, int texture){
		Tessellator tessellator = Tessellator.instance;
        int var10 = (texture & 15) << 4;
        int var11 = texture & 240;
        double var12 = ((double)var10 + renderer.field_83021_g * 16.0D) / 256.0D;
        double var14 = ((double)var10 + renderer.field_83026_h * 16.0D - 0.01D) / 256.0D;
        double var16 = ((double)(var11 + 16) - renderer.field_83024_j * 16.0D) / 256.0D;
        double var18 = ((double)(var11 + 16) - renderer.field_83027_i * 16.0D - 0.01D) / 256.0D;
        double var20;
        if (renderer.flipTexture){
            var20 = var12;
            var12 = var14;
            var14 = var20;
        }
        if (renderer.field_83021_g < 0.0D || renderer.field_83026_h > 1.0D){
            var12 = (double)(((float)var10 + 0.0F) / 256.0F);
            var14 = (double)(((float)var10 + 15.99F) / 256.0F);
        }
        if (renderer.field_83027_i < 0.0D || renderer.field_83024_j > 1.0D){
            var16 = (double)(((float)var11 + 0.0F) / 256.0F);
            var18 = (double)(((float)var11 + 15.99F) / 256.0F);
        }
        var20 = var14;
        double var22 = var12;
        double var24 = var16;
        double var26 = var18;
        double var28 = x + renderer.field_83021_g;
        double var30 = x + renderer.field_83026_h;
        double var32 = y + renderer.field_83027_i;
        double var34 = y + renderer.field_83024_j;
        double var36 = z + renderer.field_83022_l;
        tessellator.setBrightness(0xFF);
        tessellator.setColorOpaque_F(1F, 1F, 1F);
        tessellator.addVertexWithUV(var28, var34, var36, var12, var16);
        tessellator.addVertexWithUV(var28, var32, var36, var22, var26);
        tessellator.addVertexWithUV(var30, var32, var36, var14, var18);
        tessellator.addVertexWithUV(var30, var34, var36, var20, var24);
    }
	
	public void renderXNeg(RenderBlocks renderer, double x, double y, double z, int texture){
		Tessellator tessellator = Tessellator.instance;
        int texX = (texture & 15) << 4;
        int texY = texture & 240;
        double texXMin = ((double)texX + renderer.field_83025_k * 16.0D) / 256.0D;
        double texXMax = ((double)texX + renderer.field_83022_l * 16.0D - 0.01D) / 256.0D;
        double texYMin = ((double)(texY + 16) - renderer.field_83024_j * 16.0D) / 256.0D;
        double texYMax = ((double)(texY + 16) - renderer.field_83027_i * 16.0D - 0.01D) / 256.0D;
        double swap;
        if (renderer.flipTexture){
            swap = texXMin;
            texXMin = texXMax;
            texXMax = swap;
        }
        if (renderer.field_83025_k < 0.0D || renderer.field_83022_l > 1.0D){
            texXMin = (double)(((float)texX + 0.0F) / 256.0F);
            texXMax = (double)(((float)texX + 15.99F) / 256.0F);
        }
        if (renderer.field_83027_i < 0.0D || renderer.field_83024_j > 1.0D){
            texYMin = (double)(((float)texY + 0.0F) / 256.0F);
            texYMax = (double)(((float)texY + 15.99F) / 256.0F);
        }
        swap = texXMax;
        double var22 = texXMin;
        double var24 = texYMin;
        double var26 = texYMax;
        double vertexX = x + renderer.field_83021_g;
        double vertexYMax = y + renderer.field_83027_i;
        double vertexYMin = y + renderer.field_83024_j;
        double vertexZMax = z + renderer.field_83025_k;
        double vertexZMin = z + renderer.field_83022_l;
        tessellator.setBrightness(0xFF);
        tessellator.setColorOpaque_F(1F, 1F, 1F);
        tessellator.addVertexWithUV(vertexX, vertexYMin, vertexZMin, swap, var24);
        tessellator.addVertexWithUV(vertexX, vertexYMin, vertexZMax, texXMin, texYMin);
        tessellator.addVertexWithUV(vertexX, vertexYMax, vertexZMax, var22, var26);
        tessellator.addVertexWithUV(vertexX, vertexYMax, vertexZMin, texXMax, texYMax);
    }
	
	public void renderXPos(RenderBlocks renderer, double x, double y, double z, int texture){
        Tessellator tessellator = Tessellator.instance;
        int texX = (texture & 15) << 4;
        int texY = texture & 240;
        double texXMin = ((double)texX + renderer.field_83025_k * 16.0D) / 256.0D;
        double texXMax = ((double)texX + renderer.field_83022_l * 16.0D - 0.01D) / 256.0D;
        double texYMin = ((double)(texY + 16) - renderer.field_83024_j * 16.0D) / 256.0D;
        double texYMax = ((double)(texY + 16) - renderer.field_83027_i * 16.0D - 0.01D) / 256.0D;
        double swap;
        if (renderer.flipTexture){
            swap = texXMin;
            texXMin = texXMax;
            texXMax = swap;
        }
        if (renderer.field_83025_k < 0.0D || renderer.field_83022_l > 1.0D){
            texXMin = (double)(((float)texX + 0.0F) / 256.0F);
            texXMax = (double)(((float)texX + 15.99F) / 256.0F);
        }
        if (renderer.field_83027_i < 0.0D || renderer.field_83024_j > 1.0D){
            texYMin = (double)(((float)texY + 0.0F) / 256.0F);
            texYMax = (double)(((float)texY + 15.99F) / 256.0F);
        }
        swap = texXMax;
        double var22 = texXMin;
        double var24 = texYMin;
        double var26 = texYMax;
        double vertexX = x + renderer.field_83026_h;
        double vertexYMin = y + renderer.field_83027_i;
        double vertexYMax = y + renderer.field_83024_j;
        double vertexZMax = z + renderer.field_83025_k;
        double vertexZMin = z + renderer.field_83022_l;
        tessellator.setBrightness(0xFF);
        tessellator.setColorOpaque_F(1F, 1F, 1F);
        tessellator.addVertexWithUV(vertexX, vertexYMin, vertexZMin, var22, var26);
        tessellator.addVertexWithUV(vertexX, vertexYMin, vertexZMax, texXMax, texYMax);
        tessellator.addVertexWithUV(vertexX, vertexYMax, vertexZMax, swap, var24);
        tessellator.addVertexWithUV(vertexX, vertexYMax, vertexZMin, texXMin, texYMin);
    }
}
