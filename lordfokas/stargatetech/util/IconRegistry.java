package lordfokas.stargatetech.util;

import java.util.HashMap;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public final class IconRegistry {
	public static class Files{
		public static final String GUI_IONIZER		= "/mods/StargateTech/textures/ionizer.png";
		public static final String GUI_GENERATOR	= "/mods/StargateTech/textures/generator.png";
		public static final String GUI_DIALER		= "/mods/StargateTech/textures/dialer.png";
		public static final String GATE_SYMBOLS		= "/mods/StargateTech/textures/symbols.png";
	}
	
	public static Icon busCable, busCableCorner1, busCableCorner2, busCableJoint, busCableJointLit, busCableX, busCableY, busCableZ;
	public static Icon ionTubeJoint, lantean00simple, lantean01hexFloor, lantean02verticalEmitter;
	public static Icon machine, machineLink0, machineLink1, machineLink2;
	public static Icon machineSlot, machineSlotLink0, machineSlotLink1, machineSlotLink2;
	public static Icon naquadahOreGlow, naquadriaOreGlow, oreBase, particleIonizerSide, powerConduitJoint;
	public static Icon shieldEmitterLink0, shieldEmitterLink1, shieldEmitterLink2, shieldEmitterTop;
	public static Icon slot0red, slot1blue, slot2yellow, empty;
	
	public static final IconRegistry instance = new IconRegistry();
	
	public void loadAllBlocks(IconRegister register){
		busCable = loadBlock(register, "busCable");
		busCableCorner1 = loadBlock(register, "busCableCorner1");
		busCableCorner2 = loadBlock(register, "busCableCorner2");
		busCableJoint = loadBlock(register, "busCableJoint");
		busCableJointLit = loadBlock(register, "busCableJointLit");
		busCableX = loadBlock(register, "busCableX");
		busCableY = loadBlock(register, "busCableY");
		busCableZ = loadBlock(register, "busCableZ");
		ionTubeJoint = loadBlock(register, "ionTubeJoint");
		lantean00simple = loadBlock(register, "lantean-00-simple");
		lantean01hexFloor = loadBlock(register, "lantean-01-hexFloor");
		lantean02verticalEmitter = loadBlock(register, "lantean-02-verticalEmitter");
		machine = loadBlock(register, "machine");
		machineLink0 = loadBlock(register, "machine-Link0");
		machineLink1 = loadBlock(register, "machine-Link1");
		machineLink2 = loadBlock(register, "machine-Link2");
		machineSlot = loadBlock(register, "machineSlot");
		machineSlotLink0 = loadBlock(register, "machineSlot-Link0");
		machineSlotLink1 = loadBlock(register, "machineSlot-Link1");
		machineSlotLink2 = loadBlock(register, "machineSlot-Link2");
		naquadahOreGlow = loadBlock(register, "naquadahOreGlow");
		naquadriaOreGlow = loadBlock(register, "naquadriaOreGlow");
		oreBase = loadBlock(register, "oreBase");
		particleIonizerSide = loadBlock(register, "particleIonizerSide");
		powerConduitJoint = loadBlock(register, "powerConduitJoint");
		shieldEmitterLink0 = loadBlock(register, "shieldEmitter-Link0");
		shieldEmitterLink1 = loadBlock(register, "shieldEmitter-Link1");
		shieldEmitterLink2 = loadBlock(register, "shieldEmitter-Link2");
		shieldEmitterTop = loadBlock(register, "shieldEmitterTop");
		slot0red = loadBlock(register, "slot0-red");
		slot1blue = loadBlock(register, "slot1-blue");
		slot2yellow = loadBlock(register, "slot2-yellow");
		empty = loadBlock(register, "void");
	}
	
	private Icon loadBlock(IconRegister register, String name){
		return register.registerIcon("StargateTech:" + name);
	}
}