package lordfokas.stargatetech.networks.bus;

import java.util.List;

import net.minecraft.world.IBlockAccess;

/**
 * Specification for the Bus system
 * 
 * @author LordFokas
 */
public final class BusBlock {
	private BusBlock(){}
	
	/**
	 * A Bus Component. Every block that interacts with the bus should implement this.
	 * 
	 * @author LordFokas
	 */
	public interface IBusComponent{
		/**
		 * Used to determine if another component can connect to this component on this face.
		 * 
		 * @param w World object
		 * @param x X Coordinate
		 * @param y Y Coordinate
		 * @param z Z Coordinate
		 * @param side On what face of this IBusComponent the connection is done
		 * @param cableFace On what face is the cable attempting to connect on
		 * @return Whether this component can be connected under these conditions or not.
		 */
		public boolean canBusPlugOnSide(IBlockAccess w, int x, int y, int z, int side, int cableFace);
	}
	
	/**
	 * A Bus listener. Can receive packets from the bus.
	 * 
	 * @author LordFokas
	 */
	public interface IBusConnector extends IBusComponent{
		/**
		 * Used to determine if this component should receive packets of this type.
		 * 
		 * @param w World object
		 * @param x X Coordinate
		 * @param y Y Coordinate
		 * @param z Z Coordinate
		 * @param packetType PacketID of the packet being tested.
		 * @return Whether this IBusConnector accepts this kind of packets or not.
		 */
		public boolean canHandlePacketType(IBlockAccess w, int x, int y, int z, byte packetType);
		
		/**
		 * Used to let the IBusConnector handle a packet being sent through the Bus.
		 * 
		 * @param w World object
		 * @param x X Coordinate
		 * @param y Y Coordinate
		 * @param z Z Coordinate
		 * @param packet A BusPacket accepted in IBusConnector.canHandlePacketType()
		 */
		public void handlePacket(IBlockAccess w, int x, int y, int z, BusPacket packet);
		
		/**
		 * This IBusConnector's ID.
		 * Works like a single-octet IP address, where values range from 0-255.
		 * There is nothing similar to a DHCP server, so there can be 2 devices with the same ID in the same Bus.
		 * BusID 0x00 and 0xFF have special properties.
		 * 
		 * BusPackets are propagated through the Bus class.
		 * There's a target BusID that represents which BusID (IBusConnector.getBusConnectorID())
		 * a device should have to receive that packet.
		 * Only devices with matching BusIDs will receive that packet, with two exceptions:
		 * 
		 * BROADCAST:
		 * BusID 0xFF (255) represents a broadcast. Every device will receive packets sent to BusID 0xFF
		 * no matter what BusID that device has.
		 * In the same way, devices with BusID 0xFF will only receive broadcasts.
		 * 
		 * SNIFFING:
		 * BusID 0x00 (0) represents a sniffer. Devices with BusID 0x00 will receive every single packet
		 * in the Bus, no matter what BusID that packet has.
		 * In the same way, packets sent with BusID 0x00 will only be sent to devices that are sniffing.
		 * 
		 * ProTip: Allow players to change the device's BusID.
		 * 
		 * @return this device's BusID
		 */
		public byte getBusConnectorID();
	}
	
	/**
	 * A propagator for BusPackets. The Bus system will use this interface to propagate packets across the bus.
	 * 
	 * @author LordFokas
	 */
	public interface IBusPropagator extends IBusComponent{
		/**
		 * Used to get a list of BusConnection objects representing what blocks this IBusPropagator is connected to.
		 * 
		 * ProTip: Cache this value for performance optimization.
		 * 
		 * @param w World object
		 * @param x X Coordinate
		 * @param y Y Coordinate
		 * @param z Z Coordinate
		 * @return A list of BusConnection objects.
		 */
		public List<BusConnection> getConnectionsList(IBlockAccess w, int x, int y, int z);
	}
}