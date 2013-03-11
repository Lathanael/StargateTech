package lordfokas.stargatetech.networks.power;

import net.minecraft.nbt.NBTTagCompound;
import lordfokas.stargatetech.networks.ion.StaticIonNetRouter;
import lordfokas.stargatetech.networks.power.PowerNetTE.IPowerSink;

public class BasePowerNetSinkTE extends BasePowerNetStorageTE implements IPowerSink {
	/** Power request timer. Used along with the power buffer to prevent PowerNet-related lag. */
	protected int powerSearchTicks = 0;
	
	protected BasePowerNetSinkTE(int buffersize) {
		super(buffersize);
	}
	
	@Override
	public void refillPowerBuffer() {
		if(powerAmount < powerBufferSize / 10){
			if(powerSearchTicks == 0){
				powerAmount += StaticIonNetRouter.route(powerBufferSize - powerAmount, position, true);
			}
			if(powerAmount > powerBufferSize / 30){
				powerSearchTicks = 0;
			}else{
				powerSearchTicks = (powerSearchTicks + 1) % PowerNetTE.SEARCH_INTERVAL;
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		powerSearchTicks = nbt.getInteger("powerSearchTicks");
	}
	
    @Override
    public void writeToNBT(NBTTagCompound nbt){
    	super.writeToNBT(nbt);
    	nbt.setInteger("powerSearchTicks", powerSearchTicks);
    }
}