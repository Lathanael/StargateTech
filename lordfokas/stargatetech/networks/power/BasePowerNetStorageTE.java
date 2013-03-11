package lordfokas.stargatetech.networks.power;

import net.minecraft.nbt.NBTTagCompound;
import lordfokas.stargatetech.networks.NetworkTE;
import lordfokas.stargatetech.networks.power.PowerNetTE.IPowerStorage;

public class BasePowerNetStorageTE extends NetworkTE implements IPowerStorage{
	protected int powerAmount = 0;
	protected int powerBufferSize = 0;
	
	protected BasePowerNetStorageTE(int buffersize){
		powerBufferSize = buffersize;
	}
	
	@Override
	public int getPowerAmount() {
		return powerAmount;
	}

	@Override
	public int getPowerBufferSize() {
		return powerBufferSize;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		powerAmount = nbt.getInteger("powerAmount");
		powerBufferSize = nbt.getInteger("powerBufferSize");
	}
	
    @Override
    public void writeToNBT(NBTTagCompound nbt){
    	super.writeToNBT(nbt);
    	nbt.setInteger("powerAmount", powerAmount);
    	nbt.setInteger("powerBufferSize", powerBufferSize);
    }
}
