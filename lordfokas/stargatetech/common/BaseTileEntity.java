package lordfokas.stargatetech.common;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class BaseTileEntity extends TileEntity {
	
	public Packet132TileEntityData getDescriptionPacket(){
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, nbt);
    }
	
	public void onDataPacket(INetworkManager net, Packet132TileEntityData packet){
		NBTTagCompound nbt = packet.customParam1;
		if(nbt != null){
			this.readFromNBT(nbt);
		}
    }
	
	public void updateClients(){
		if(worldObj.isRemote) return;
		Packet132TileEntityData packet = this.getDescriptionPacket();
		PacketDispatcher.sendPacketToAllInDimension(packet, this.worldObj.provider.dimensionId);
	}
}