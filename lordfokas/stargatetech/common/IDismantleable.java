package lordfokas.stargatetech.common;

import net.minecraft.world.World;

/**
 * A dismantleable block.
 * When right clicking with a dismantler on a block that implements this interface, 'dismantle' will be called on that block.
 * @author LordFokas
 */
public interface IDismantleable {
	public boolean dismantle(World w, int x, int y, int z);
}