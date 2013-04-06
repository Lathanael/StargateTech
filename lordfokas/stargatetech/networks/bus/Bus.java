package lordfokas.stargatetech.networks.bus;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;

import lordfokas.stargatetech.networks.bus.BusBlock.IBusComponent;
import lordfokas.stargatetech.networks.bus.BusBlock.IBusConnector;
import lordfokas.stargatetech.networks.bus.BusBlock.IBusPropagator;
import lordfokas.stargatetech.util.CoordinateSet;
import lordfokas.stargatetech.util.Helper;
import lordfokas.stargatetech.util.StargateLogger;

public final class Bus {
	private Bus(){}
	
	public static void propagatePacket(BusPacket packet, byte target, CoordinateSet source){
		if(source.w != null && source.w.isRemote == false){
			boolean broadcast = (target == (byte) 0xFF);
			propagate0(packet, broadcast, target, source);
		}else{
			if(source.w == null){
				StargateLogger.warning("Bus Error: Trying to propagate a packet of class \"" + packet.getClass().getSimpleName() + "\" on a null world object!");
			}else{
				StargateLogger.warning("Bus Error: Trying to propagate a packet of class \"" + packet.getClass().getSimpleName() + "\" on the client side!");
			}
		}
	}
	
	private static void propagate0(BusPacket packet, boolean broadcast, byte target, CoordinateSet source){
		ArrayList<CoordinateSet> visited = new ArrayList<CoordinateSet>();
		visited.add(source);
		IBusComponent src = (IBusComponent) Helper.getBlockInstance(source);
		for(int direction = 0; direction < 6; direction++){
			CoordinateSet derivate = source.fromDirection(direction);
			if(!visited.contains(derivate)){
				Block block = Helper.getBlockInstance(derivate);
				if(block instanceof IBusComponent){
					IBusComponent tgt = (IBusComponent) block;
					boolean tc = tgt.canBusPlugOnSide(derivate.w, derivate.x, derivate.y, derivate.z, Helper.oppositeDirection(direction));
					boolean sc = src.canBusPlugOnSide(source.w, source.x, source.y, source.z, direction);
					if(tc && sc){
						visited.add(derivate);
						if(tgt instanceof IBusPropagator){
							propagate1(packet, broadcast, target, derivate.clone(), visited);
						}
					}
				}
			}
		}
	}
	
	private static void propagate1(BusPacket packet, boolean broadcast, byte target, CoordinateSet source, ArrayList<CoordinateSet> visited){
		IBusPropagator src = (IBusPropagator) Helper.getBlockInstance(source);
		List<BusConnection> links = src.getConnectionsList(source.w, source.x, source.y, source.z);
		if(links != null){
			for(BusConnection conn : links){
				if(!visited.contains(conn)){
					Block block = Helper.getBlockInstance(conn);
					if(block instanceof IBusComponent){
						IBusComponent tgt = (IBusComponent) block;
						boolean tc = tgt.canBusPlugOnSide(conn.w, conn.x, conn.y, conn.z, conn.targetSide);
						boolean sc = src.canBusPlugOnSide(source.w, source.x, source.y, source.z, conn.sourceSide);
						if(tc && sc){
							visited.add(conn);
							if(tgt instanceof IBusConnector){
								IBusConnector connector = (IBusConnector) tgt;
								if(broadcast || connector.getBusConnectorID() == target || connector.getBusConnectorID() == 0){
									if(connector.canHandlePacketType(conn.w, conn.x, conn.y, conn.z, packet.getPacketID())){
										connector.handlePacket(conn.w, conn.x, conn.y, conn.z, packet);
									}
								}
							}
							if(tgt instanceof IBusPropagator){
								propagate1(packet, broadcast, target, conn.clone(), visited);
							}
						}
					}
				}
			}
		}
	}
}