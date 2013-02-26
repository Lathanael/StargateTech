package lordfokas.stargatetech.util;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public abstract class PacketHandler implements IPacketHandler {
	public static final String CHANNEL_STARGATE = "StargateCH";
}
