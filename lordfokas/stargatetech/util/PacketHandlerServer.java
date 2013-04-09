package lordfokas.stargatetech.util;

import lordfokas.stargatetech.machine.DialingComputerTE;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.Player;

/**
 * Server Sided Packet Handler
 * @author LordFokas
 */
public class PacketHandlerServer extends PacketHandler {
	public static final PacketHandlerServer instance = new PacketHandlerServer();
	private PacketHandlerServer(){};

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player p) {
		if(p instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) p;
			byte[] data = packet.data;
			switch(data[0]){
				case PACKET_DIALING_COMPUTER_BUTTON:
					handleDialingComputerButton(data, player);
					break;
			}
		}else{
			System.out.println("[StargateTech] Packet Handler couldn't handle packet because player isn't EntityPlayerMP!!");
		}
	}
	
	public void handleDialingComputerButton(byte[] data, EntityPlayer player){
		int x = ByteUtil.readInt(data, 1);
		int y = ByteUtil.readInt(data, 5);
		int z = ByteUtil.readInt(data, 9);
		byte type = data[13];
		byte button = data[14];
		TileEntity te = player.worldObj.getBlockTileEntity(x, y, z);
		if(te != null && te instanceof DialingComputerTE){
			((DialingComputerTE)te).onButtonClick(type, button);
		}else{
			System.out.println("[StargateTech] Not a Dialing Computer!");
		}
	}
}
