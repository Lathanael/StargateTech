package lordfokas.stargatetech.networks.ion;

import lordfokas.stargatetech.networks.ion.IonNetTE.IIonGenerator;
import net.minecraft.nbt.NBTTagCompound;

public class BaseIonNetGeneratorTE extends BaseIonNetSourceTE implements IIonGenerator{
	/** How many excess ions this machine has. Non-zero values mean the main Ion Buffer is full. */
	protected int ionOverflow = 0;
	
	protected BaseIonNetGeneratorTE(int buffersize) {
		super(buffersize);
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
	public float getIonFill(){
		return 100.0F;
	}
	
	@Override
	public void returnIons(int ions) {
		ionOverflow += ions;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		ionOverflow = nbt.getInteger("ionOverflow");
	}
	
    @Override
    public void writeToNBT(NBTTagCompound nbt){
    	super.writeToNBT(nbt);
    	nbt.setInteger("ionOverflow", ionOverflow);
    }
}