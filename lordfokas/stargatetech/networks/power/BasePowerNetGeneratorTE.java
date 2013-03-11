package lordfokas.stargatetech.networks.power;

import net.minecraft.nbt.NBTTagCompound;
import lordfokas.stargatetech.networks.power.PowerNetTE.IPowerGenerator;

public class BasePowerNetGeneratorTE extends BasePowerNetSourceTE implements IPowerGenerator {
	/** How much excess power this machine has. Non-zero values mean this machine's main power buffer is full. */
	protected int powerOverflow = 0;
	
	protected BasePowerNetGeneratorTE(int buffersize) {
		super(buffersize);
	}

	@Override
	public int getPowerOverflow() {
		return powerOverflow;
	}

	@Override
	public void drainPowerOverflow() {
		powerAmount += powerOverflow;
		powerOverflow = 0;
		if(powerAmount > powerBufferSize){
			powerOverflow = powerAmount - powerBufferSize;
			powerAmount = powerBufferSize;
		}
	}
	
	@Override
	public float getPowerFill(){
		return 100.0F;
	}
	
	@Override
	public void returnPower(int power){
		powerOverflow += power;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		powerOverflow = nbt.getInteger("powerOverflow");
	}
	
    @Override
    public void writeToNBT(NBTTagCompound nbt){
    	super.writeToNBT(nbt);
    	nbt.setInteger("powerOverflow", powerOverflow);
    }
}