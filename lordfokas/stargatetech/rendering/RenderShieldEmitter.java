package lordfokas.stargatetech.rendering;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import lordfokas.stargatetech.common.BaseBlock;
import lordfokas.stargatetech.common.BaseBlockRenderer;
import lordfokas.stargatetech.machine.ShieldEmitter;
import lordfokas.stargatetech.networks.ion.IIonNetComponent;
import lordfokas.stargatetech.networks.ion.IIonNetComponent.EIonComponentType;
import lordfokas.stargatetech.util.CoordinateSet;
import lordfokas.stargatetech.util.Helper;
import lordfokas.stargatetech.util.TextureIndex;

public class RenderShieldEmitter extends BaseBlockRenderer {
	private static RenderShieldEmitter INSTANCE = new RenderShieldEmitter();
	
	public static RenderShieldEmitter instance(){
		return INSTANCE;
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer){
		if(!(block instanceof ShieldEmitter)) return false;
		ShieldEmitter se = (ShieldEmitter) block;
		int type = 0;
		int meta = world.getBlockMetadata(x, y, z);
		boolean top = world.getBlockId(x, y-1, z) == block.blockID && world.getBlockMetadata(x, y-1, z) == meta;
		boolean bot = world.getBlockId(x, y+1, z) == block.blockID && world.getBlockMetadata(x, y+1, z) == meta;
		if(top && bot) type = 2;
		else if(top) type = 1;
		else if(bot) type = 3;
		type *= 0x10;
		int v = TextureIndex.voidTexture;
		int s = type + TextureIndex.blockSingle;
		int[] tmap = { s, s, s, s, s, s };
		int[] glow = { v, v, v, v, v, v };
		tmap[0] = tmap[1] = TextureIndex.shieldEmitterYFace;
		for(int d = 2; d < 6; d++){
			if(meta == d){
				tmap[d] = type + TextureIndex.shieldEmitterSingle;
			}else{
				CoordinateSet cs = new CoordinateSet(x, y, z).fromDirection(d);
				Block b = Helper.getBlockInstance(world, cs.x, cs.y, cs.z);
				if(b instanceof IIonNetComponent){
					if(((IIonNetComponent)b).getIonComponentType() == EIonComponentType.TUBE){
						tmap[d] = type + TextureIndex.networkSlot;
						glow[d] = TextureIndex.ionTubeBlueLight;
					}
				}
			}
		}
		se.overrideTextures(tmap);
		renderer.renderStandardBlock(se, x, y, z);
		se.overrideTextures(glow);
		renderer.renderStandardBlock(se, x, y, z);
		se.restoreTextures();
		return false;
	}
}
