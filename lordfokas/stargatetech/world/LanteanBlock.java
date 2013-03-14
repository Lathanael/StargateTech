package lordfokas.stargatetech.world;

import lordfokas.stargatetech.common.BaseBlock;
import lordfokas.stargatetech.util.Helper;
import lordfokas.stargatetech.util.TextureIndex;

/**
 * This was a block which would have several metadata sub blocks (16, actually)
 * It would be used as the mod's "Fancy Block". I still want to implement it,
 * so this stub stays. For now, at least.
 * @author LordFokas
 */
public class LanteanBlock extends BaseBlock {
	public LanteanBlock(int id) {
		super(id, TextureIndex.lanteanQuadSide);
		this.setBlockName("lanteanBlock");
	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta){
		if(side != Helper.dirTop) return TextureIndex.lanteanQuadSide;
		if(meta == 0) return TextureIndex.lanteanHexFloor;
		return TextureIndex.lanteanQuadEmit;
	}
}
