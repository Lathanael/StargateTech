package lordfokas.stargatetech.networks.power;

import net.minecraft.nbt.NBTTagCompound;
import lordfokas.stargatetech.networks.power.PowerNetTE.IPowerSource;

public class BasePowerNetSourceTE extends BasePowerNetStorageTE implements IPowerSource {
	protected BasePowerNetSourceTE(int buffersize) {
		super(buffersize);
	}

	@Override
	public int requestPower(int power) {
		if(powerAmount > power){
			powerAmount -= power;
			return power;
		}else{
			int ret = powerAmount;
			powerAmount = 0;
			return ret;
		}
	}

	@Override
	public void returnPower(int power) {
		powerAmount += power;
	}

	@Override
	public float getPowerFill() {
		return ((float)powerAmount) / ((float)powerBufferSize);
	}
}