/**
 * 
 */
package lathanael.stargatetech.util;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import lordfokas.stargatetech.util.Config;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.util.MathHelper;

/**
 * 
 * 
 * @author Lathanael (aka Philippe Leipold)
 */
public class BlockUtils {

	/**
	 * Checks if an entity is placed on a valid Stargateblock.
	 * 
	 * @param entity
	 *            The entity which should be checked.
	 * @return {@code true} if the entity is placed on a Stargate block else
	 *         {@code false}
	 */
	public static boolean onValidStargateBlock(final EntityPainting entity) {
		int id = 0;
		if (entity.hangingDirection == 0) {
			id = entity.worldObj.getBlockId(entity.xPosition + 1,
					entity.yPosition, entity.zPosition);
		}

		if (entity.hangingDirection == 2) {
			id = entity.worldObj.getBlockId(entity.xPosition - 1,
					entity.yPosition, entity.zPosition);
		}

		if (entity.hangingDirection == 1) {
			id = entity.worldObj.getBlockId(entity.xPosition, entity.yPosition,
					entity.zPosition + 1);
		}

		if (entity.hangingDirection == 3) {
			id = entity.worldObj.getBlockId(entity.xPosition, entity.yPosition,
					entity.zPosition - 1);
		}

		StargateLogger.log(Level.INFO, Integer.toString(id));
		if (id == Config.naquadahOre) {
			return true;
		}
		return false;
	}
}