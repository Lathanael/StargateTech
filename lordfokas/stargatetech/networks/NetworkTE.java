package lordfokas.stargatetech.networks;

import lordfokas.stargatetech.util.CoordinateSet;
import net.minecraft.tileentity.TileEntity;

public abstract class NetworkTE extends TileEntity implements INetworkTE{
	protected CoordinateSet position;
	
	@Override
	public void validate(){
		super.validate();
		position = new CoordinateSet(worldObj, xCoord, yCoord, zCoord);
	}

	@Override
	public CoordinateSet getPosition() {
		return position;
	}
}
