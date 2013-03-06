package lordfokas.stargatetech.networks.ion;

import net.minecraft.world.World;

/**
 * Not only a component, but also an Ion Source.
 * Blocks that implement this can supply Ions to the Network.
 * @author LordFokas
 */
public interface IIonNetSource extends IIonNetComponent{
	public int requestIons(World w, int x, int y, int z, int ions);
	public void giveBack(World w, int x, int y, int z, int ions);
	public float getFill(World w, int x, int y, int z);
}
