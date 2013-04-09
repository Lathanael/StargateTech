package lordfokas.stargatetech.networks.ion;

import net.minecraft.nbt.NBTTagCompound;
import lordfokas.stargatetech.api.networks.IonNetTE.IIonStorage;
import lordfokas.stargatetech.networks.NetworkTE;

public class BaseIonNetStorageTE extends NetworkTE implements IIonStorage{
	/** Current amount of ions in this machine's buffer. */
	protected int ionAmount = 0;
	/** Maximum amount of ions this machine's buffer can store */
	protected int ionBufferSize = 0;
	
	protected BaseIonNetStorageTE(int buffersize){
		ionBufferSize = buffersize;
	}
	
	@Override
	public int getIonAmount() {
		return ionAmount;
	}
	
	@Override
	public int getIonBufferSize() {
		return ionBufferSize;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		ionAmount = nbt.getInteger("ionAmount");
		ionBufferSize = nbt.getInteger("ionBufferSize");
	}
	
    @Override
    public void writeToNBT(NBTTagCompound nbt){
    	super.writeToNBT(nbt);
    	nbt.setInteger("ionAmount", ionAmount);
    	nbt.setInteger("ionBufferSize", ionBufferSize);
    }
}
