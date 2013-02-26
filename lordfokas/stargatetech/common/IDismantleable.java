package lordfokas.stargatetech.common;

import net.minecraft.world.World;

public interface IDismantleable {
	public boolean dismantle(World w, int x, int y, int z);
}