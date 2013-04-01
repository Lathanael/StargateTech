/**
 * 
 */
package lathanael.stargatetech.item;

import lathanael.stargatetech.entity.EntityStargatePainting;
import lordfokas.stargatetech.StargateTech;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHangingEntity;
import net.minecraft.world.World;

/**
 * @author Lathanael (aka Philippe Leipold)
 * 
 */
public class StargatePainting extends ItemHangingEntity {

	public StargatePainting(final int id,
			final Class entityClass, final String name) {
		super(id, entityClass);
		this.setCreativeTab(StargateTech.tab);
		setUnlocalizedName(name);
	}

	@Override
	public void updateIcons(IconRegister register){
		iconIndex = register.registerIcon("painting");
	}
	
	@SuppressWarnings("unused")
	private EntityHanging createHangingEntity(World world, int x, int y, int z,
			int direction) {
		return (EntityHanging) (new EntityStargatePainting(world, x, y, z,
				direction));
	}
}
