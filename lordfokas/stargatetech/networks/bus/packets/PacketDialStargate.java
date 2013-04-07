package lordfokas.stargatetech.networks.bus.packets;

import lordfokas.stargatetech.networks.bus.BusPacket;
import lordfokas.stargatetech.networks.stargate.Address;

public class PacketDialStargate extends BusPacket{
	private Address address;
	
	public PacketDialStargate(Address address){
		super(PKT_DIAL_STARGATE);
		this.address = address;
	}
	
	public Address getAddress(){
		return address;
	}
}