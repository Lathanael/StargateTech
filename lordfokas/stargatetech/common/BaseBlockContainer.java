package lordfokas.stargatetech.common;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import lordfokas.stargatetech.ClientProxy;
import lordfokas.stargatetech.StargateTech;
import lordfokas.stargatetech.util.IOverrideableTexture;

public abstract class BaseBlockContainer extends BlockContainer implements IOverrideableTexture {
	protected int[] override = new int[6];
	protected boolean isOverride = false;
	
	public BaseBlockContainer(int id, int textureIndex) {
		this(id, textureIndex, Material.rock, true);
	}
	
	public BaseBlockContainer(int id, int textureIndex, boolean hard) {
		this(id, textureIndex, Material.rock, hard);
	}
	
	public BaseBlockContainer(int id, int textureIndex, Material material, boolean hard) {
		super(id, textureIndex, material);
		if(hard){
			this.setBlockUnbreakable();
			this.setResistance(20000000F);
		}
		this.setCreativeTab(StargateTech.tab);
	}
	
	@Override
	public String getTextureFile(){
		return ClientProxy.BLOCK_TEXTURES;
	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata){
		if(isOverride) return override[side];
		else return getBlockTextureFromSide(side);
	}
	
	@Override
	public void overrideTextures(int[] tmap){
		isOverride = true;
		override = tmap;
	}
	
	@Override
	public void restoreTextures()
		{ isOverride = false; }
}
