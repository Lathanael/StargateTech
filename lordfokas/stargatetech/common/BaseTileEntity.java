package lordfokas.stargatetech.common;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import lordfokas.stargatetech.util.NBTAutomation;
import lordfokas.stargatetech.util.NBTAutomation.NBTFieldCompound;

@NBTFieldCompound
public abstract class BaseTileEntity extends TileEntity{
	public abstract String getID();
	
	public void readFromNBT(NBTTagCompound nbt)
    {
        this.xCoord = nbt.getInteger("x");
        this.yCoord = nbt.getInteger("y");
        this.zCoord = nbt.getInteger("z");
        NBTAutomation.load(this, nbt);
    }
	
    public void writeToNBT(NBTTagCompound nbt)
    {
        nbt.setString("id", getID());
        nbt.setInteger("x", this.xCoord);
        nbt.setInteger("y", this.yCoord);
        nbt.setInteger("z", this.zCoord);
        NBTAutomation.save(this, nbt);
    }
}
