package lordfokas.stargatetech.gui;

import lordfokas.stargatetech.machine.DialingComputerContainer;
import lordfokas.stargatetech.machine.DialingComputerTE;
import lordfokas.stargatetech.networks.stargate.Symbol;
import lordfokas.stargatetech.packet.PacketHandlerClient;
import lordfokas.stargatetech.util.Helper;
import lordfokas.stargatetech.util.IconRegistry;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.ForgeHooksClient;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GUIDialingComputer extends GuiContainer{
	private DialingComputerTE computer = null;
	private boolean onBackground = false;
	private int hover = -1;
	private Pair[] symbol = new Pair[39];
	
	private class Pair{
		public int i, x, y;
		public Pair(int i, int x, int y){
			this.i = i;
			this.x = x;
			this.y = y;
		}
	}

	public GUIDialingComputer(DialingComputerContainer container){
		super(container);
		computer = container.computer;
		this.xSize = 227;
		this.ySize = 227;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f1, int i1, int i2) {
		onBackground = true;
		Helper.bindTexture(IconRegistry.Files.GUI_DIALER);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		zLevel = 0F;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		if(computer.canDial){
			drawQuad(78, 59, 186F / 256F, 1, 244F / 256F, 1, 70, 12);
		}
		drawSymbolButtons();
		drawSymbols();
		Helper.bindTexture(IconRegistry.Files.GUI_DIALER);
		if(hover == -2){
			drawQuad(85, 32, 238F / 256F, 1.0F, 40F / 256F, 56F / 256F, 18, 16);
		}else if(hover == -3){
			drawQuad(123, 32, 238F / 256F, 1.0F, 56F / 256F, 72F / 256F, 18, 16);
		}
		onBackground = false;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i1, int i2) {
		drawCentered("Dialing Computer", 8, xSize/2, 4210752);
		drawCentered("Dial Address", 61, xSize/2, computer.canDial ? 0xFFFFFF : 0x007FFF);
		drawCentered("Reset", 75, xSize/2, 0xFF0000);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 32, ySize - 96 + 2, 4210752);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		zLevel = -1F;
		StringBuffer name = new StringBuffer();
		for(int i = 0; i < 9; i++){
			if(computer.addr[i] != null){
				String s = computer.addr[i].getName();
				if(i == 0 || i == 3 || i == 6){
					name.append(" ").append(s);
				}else{
					name.append(s.toLowerCase());
				}
			}
		}
		drawCentered(name.toString().trim(), 21, xSize/2, 0x007FFF);
		drawHover();
	}
	
	@Override
	protected void mouseClicked(int x, int y, int btn){
		super.mouseClicked(x, y, btn);
		x -= guiLeft;
		y -= guiTop;
		if(btn == 0){
			Pair p = getSymbolButton(x, y);
			if(p != null){
				PacketHandlerClient.sendDialingComputerButtonHit(computer.xCoord, computer.yCoord, computer.zCoord, (byte)0, (byte)p.i);
			}else{
				if(x >= 78 && x <= 147 && y >= 59 && y <= 70){
					// Dial
					PacketHandlerClient.sendDialingComputerButtonHit(computer.xCoord, computer.yCoord, computer.zCoord, (byte)1, (byte)0);
				}else if(x >= 78 && x <= 147 && y >= 73 && y <= 84){
					// Reset
					PacketHandlerClient.sendDialingComputerButtonHit(computer.xCoord, computer.yCoord, computer.zCoord, (byte)1, (byte)1);
				}else if(x >= 85 && x <= 103 && y >= 32 && y <= 48){
					// Save
					PacketHandlerClient.sendDialingComputerButtonHit(computer.xCoord, computer.yCoord, computer.zCoord, (byte)1, (byte)2);
				}else if(x >= 123 && x <= 141 && y >= 32 && y <= 48){
					// Load
					PacketHandlerClient.sendDialingComputerButtonHit(computer.xCoord, computer.yCoord, computer.zCoord, (byte)1, (byte)3);
				}
			}
		}
	}
	
	private void drawCentered(String s, int y, int xMid, int color){
		fontRenderer.drawString(s, xMid - fontRenderer.getStringWidth(s) / 2, y, color);
	}
	
	private void drawSymbolQuad(float x, float y, float xMin, float xMax, float yMin, float yMax){
		xMin /= 98;
		xMax /= 98;
		yMin /= 428;
		yMax /= 428;
		drawQuad(x+1, y+1, xMin, xMax, yMin, yMax, 14);
	}
	
	private void drawQuad(float x, float y, float xMin, float xMax, float yMin, float yMax)
		{ drawQuad(x, y, xMin, xMax, yMin, yMax, 16); }
	
	private void drawQuad(float x, float y, float xMin, float xMax, float yMin, float yMax, int step)
		{ drawQuad(x, y, xMin, xMax, yMin, yMax, step, step); }
	
	private void drawQuad(float x, float y, float xMin, float xMax, float yMin, float yMax, int xStep, int yStep){
		if(onBackground){
			x += guiLeft;
			y += guiTop;
		}
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
		GL11.glTexCoord3f(xMin, yMin, zLevel);
		GL11.glVertex2f(x, y);
		GL11.glTexCoord3f(xMin, yMax, zLevel);
		GL11.glVertex2f(x, y+yStep);
		GL11.glTexCoord3f(xMax, yMin, zLevel);
		GL11.glVertex2f(x+xStep, y);
		GL11.glTexCoord3f(xMax, yMax, zLevel);
		GL11.glVertex2f(x+xStep, y+yStep);
		GL11.glEnd();
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	private void drawButton(int i, int x, int y){
		symbol[i-1] = new Pair(i, x, y);
		float xMin, xMax, yMin, yMax;
		float seg = 1F / 16F;
		xMin = 1F - seg;
		xMax = 1F;
		if(computer.used[i-1]){
			yMin = seg;
			yMax = seg * 2F;
		}else{
			yMin = 0F;
			yMax = seg;
		}
		drawQuad(x, y, xMin, xMax, yMin, yMax);
	}
	
	private void drawSymbols(){
		Helper.bindTexture(IconRegistry.Files.GATE_SYMBOLS);
		for(Pair p : symbol){
			int x = (p.i - 1) % 3;
			int y = (p.i - 1) / 3;
			drawSymbolQuad(p.x, p.y, x*32 + x, (x+1)*32 +x, y*32 + y, (y+1)*32 +y);
		}
	}
	
	private void drawHover(){
		Helper.bindTexture(IconRegistry.Files.GUI_DIALER);
		if(hover > -1){
			Pair p = symbol[hover];
			drawQuad(p.x - 8, p.y - 14, 0, 32 / 256F, 242F / 256F, 1, 32, 14);
			GL11.glPushMatrix();
			GL11.glTranslatef(0, -0.5F, 0);
			drawCentered(Symbol.symbols[hover+1].getName(), p.y - 11, p.x + 8, 0xFFFFFF);
			GL11.glPopMatrix();
		}
	}
	
	private void drawSymbolButtons(){
		drawButton(1, 78, 92);
		drawButton(2, 96, 92);
		drawButton(3, 114, 92);
		drawButton(4, 132, 92);
		drawButton(5, 69, 110);
		drawButton(6, 87, 110);
		drawButton(7, 105, 110);
		drawButton(8, 123, 110);
		drawButton(9, 141, 110);
		
		drawButton(10, 8, 20);
		drawButton(11, 8, 38);
		drawButton(12, 8, 56);
		drawButton(13, 8, 74);
		drawButton(14, 8, 92);
		drawButton(15, 8, 110);
		drawButton(16, 26, 29);
		drawButton(17, 26, 47);
		drawButton(18, 26, 65);
		drawButton(19, 26, 83);
		drawButton(20, 26, 101);
		drawButton(21, 44, 38);
		drawButton(22, 44, 56);
		drawButton(23, 44, 74);
		drawButton(24, 44, 92);
		
		drawButton(25, 167, 38);
		drawButton(26, 167, 56);
		drawButton(27, 167, 74);
		drawButton(28, 167, 92);
		drawButton(29, 185, 29);
		drawButton(30, 185, 47);
		drawButton(31, 185, 65);
		drawButton(32, 185, 83);
		drawButton(33, 185, 101);
		drawButton(34, 203, 20);
		drawButton(35, 203, 38);
		drawButton(36, 203, 56);
		drawButton(37, 203, 74);
		drawButton(38, 203, 92);
		drawButton(39, 203, 110);
	}
	
	private void detectHover(int x, int y){
		Pair p = getSymbolButton(x, y);
		if(x >= 85 && x <= 103 && y >= 32 && y <= 48) hover = -2;
		else if(x >= 123 && x <= 141 && y >= 32 && y <= 48) hover = -3;
		else if(p == null) hover = -1;
		else hover = p.i - 1;
	}
	
	private Pair getSymbolButton(int x, int y){
		for(Pair p : symbol){
			if(x >= p.x && x <= p.x + 16 && y >= p.y && y <= p.y + 16){
				return p;
			}
		}
		return null;
	}
	
	@Override
	public void handleMouseInput(){
		super.handleMouseInput();
		int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
	    int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
	    if(Mouse.getEventButton() == -1){
	    	detectHover(x - guiLeft, y - guiTop);
	    }
	}
}
