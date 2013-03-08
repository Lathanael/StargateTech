package lordfokas.stargatetech.common;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

/**
 * Custom item renderer base code.
 * It will be used later to give Dismantler / Disintegrator / Mechanus Clavia a better (3D) look.
 * @author LordFokas
 */
public abstract class BaseItemRenderer implements IItemRenderer {

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch(type){
		case ENTITY:
			renderEntity(item, data);
			break;
		case EQUIPPED:
			renderEquipped(item, data);
			break;
		case FIRST_PERSON_MAP:
			renderFirstPerson(item, data);
			break;
		case INVENTORY:
			renderInventory(item, data);
			break;
		}
	}
	
	protected void renderEntity(ItemStack item, Object... data){}
	protected void renderEquipped(ItemStack item, Object... data){}
	protected void renderFirstPerson(ItemStack item, Object... data){}
	protected void renderInventory(ItemStack item, Object... data){}
}
