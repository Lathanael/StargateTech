package lordfokas.stargatetech.common;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import lordfokas.stargatetech.StargateTech;
import lordfokas.stargatetech.util.Config;
import lordfokas.stargatetech.util.IconRegistry;

/**
 * A base for all the blocks used on this mod.
 * @author LordFokas
 */
public class BaseBlock extends Block{
	protected Icon[] override = new Icon[6];
	protected boolean isOverride = false;
	protected String texturename;
	
	public BaseBlock(int id, String name) {
		this(id, name, Material.rock, true);
	}
	
	public BaseBlock(int id, String name, boolean hard) {
		this(id, name, Material.rock, hard);
	}
	
	public BaseBlock(int id, String name, Material material, boolean hard) {
		super(id, material);
		if(hard){
			this.setBlockUnbreakable();
			this.setResistance(20000000F);
		}
		texturename = name;
		this.setUnlocalizedName(name);
		if(id != Config.shield && id != Config.placeholder) this.setCreativeTab(StargateTech.tab);
	}
	
	@Override
	public Icon getBlockTextureFromSideAndMetadata(int side, int metadata){
		if(isOverride) return override[side];
		else return getTextureFromSide(side, metadata);
	}
	
	public Icon getTextureFromSide(int side, int meta){
		return getTextureFromSide(side);
	}
	
	public Icon getTextureFromSide(int side){
		return getTexture();
	}
	
	public Icon getTexture(){
		return this.blockIcon;
	}
	
	@Override
	public void registerIcons(IconRegister register){
		this.blockIcon = register.registerIcon("StargateTech:" + texturename);
	}
	
	public void overrideTextures(Icon[] tmap){
		isOverride = true;
		override = tmap;
	}
	
	public void restoreTextures()
		{ isOverride = false; }
	
	@Override
	public int getMobilityFlag()
		{ return 2; }
}
