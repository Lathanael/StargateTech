package lordfokas.stargatetech.rendering;

import lordfokas.stargatetech.machine.StargateTE;
import lordfokas.stargatetech.util.Helper;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

public class RenderStargate extends TileEntitySpecialRenderer {
	public static final RenderStargate instance = new RenderStargate();
	private static final ModelStargate model = new ModelStargate();
	
	@Override
	public void renderTileEntityAt(TileEntity e, double x, double y, double z, float f){
		StargateTE stargate = (StargateTE) e;
		GL11.glPushMatrix();
		switch(stargate.getDirection()){
			case Helper.dirXPos:
				GL11.glTranslated(x, y+0.5, z+0.5);
				break;
			case Helper.dirXNeg:
				GL11.glTranslated(x+1, y+0.5, z+0.5);
				GL11.glRotatef(180, 0, 1, 0);
				break;
			case Helper.dirZNeg:
				GL11.glTranslated(x+0.5, y+0.5, z+1);
				GL11.glRotatef(90, 0, 1, 0);
				break;
			case Helper.dirZPos:
				GL11.glTranslated(x+0.5, y+0.5, z);
				GL11.glRotatef(270, 0, 1, 0);
				break;
		}
		bindTextureByName("/lordfokas/stargatetech/textures/stargate.png");
		model.render(stargate, 1.0F);
		GL11.glPopMatrix();
	}
}
