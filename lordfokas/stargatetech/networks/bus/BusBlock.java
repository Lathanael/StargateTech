package lordfokas.stargatetech.networks.bus;

import net.minecraft.world.IBlockAccess;

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
		public boolean canBusPlugOnSide(IBlockAccess w, int x, int y, int z, int side);
	}
	
	public interface IBusConnector extends IBusComponent{
		public boolean canHandlePacketType(IBlockAccess w, int x, int y, int z, byte packetType);
		public void handlePacket(IBlockAccess w, int x, int y, int z, BusPacket packet);
		public int getBusConnectorID();
	}
}