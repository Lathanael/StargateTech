package lordfokas.stargatetech.api.networks;

import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * PowerNet Block Specification.
 * Blocks that belong to the PowerNet should implement at least IPowerNetComponent,
 * no matter what TileEntities those blocks might have.
 * 
 * @author LordFokas
 */
public final class PowerNetBlock {
	private PowerNetBlock(){}
	
	/** A PowerNet component. Every PowerNet block MUST implement this. */
	public interface IPowerNetComponent {
		/** Available types of for PowerNetComponent. */
		public enum EPowerComponentType{
			/** A sink. Draws power from the network. */
			SINK,
			
			/** A source. Supplies sinks with power. May be a generator. */
			SOURCE,
			
			/** A relay. It's both a source and a sink. Shouldn't be a generator. */
			RELAY,
			
			/** A conduit. Transports power between other network components. */
			CONDUIT;
		}
		
		/** What type of component this PowerNetComponent is. */
		public EPowerComponentType getPowerComponentType();
		/** Can a power conduit connect on a specific side of this component? */
		public boolean canConduitConnectOnSide(IBlockAccess w, int x, int y, int z, int side);
	}
	
	/** A PowerNetSource. Can output power into the network. */
	public interface IPowerNetSource extends IPowerNetComponent{
		/** Request power. Return as much power as possible, up to the request's value. */
		public int requestPower(World w, int x, int y, int z, int p);
		/** Give power back from an earlier request. Related to StaticPowerNetRouter's design. */
		public void giveBack(World w, int x, int y, int z, int p);
		/** How full this component is (0.0F - 1.0F). StaticPowerNetRouter will always get power from the fullest component available first. */
		public float getFill(World w, int x, int y, int z);
	}
}