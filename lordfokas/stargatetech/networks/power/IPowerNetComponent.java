package lordfokas.stargatetech.networks.power;

import net.minecraft.world.IBlockAccess;

/**
 * A component in the Power Network. Should be implemented by Blocks.
 * @author LordFokas
 */
public interface IPowerNetComponent {
	public enum EPowerComponentType{
		SINK,
		SOURCE,
		RELAY,
		CONDUIT;
	}
	
	public EPowerComponentType getPowerComponentType();
	public boolean canConduitConnectOnSide(IBlockAccess w, int x, int y, int z, int side);
}
