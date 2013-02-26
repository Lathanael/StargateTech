package lordfokas.stargatetech.common;

import net.minecraft.world.World;

public interface IDisintegrable {
	public boolean disintegrate(World w, int x, int y, int z);
}
