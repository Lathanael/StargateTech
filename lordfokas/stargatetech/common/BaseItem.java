package lordfokas.stargatetech.common;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import lordfokas.stargatetech.StargateTech;

/**
 * Common code for items. Not really much for now.
 * @author LordFokas
 */
public class BaseItem extends Item {
	protected String texture;

	public BaseItem(int id, String name) {
		super(id);
		texture = name;
		setUnlocalizedName(name);
		this.setCreativeTab(StargateTech.instance.tab);
	}
	
	@Override
	public void updateIcons(IconRegister register){
		iconIndex = register.registerIcon("StargateTech:" + texture);
	}
}
