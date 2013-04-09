package lordfokas.stargatetech.api;

import net.minecraft.world.World;

/**
 * A disintegrable block.
 * When right clicking with a disintegrator on a block that implements this interface, 'disintegrate' will be called on that block.
 * @author LordFokas
 */
public interface IDisintegrable {
	public boolean disintegrate(World w, int x, int y, int z);
}
