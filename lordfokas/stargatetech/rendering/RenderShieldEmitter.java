package lordfokas.stargatetech.rendering;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import lordfokas.stargatetech.api.networks.IonNetBlock.IIonNetComponent;
import lordfokas.stargatetech.api.networks.IonNetBlock.IIonNetComponent.EIonComponentType;
import lordfokas.stargatetech.common.BaseBlockContainer;
import lordfokas.stargatetech.common.BaseBlockRenderer;
import lordfokas.stargatetech.machine.ShieldEmitter;
import lordfokas.stargatetech.util.CoordinateSet;
import lordfokas.stargatetech.util.Helper;
import lordfokas.stargatetech.util.IconRegistry;

public class RenderShieldEmitter extends BaseBlockRenderer {
	private static RenderShieldEmitter INSTANCE = new RenderShieldEmitter();
	
	public static RenderShieldEmitter instance(){
		return INSTANCE;
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer){
		if(!(block instanceof ShieldEmitter)) return false;
		ShieldEmitter se = (ShieldEmitter) block;
		String type = "";
		int meta = world.getBlockMetadata(x, y, z);
		boolean top = world.getBlockId(x, y-1, z) == block.blockID && world.getBlockMetadata(x, y-1, z) == meta;
		boolean bot = world.getBlockId(x, y+1, z) == block.blockID && world.getBlockMetadata(x, y+1, z) == meta;
		if(top && bot) type = "-Link1";
		else if(top) type = "-Link0";
		else if(bot) type = "-Link2";
		Icon v = IconRegistry.empty;
		Icon s = IconRegistry.machine;
		Icon[] tmap = { s, s, s, s, s, s };
		Icon[] glow = { v, v, v, v, v, v };
		tmap[0] = tmap[1] = IconRegistry.shieldEmitterTop;
		for(int d = 2; d < 6; d++){
			if(meta == d){
				tmap[d] = ((BaseBlockContainer)block).getTexture();
			}else{
				CoordinateSet cs = new CoordinateSet(x, y, z).fromDirection(d);
				Block b = Helper.getBlockInstance(world, cs.x, cs.y, cs.z);
				if(b instanceof IIonNetComponent){
					if(((IIonNetComponent)b).getIonComponentType() == EIonComponentType.TUBE){
						tmap[d] = IconRegistry.machineSlot;
						glow[d] = IconRegistry.slot1blue;
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
