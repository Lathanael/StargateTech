package lordfokas.stargatetech.networks.bus;

import net.minecraft.world.World;
import lordfokas.stargatetech.util.CoordinateSet;

/**
 * A CoordinateSet that represents a connection from a source and a target objects.
 * Knows which faces are being used in the connection on both sides.
 * It is used internally by the Bus system to route packets.
 * @author LordFokas
 */
public class BusConnection extends CoordinateSet{
	public int sourceSide, targetSide;
	public boolean cornerIn = false, cornerOut = false;
	
	/**
	 * @param w The World object for this connection
	 * @param x Source X coordinate
	 * @param y Source Y coordinate
	 * @param z Source Z coordinate
	 * @param src Side the source is connecting with
	 * @param tgt Side the target is connecting with
	 */
	public BusConnection(World w, int x, int y, int z, int src, int tgt) {
		super(w, x, y, z);
		sourceSide = src;
		targetSide = tgt;
	}
	
}
