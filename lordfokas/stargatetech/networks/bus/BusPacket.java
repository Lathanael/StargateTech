package lordfokas.stargatetech.networks.bus;

public abstract class BusPacket {
	protected byte[] data;
	protected int length;
	
	protected BusPacket(){}
	
	protected BusPacket(byte[] data){
		this.data = data;
	}
	
	public byte[] getData(){
		return data;
	}
	
	public int getLength(){
		return length;
	}
}