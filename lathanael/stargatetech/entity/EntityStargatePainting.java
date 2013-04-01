/**
 * 
 */
package lathanael.stargatetech.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumArt;
import net.minecraft.world.World;

/**
 * @author Lathanael (aka Philippe Leipold)
 * 
 */
public class EntityStargatePainting extends EntityPainting {

	public EntityStargatePainting() {
		super(null);
	}
	public EntityStargatePainting(World par1World) {
		super(par1World);
	}

	public EntityStargatePainting(final World world, int x, int y, int z,
			int direction) {
		super(world, x, y, z, direction);
	}

	public EntityStargatePainting(final EntityPainting entity) {
		super(entity.worldObj, entity.xPosition, entity.yPosition,
				entity.zPosition, entity.hangingDirection);
		this.setDirection(entity.hangingDirection);
	}

	@SideOnly(Side.CLIENT)
	public EntityStargatePainting(final EntityPainting entity, final String name) {
		super(entity.worldObj, entity.xPosition, entity.yPosition,
				entity.zPosition, entity.hangingDirection);
		this.setDirection(entity.hangingDirection);
	}

	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
		String s = par1NBTTagCompound.getString("Motive");
		EnumArt[] aenumart = EnumArt.values();
		int i = aenumart.length;

		for (int j = 0; j < i; ++j) {
			EnumArt enumart = aenumart[j];

			if (enumart.title.equals(s)) {
				this.art = enumart;
			}
		}

		if (this.art == null) {
			this.art = EnumArt.Kebab;
		}

		super.readEntityFromNBT(par1NBTTagCompound);
	}
}
