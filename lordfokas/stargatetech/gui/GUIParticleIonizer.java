package lordfokas.stargatetech.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.registry.LanguageRegistry;

import lordfokas.stargatetech.machine.ParticleIonizerContainer;
import lordfokas.stargatetech.util.Helper;
import lordfokas.stargatetech.util.IconRegistry;

public class GUIParticleIonizer extends GuiContainer{

	public GUIParticleIonizer(ParticleIonizerContainer container){
		super(container);
		this.xSize = 176;
		this.ySize = 160;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f1, int i1, int i2) {
		Helper.bindTexture(IconRegistry.Files.GUI_IONIZER);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i1, int i2) {
		ParticleIonizerContainer pic = (ParticleIonizerContainer) this.inventorySlots;
		fontRenderer.drawString("Particle Ionizer", 48, 8, 4210752);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
		
		fontRenderer.drawString("Production: " + pic.ionizer.ionsPerTick + " IQ/t", 8, 38, 0x0000FF);
		fontRenderer.drawString("Stored: " + pic.ionizer.getIonAmount() + " IQ", 8, 48, 0x0000FF);
		
		if(pic.material == null){
			fontRenderer.drawString("Empty", 28, 23, 0x0000FF);
		}else{
	        boolean var5 = false;
	        this.zLevel = 100.0F;
	        itemRenderer.zLevel = 100.0F;
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            itemRenderer.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.renderEngine, pic.material, 8, 18);
	        itemRenderer.zLevel = 0.0F;
	        this.zLevel = 0.0F;
	        fontRenderer.drawString(pic.material.getDisplayName(), 28, 23, 0x0000FF);
		}
	}
}
