package lordfokas.stargatetech.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.registry.LanguageRegistry;
import lordfokas.stargatetech.machine.NaquadahGeneratorContainer;
import lordfokas.stargatetech.util.Helper;
import lordfokas.stargatetech.util.IconRegistry;

public class GUINaquadahGenerator extends GuiContainer{

	public GUINaquadahGenerator(NaquadahGeneratorContainer container){
		super(container);
		this.xSize = 176;
		this.ySize = 160;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f1, int i1, int i2) {
		Helper.bindTexture(IconRegistry.Files.GUI_GENERATOR);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i1, int i2) {
		NaquadahGeneratorContainer ngc = (NaquadahGeneratorContainer) this.inventorySlots;
		fontRenderer.drawString("Naquadah Generator", 30, 8, 4210752);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
		fontRenderer.drawString("Power: " + ngc.generator.getPowerAmount(), 8, 28, 0x0000FF);
		fontRenderer.drawString("Max Power: 100.000", 8, 38, 0x0000FF);
	}
}
