package lordfokas.stargatetech.networks.ion;

import lordfokas.stargatetech.networks.ion.IonNetTE.IIonSource;
import net.minecraft.nbt.NBTTagCompound;

public class BaseIonNetSourceTE extends BaseIonNetStorageTE implements IIonSource {
	protected BaseIonNetSourceTE(int buffersize){
		super(buffersize);
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
		return ((float)ionAmount) / ((float)ionBufferSize);
	}
}
