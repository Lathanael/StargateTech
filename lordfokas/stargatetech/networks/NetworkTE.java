package lordfokas.stargatetech.networks;

import lordfokas.stargatetech.api.networks.INetworkTE;
import lordfokas.stargatetech.common.BaseTileEntity;
import lordfokas.stargatetech.util.CoordinateSet;

public abstract class NetworkTE extends BaseTileEntity implements INetworkTE{
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
