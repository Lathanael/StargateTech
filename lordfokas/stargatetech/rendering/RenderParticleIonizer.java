package lordfokas.stargatetech.rendering;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import lordfokas.stargatetech.common.BaseBlockRenderer;
import lordfokas.stargatetech.machine.IonTube;
import lordfokas.stargatetech.machine.ParticleIonizer;
import lordfokas.stargatetech.machine.PowerConduit;
import lordfokas.stargatetech.util.Helper;
import lordfokas.stargatetech.util.TextureIndex;

public class RenderParticleIonizer extends BaseBlockRenderer{
	private static RenderParticleIonizer INSTANCE = new RenderParticleIonizer();
	
	public static RenderParticleIonizer instance(){
		return INSTANCE;
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer){
		if(!(block instanceof ParticleIonizer)) return false;
		ParticleIonizer particleIonizer = (ParticleIonizer) block;
		int v = TextureIndex.voidTexture;
		int s = TextureIndex.networkSlot;
		int[] tmap = { s, s, s, s, s, s };
		int[] glow = { v, v, v, v, v, v };
		tmap[0] = tmap[1] = TextureIndex.particleIonizerYFace;
		if(Helper.getBlockInstance(world, x+1, y, z) instanceof IonTube){
			glow[Helper.dirXPos] = TextureIndex.ionTubeBlueLight;
		}else if(Helper.getBlockInstance(world, x+1, y, z) instanceof PowerConduit){
			glow[Helper.dirXPos] = TextureIndex.powerConduitYellowLight;
		}else{
			glow[Helper.dirXPos] = TextureIndex.slotRedLight;
		}
		if(Helper.getBlockInstance(world, x-1, y, z) instanceof IonTube){
			glow[Helper.dirXNeg] = TextureIndex.ionTubeBlueLight;
		}else if(Helper.getBlockInstance(world, x-1, y, z) instanceof PowerConduit){
			glow[Helper.dirXNeg] = TextureIndex.powerConduitYellowLight;
		}else{
			glow[Helper.dirXNeg] = TextureIndex.slotRedLight;
		}
		if(Helper.getBlockInstance(world, x, y, z+1) instanceof IonTube){
			glow[Helper.dirZPos] = TextureIndex.ionTubeBlueLight;
		}else if(Helper.getBlockInstance(world, x, y, z+1) instanceof PowerConduit){
			glow[Helper.dirZPos] = TextureIndex.powerConduitYellowLight;
		}else{
			glow[Helper.dirZPos] = TextureIndex.slotRedLight;
		}
		if(Helper.getBlockInstance(world, x, y, z-1) instanceof IonTube){
			glow[Helper.dirZNeg] = TextureIndex.ionTubeBlueLight;
		}else if(Helper.getBlockInstance(world, x, y, z-1) instanceof PowerConduit){
			glow[Helper.dirZNeg] = TextureIndex.powerConduitYellowLight;
		}else{
			glow[Helper.dirZNeg] = TextureIndex.slotRedLight;
		}
		particleIonizer.overrideTextures(tmap);
		renderer.renderStandardBlock(particleIonizer, x, y, z);
		particleIonizer.overrideTextures(glow);
		renderer.renderStandardBlock(particleIonizer, x, y, z);
		particleIonizer.restoreTextures();
		return true;
	}
}