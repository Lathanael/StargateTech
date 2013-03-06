package lordfokas.stargatetech.util;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.Player;

/**
 * Server Sided Packet Handler
 * @author LordFokas
 */
public class PacketHandlerServer extends PacketHandler {
	public static final PacketHandlerServer instance = new PacketHandlerServer();
	private PacketHandlerServer(){};

	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		// TODO Auto-generated method stub
		
	}

}
