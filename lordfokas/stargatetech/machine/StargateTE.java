package lordfokas.stargatetech.machine;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import lordfokas.stargatetech.networks.stargate.Address;
import lordfokas.stargatetech.networks.stargate.StargateNetwork;
import lordfokas.stargatetech.util.Helper;

public class StargateTE extends TileEntity {
	public static final String ID = "StargateTE";
	public Address addr;
	
	public StargateTE(){}
	
	@Override
	public void validate(){
		super.validate();
		addr = StargateNetwork.instance().getMyAddress(worldObj, xCoord, yCoord, zCoord);
		if(addr != null) System.out.println("NEW ADDR: " + addr.getName());
	}
	
	public int getDirection(){
		return worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
	}
	
	public void setDirection(int d){
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, d, Helper.SETBLOCK_NO_UPDATE);
	}
	
	@Override
	public boolean canUpdate(){
		return false;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox(){
		return AxisAlignedBB.getAABBPool().getAABB(xCoord - 3, xCoord + 4, yCoord, yCoord+7, zCoord - 3, zCoord + 4);
	}
	
	@Override
	public double func_82115_m(){
		return 65536D;
	}
}
