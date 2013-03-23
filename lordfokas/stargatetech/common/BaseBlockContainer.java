package lordfokas.stargatetech.common;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import lordfokas.stargatetech.StargateTech;
import lordfokas.stargatetech.util.IOverrideableTexture;
import lordfokas.stargatetech.util.IconRegistry;

/**
 * A base for all the tile entity container blocks used by this mod.
 * @author LordFokas
 */
public abstract class BaseBlockContainer extends BlockContainer implements IOverrideableTexture {
	protected Icon[] override = new Icon[6];
	protected boolean isOverride = false;
	protected String texturename;
	
	public BaseBlockContainer(int id, String name) {
		this(id, name, Material.rock, true);
	}
	
	public BaseBlockContainer(int id, String name, boolean hard) {
		this(id, name, Material.rock, hard);
	}
	
	public BaseBlockContainer(int id, String name, Material material, boolean hard) {
		super(id, material);
		texturename = name;
		this.setUnlocalizedName(name);
		if(hard){
			this.setBlockUnbreakable();
			this.setResistance(20000000F);
		}
		this.setCreativeTab(StargateTech.tab);
	}
	
	@Override
	public Icon getBlockTextureFromSideAndMetadata(int side, int metadata){
		if(isOverride) return override[side];
		else return getTextureFromSide(side);
	}
	
	public Icon getTextureFromSide(int side){
		return getTexture();
	}
	
	public Icon getTexture(){
		return this.field_94336_cN;
	}
	
	@Override
	public void func_94332_a(IconRegister register){
		this.field_94336_cN = register.func_94245_a("StargateTech:" + texturename);
	}
	
	@Override
	public void overrideTextures(Icon[] tmap){
		isOverride = true;
		override = tmap;
	}
	
	@Override
	public void restoreTextures()
		{ isOverride = false; }
}
