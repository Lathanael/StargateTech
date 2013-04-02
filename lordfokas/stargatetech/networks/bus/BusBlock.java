package lordfokas.stargatetech.networks.bus;

import net.minecraft.world.World;

/**
 * Specification for the Abstract Bus
 * 
 * @author LordFokas
 */
public final class BusBlock {
	private BusBlock(){}
	
	public interface IBusComponent{
		public boolean isPropagator();
		public boolean isConnector();
		public boolean canBusPlugOnSide(World w, int x, int y, int z, int side);
	}
	
	public interface IBusConnector extends IBusComponent{
		public boolean canHandlePacketType(World w, int x, int y, int z, Class <? extends BusPacket> packetType);
		public void handlePacket(World w, int x, int y, int z, BusPacket packet);
		public int getBusConnectorID();
	}
}