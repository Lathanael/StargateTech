package lordfokas.stargatetech.networks.power;

import net.minecraft.world.World;

public interface IPowerNetSource extends IPowerNetComponent{
	public int requestPower(World w, int x, int y, int z, int p);
	public void giveBack(World w, int x, int y, int z, int p);
	public float getFill(World w, int x, int y, int z);
}