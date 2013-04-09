package lordfokas.stargatetech.networks.bus;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;

import lordfokas.stargatetech.api.networks.BusBlock.IBusComponent;
import lordfokas.stargatetech.api.networks.BusBlock.IBusConnector;
import lordfokas.stargatetech.api.networks.BusBlock.IBusPropagator;
import lordfokas.stargatetech.api.networks.BusConnection;
import lordfokas.stargatetech.api.networks.IBusPacket;
import lordfokas.stargatetech.util.CoordinateSet;
import lordfokas.stargatetech.util.Helper;
import lordfokas.stargatetech.util.StargateLogger;

public final class Bus {
	private Bus(){}
	
	public static void propagatePacket(IBusPacket packet, byte targetBusID, CoordinateSet source){
		if(packet.getPacketID() == 0){
			StargateLogger.warning("Bus Error: Trying to propagate a packet of class \"" + packet.getClass().getName() + "\" with an invalid PacketID (0).");
			StargateLogger.warning("Packet propagation was canceled to avoid critical errors.");
		}else if(source.w != null && source.w.isRemote == false){
			boolean broadcast = (targetBusID == (byte) 0xFF);
			propagate0(packet, broadcast, targetBusID, source);
		}else{
			if(source.w == null){
				StargateLogger.warning("Bus Error: Trying to propagate a packet of class \"" + packet.getClass().getName() + "\" on a null world object!");
			}else{
				StargateLogger.warning("Bus Error: Trying to propagate a packet of class \"" + packet.getClass().getName() + "\" on the client side!");
			}
		}
	}
	
	private static void propagate0(IBusPacket packet, boolean broadcast, byte targetBusID, CoordinateSet source){
		ArrayList<CoordinateSet> visited = new ArrayList<CoordinateSet>();
		visited.add(source);
		IBusComponent src = (IBusComponent) Helper.getBlockInstance(source);
		for(int direction = 0; direction < 6; direction++){
			CoordinateSet derivate = source.fromDirection(direction);
			if(!visited.contains(derivate)){
				Block block = Helper.getBlockInstance(derivate);
				if(block instanceof IBusComponent){
					IBusComponent tgt = (IBusComponent) block;
					boolean tc = tgt.canBusPlugOnSide(derivate.w, derivate.x, derivate.y, derivate.z, Helper.oppositeDirection(direction), Helper.dirIgnore);
					boolean sc = src.canBusPlugOnSide(source.w, source.x, source.y, source.z, direction, Helper.oppositeDirection(direction));
					if(tc && sc){
						visited.add(derivate);
						if(tgt instanceof IBusPropagator){
							propagate1(packet, broadcast, targetBusID, derivate.clone(), visited);
						}
					}
				}
			}
		}
	}
	
	private static void propagate1(IBusPacket packet, boolean broadcast, byte targetBusID, CoordinateSet source, ArrayList<CoordinateSet> visited){
		IBusPropagator src = (IBusPropagator) Helper.getBlockInstance(source);
		List<BusConnection> links = src.getConnectionsList(source.w, source.x, source.y, source.z);
		if(links != null){
			for(BusConnection conn : links){
				if(!visited.contains(conn)){
					Block block = Helper.getBlockInstance(conn);
					if(block instanceof IBusComponent){
						IBusComponent tgt = (IBusComponent) block;
						visited.add(conn);
						if(tgt instanceof IBusConnector){
							IBusConnector connector = (IBusConnector) tgt;
							if(broadcast || connector.getBusConnectorID() == targetBusID || connector.getBusConnectorID() == 0){
								if(connector.canHandlePacketType(conn.w, conn.x, conn.y, conn.z, packet.getPacketID())){
									connector.handlePacket(conn.w, conn.x, conn.y, conn.z, packet, targetBusID);
								}
							}
						}
						if(tgt instanceof IBusPropagator){
							propagate1(packet, broadcast, targetBusID, conn.clone(), visited);
						}
					}
				}
			}
		}
	}
}