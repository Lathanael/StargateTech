package lordfokas.stargatetech.networks.bus;

import java.util.ArrayList;

import net.minecraft.block.Block;

import lordfokas.stargatetech.networks.bus.BusBlock.IBusComponent;
import lordfokas.stargatetech.networks.bus.BusBlock.IBusConnector;
import lordfokas.stargatetech.util.CoordinateSet;
import lordfokas.stargatetech.util.Helper;
import lordfokas.stargatetech.util.StargateLogger;

public final class Bus {
	private Bus(){}
	
	public static void propagatePacket(BusPacket packet, byte target, CoordinateSet source){
		if(source.w != null && source.w.isRemote == false){
			boolean broadcast = (target == (byte) 0xFF);
			ArrayList<CoordinateSet> visited = new ArrayList<CoordinateSet>();
			visited.add(source);
			propagate(packet, broadcast, target, source, visited);
		}else{
			if(source.w == null){
				StargateLogger.warning("Bus Error: Trying to propagate a packet of class \"" + packet.getClass().getSimpleName() + "\" on a null world object");
			}else{
				StargateLogger.warning("Bus Error: Trying to propagate a packet of class \"" + packet.getClass().getSimpleName() + "\" on the client side");
			}
		}
	}
	
	private static void propagate(BusPacket packet, boolean broadcast, byte target, CoordinateSet source, ArrayList<CoordinateSet> visited){
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
						if(tgt.isConnector() && tgt instanceof IBusConnector){
							IBusConnector connector = (IBusConnector) tgt;
							if(broadcast || connector.getBusConnectorID() == target || connector.getBusConnectorID() == 0){
								if(connector.canHandlePacketType(derivate.w, derivate.x, derivate.y, derivate.z, packet.getPacketID())){
									connector.handlePacket(derivate.w, derivate.x, derivate.y, derivate.z, packet);
								}
							}
						}
						if(tgt.isPropagator()){
							propagate(packet, broadcast, target, derivate.clone(), visited);
						}
					}
				}
			}
		}
	}
}