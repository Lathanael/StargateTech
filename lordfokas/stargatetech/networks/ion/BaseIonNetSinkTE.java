package lordfokas.stargatetech.networks.ion;

import net.minecraft.nbt.NBTTagCompound;
import lordfokas.stargatetech.api.networks.IonNetTE;
import lordfokas.stargatetech.api.networks.IonNetTE.IIonSink;
import lordfokas.stargatetech.util.CoordinateSet;

public class BaseIonNetSinkTE extends BaseIonNetStorageTE implements IIonSink{
	/** Ion request timer. Used along with the buffer to prevent IonNet-related lag. */
	protected int ionSearchTicks = 0;
	
	protected BaseIonNetSinkTE(int buffersize){
		super(buffersize);
	}

	@Override
	public void refillIonBuffer() {
		if(ionAmount < ionBufferSize / 10){
			if(ionSearchTicks == 0){
				ionAmount += StaticIonNetRouter.route(ionBufferSize - ionAmount, position, true);
			}
			if(ionAmount > ionBufferSize / 30){
				ionSearchTicks = 0;
			}else{
				ionSearchTicks = (ionSearchTicks + 1) % IonNetTE.SEARCH_INTERVAL;
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		ionSearchTicks = nbt.getInteger("ionSearchTicks");
	}
	
    @Override
    public void writeToNBT(NBTTagCompound nbt){
    	super.writeToNBT(nbt);
    	nbt.setInteger("ionSearchTicks", ionSearchTicks);
    }
}