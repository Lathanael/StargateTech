package lordfokas.stargatetech.networks.bus;

import lordfokas.stargatetech.api.networks.IBusPacket;

public abstract class BusPacket implements IBusPacket{
	private byte packetID;
	
	protected BusPacket(byte ID){
		this.packetID = ID;
	}
	
	public byte getPacketID(){
		return packetID;
	}
}