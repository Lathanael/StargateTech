package lordfokas.stargatetech.networks.bus;

public abstract class BusPacket {
	public static final byte PKT_DIAL_STARGATE = 0x01;
	
	private byte packetID;
	
	protected BusPacket(byte ID){
		this.packetID = ID;
	}
	
	public byte getPacketID(){
		return packetID;
	}
}