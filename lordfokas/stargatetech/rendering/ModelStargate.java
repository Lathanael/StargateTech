package lordfokas.stargatetech.rendering;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import lordfokas.stargatetech.machine.StargateTE;
import lordfokas.stargatetech.util.Helper;

/**
 * Renders a Stargate. It will be changed soon to a pure OpenGL renderer,
 * instead of using Minecraft's stuff, for the sake of client performance.
 * @author LordFokas
 */
public class ModelStargate extends ModelBase {
	private ModelRenderer ringOut, ringIn, chevron, base;
	
	public ModelStargate(){
		ringOut = new ModelRenderer(this).setTextureSize(192, 96);
		ringOut.setRotationPoint(0, 44, 0);
		ringOut.setTextureOffset(0, 0).addBox(3F, -48F, -12F, 10, 4, 24);
		//ringOut.setTextureOffset(0, 28).addBox(4F, -44F, -11F, 8, 4, 22);
		
		ringIn = new ModelRenderer(this).setTextureSize(192, 96);
		ringIn.setRotationPoint(0, 44, 0);
		//ringIn.setTextureOffset(0, 0).addBox(3F, -48F, -12F, 10, 4, 24);
		ringIn.setTextureOffset(0, 28).addBox(4F, -44F, -11F, 8, 4, 22);
		
		chevron = new ModelRenderer(this, 0, 0).setTextureSize(192, 96);
		chevron.setRotationPoint(0, 44, 0);
		chevron.addBox(2F, 42F, -3.5F, 4, 8, 6);
		base = new ModelRenderer(this, 0, 0).setTextureSize(192, 96);
		base.addBox(0, -8F, -40F, 16, 16, 80);
	}
	
	public void render(StargateTE stargate, float scale){
		for(int i = 0; i < 13; i++){
			ringOut.rotateAngleX = (float)(((360 * i) / 13) / Helper.deg2rad);
			ringOut.render(scale / 16);
			ringIn.rotateAngleX = (float)(((360 * i) / 13) / Helper.deg2rad);
			ringIn.render(scale / 16);
		}
		for(int i = 0; i < 9; i++){
			chevron.rotateAngleX = (float)(((360 * i) / 9) / Helper.deg2rad);
			chevron.render(scale / 16);
		}
		base.render(scale / 16);
		if(stargate.hasWormhole()){
			renderEventHorizon();
		}
		short irisState = stargate.getIrisState();
		if(irisState != 0){
			renderIris(irisState);
		}
	}
	
	private void renderEventHorizon(){
		GL11.glPushMatrix();
		// Even though there are 2 polygons, face culling is disabled.
		// This is so that players going through the first one, still
		// see the second before being teleported.
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glTranslatef(0.4F, -0.25F, 0);
		renderEventHorizonFace();
		GL11.glTranslatef(0.2F, 0, 0);
		renderEventHorizonFace();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}
	
	private void renderEventHorizonFace(){
		GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
		GL11.glTexCoord2f(112F / 192F, 0F);
		GL11.glVertex3f(0, 6, -3F);
		GL11.glTexCoord2f(112F / 192F, 80F / 96F);
		GL11.glVertex3f(0, 0, -3F);
		GL11.glTexCoord2f(1F, 0F);
		GL11.glVertex3f(0, 6, 3F);
		GL11.glTexCoord2f(1F, 80F / 96F);
		GL11.glVertex3f(0, 0, 3F);
		GL11.glEnd();
	}
	
	private void renderIris(short irisState){
		// no iris rendering yet.
	}
}