package lordfokas.stargatetech.networks;

import net.minecraft.nbt.NBTTagCompound;
import lordfokas.stargatetech.api.networks.IonNetTE.IIonGenerator;
import lordfokas.stargatetech.networks.power.BasePowerNetSinkTE;

public class BasePowerSinkIonGeneratorTE extends BasePowerNetSinkTE implements IIonGenerator{
	/** Current amount of ions in this machine's buffer. */
	protected int ionAmount = 0;
	/** Maximum amount of ions this machine's buffer can store */
	protected int ionBufferSize = 0;
	/** How many excess ions this machine has. Non-zero values mean the main Ion Buffer is full. */
	protected int ionOverflow = 0;
	
	protected BasePowerSinkIonGeneratorTE(int powerBufferSize, int ionBufferSize) {
		super(powerBufferSize);
		this.ionBufferSize = ionBufferSize;
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
	public int requestIons(int ions) {
		if(ions > ionAmount){
			int ret = ionAmount;
			ionAmount = 0;
			return ret;
		}else{
			ionAmount -= ions;
			return ions;
		}
	}
	
	@Override
	public void returnIons(int ions) {
		ionAmount += ions;
	}
	
	@Override
	public float getIonFill() {
		return 100.0F;
	}
	
	@Override
	public int getIonOverflow() {
		return ionOverflow;
	}
	
	@Override
	public void drainIonOverflow() {
		ionAmount += ionOverflow;
		ionOverflow = 0;
		if(ionAmount > ionBufferSize){
			ionOverflow = ionAmount - ionBufferSize;
			ionAmount = ionBufferSize;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		ionAmount = nbt.getInteger("ionAmount");
		ionBufferSize = nbt.getInteger("ionBufferSize");
		ionOverflow = nbt.getInteger("ionOverflow");
	}
	
    @Override
    public void writeToNBT(NBTTagCompound nbt){
    	super.writeToNBT(nbt);
    	nbt.setInteger("ionAmount", ionAmount);
    	nbt.setInteger("ionBufferSize", ionBufferSize);
    	nbt.setInteger("ionOverflow", ionOverflow);
    }
}
