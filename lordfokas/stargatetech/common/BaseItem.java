package lordfokas.stargatetech.common;

import net.minecraft.item.Item;
import lordfokas.stargatetech.ClientProxy;
import lordfokas.stargatetech.StargateTech;

public class BaseItem extends Item {

	public BaseItem(int id, int textureIndex) {
		super(id);
		this.setCreativeTab(StargateTech.instance.tab);
		iconIndex = textureIndex;
	}
	
	@Override
	public String getTextureFile(){
		return ClientProxy.ITEM_TEXTURES;
	}
}
