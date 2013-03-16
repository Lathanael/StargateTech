package lordfokas.stargatetech.rendering;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import lordfokas.stargatetech.common.BaseBlockContainer;
import lordfokas.stargatetech.common.BaseBlockRenderer;
import lordfokas.stargatetech.machine.IonTube;
import lordfokas.stargatetech.machine.ParticleIonizer;
import lordfokas.stargatetech.machine.PowerConduit;
import lordfokas.stargatetech.util.Helper;
import lordfokas.stargatetech.util.IconRegistry;
import lordfokas.stargatetech.util.UnlocalizedNames;

public class RenderParticleIonizer extends BaseBlockRenderer{
	private static RenderParticleIonizer INSTANCE = new RenderParticleIonizer();
	
	public static RenderParticleIonizer instance(){
		return INSTANCE;
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer){
		if(!(block instanceof ParticleIonizer)) return false;
		ParticleIonizer particleIonizer = (ParticleIonizer) block;
		Icon v = IconRegistry.empty;
		Icon s = IconRegistry.machineSlot;
		Icon[] tmap = { s, s, s, s, s, s };
		Icon[] glow = { v, v, v, v, v, v };
		Icon red	= IconRegistry.slot0red;
		Icon blue	= IconRegistry.slot1blue;
		Icon yellow	= IconRegistry.slot2yellow;
		tmap[0] = tmap[1] = ((BaseBlockContainer)block).getTexture();
		if(Helper.getBlockInstance(world, x+1, y, z) instanceof IonTube){
			glow[Helper.dirXPos] = blue;
		}else if(Helper.getBlockInstance(world, x+1, y, z) instanceof PowerConduit){
			glow[Helper.dirXPos] = yellow;
		}else{
			glow[Helper.dirXPos] = red;
		}
		if(Helper.getBlockInstance(world, x-1, y, z) instanceof IonTube){
			glow[Helper.dirXNeg] = blue;
		}else if(Helper.getBlockInstance(world, x-1, y, z) instanceof PowerConduit){
			glow[Helper.dirXNeg] = yellow;
		}else{
			glow[Helper.dirXNeg] = red;
		}
		if(Helper.getBlockInstance(world, x, y, z+1) instanceof IonTube){
			glow[Helper.dirZPos] = blue;
		}else if(Helper.getBlockInstance(world, x, y, z+1) instanceof PowerConduit){
			glow[Helper.dirZPos] = yellow;
		}else{
			glow[Helper.dirZPos] = red;
		}
		if(Helper.getBlockInstance(world, x, y, z-1) instanceof IonTube){
			glow[Helper.dirZNeg] = blue;
		}else if(Helper.getBlockInstance(world, x, y, z-1) instanceof PowerConduit){
			glow[Helper.dirZNeg] = yellow;
		}else{
			glow[Helper.dirZNeg] = red;
		}
		particleIonizer.overrideTextures(tmap);
		renderer.renderStandardBlock(particleIonizer, x, y, z);
		particleIonizer.overrideTextures(glow);
		renderer.renderStandardBlock(particleIonizer, x, y, z);
		particleIonizer.restoreTextures();
		return true;
	}
}