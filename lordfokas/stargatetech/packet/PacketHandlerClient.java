package lordfokas.stargatetech.packet;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
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
		// TODO Auto-generated method stub
	}

}