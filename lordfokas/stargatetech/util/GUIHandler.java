package lordfokas.stargatetech.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import lordfokas.stargatetech.gui.GUIDialingComputer;
import lordfokas.stargatetech.gui.GUINaquadahGenerator;
import lordfokas.stargatetech.gui.GUIParticleIonizer;
import lordfokas.stargatetech.machine.DialingComputerContainer;
import lordfokas.stargatetech.machine.DialingComputerTE;
import lordfokas.stargatetech.machine.NaquadahGeneratorContainer;
import lordfokas.stargatetech.machine.NaquadahGeneratorTE;
import lordfokas.stargatetech.machine.ParticleIonizerContainer;
import lordfokas.stargatetech.machine.ParticleIonizerTE;
import cpw.mods.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler {
	public static final GUIHandler instance = new GUIHandler();
	public static final int guiParticleIonizer = 1;
	public static final int guiNaquadahGenerator = 2;
	public static final int guiDialingComputer = 3;
	
	private GUIHandler(){}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		Container container = getContainer(ID, player, world, x, y, z);
		switch(ID){
			case guiParticleIonizer: return new GUIParticleIonizer((ParticleIonizerContainer) container);
			case guiNaquadahGenerator: return new GUINaquadahGenerator((NaquadahGeneratorContainer) container);
			case guiDialingComputer: return new GUIDialingComputer((DialingComputerContainer) container);
			default: return null;
		}
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return getContainer(ID, player, world, x, y, z);
	}
	
	private Container getContainer(int ID, EntityPlayer player, World world, int x, int y, int z){
		switch(ID){
			case guiParticleIonizer: return new ParticleIonizerContainer((ParticleIonizerTE) world.getBlockTileEntity(x, y, z), player);
			case guiNaquadahGenerator: return new NaquadahGeneratorContainer((NaquadahGeneratorTE) world.getBlockTileEntity(x, y, z), player);
			case guiDialingComputer: return new DialingComputerContainer((DialingComputerTE) world.getBlockTileEntity(x, y, z), player);
			default: return null;
		}
	}
}
