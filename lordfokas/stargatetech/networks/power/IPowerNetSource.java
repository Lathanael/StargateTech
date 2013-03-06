package lordfokas.stargatetech.networks.power;

import net.minecraft.world.World;

/**
 * A Power Net component that can also supply power to the network.
 * Should be implemented by Blocks.
 * @author LordFokas
 */
public interface IPowerNetSource extends IPowerNetComponent{
	public int requestPower(World w, int x, int y, int z, int p);
	public void giveBack(World w, int x, int y, int z, int p);
	public float getFill(World w, int x, int y, int z);
}