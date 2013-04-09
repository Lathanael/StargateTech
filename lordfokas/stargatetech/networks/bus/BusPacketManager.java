package lordfokas.stargatetech.networks.bus;

import java.util.Hashtable;

import lordfokas.stargatetech.util.StargateLogger;

public final class BusPacketManager{
	public final byte PKT_DIAL_STARGATE;
	
	private static BusPacketManager mgr;
	
	private Hashtable<Byte, String> packets;
	private short packetCount = 1;
	
	public static void init(){
		mgr = new BusPacketManager();
	}
	
	public static BusPacketManager manager(){
		return manager();
	}
	
	private BusPacketManager(){
		packets = new Hashtable<Byte, String>(256);
		packets.put((byte)0, "INVALID_PACKET");
		PKT_DIAL_STARGATE = getNewPacketID("dialStargate");
	}
	
	public byte getNewPacketID(String packetName){
		if(packetCount < 256){
			if(packets.containsValue(packetName)){
				StargateLogger.warning("Bus Packet " + packetName + " was already registered under another ID. Denying registration.");
				return 0;
			}else{
				byte id = (byte)(packetCount & 0xFF);
				packets.put(id, packetName);
				StargateLogger.info("Successfully registered Bus Packet \"" + packetName + "\" with ID " + id);
				return id;
			}
		}else{
			StargateLogger.warning("Couldn't register Bus Packet because packet list is full (255 elements).");
		}
		return 0;
	}
}