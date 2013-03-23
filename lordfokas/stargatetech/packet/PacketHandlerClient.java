package lordfokas.stargatetech.packet;

import com.google.common.io.ByteArrayDataInput;

import lordfokas.stargatetech.util.ByteUtil;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

/**
 * Client Sided Packet Handler.
 * @author LordFokas
 */
public class PacketHandlerClient extends PacketHandler {
	public static final PacketHandlerClient instance = new PacketHandlerClient();
	private PacketHandlerClient(){}
	
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		
	}
	
	public static void sendDialingComputerButtonHit(int x, int y, int z, byte type, byte button){
		byte[] data = new byte[15];
		data[0] = PACKET_DIALING_COMPUTER_BUTTON;
		data[13] = type;
		data[14] = button;
		ByteUtil.writeInt(x, data, 1);
		ByteUtil.writeInt(y, data, 5);
		ByteUtil.writeInt(z, data, 9);
		
		PacketDispatcher.sendPacketToServer(new Packet250CustomPayload(CHANNEL_STARGATE, data));
	}
}