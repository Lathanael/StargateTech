package lordfokas.stargatetech.machine;

import java.util.List;

import lordfokas.stargatetech.common.BaseTileEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.DimensionManager;
import lordfokas.stargatetech.networks.stargate.Address;
import lordfokas.stargatetech.networks.stargate.AddressHandler;
import lordfokas.stargatetech.networks.stargate.StargateNetwork;
import lordfokas.stargatetech.networks.stargate.StargateTeleporter;
import lordfokas.stargatetech.util.Helper;

public class StargateTE extends BaseTileEntity {
	public static final String ID = "StargateTE";
	/** Does this gate have an active Wormhole? */
	private boolean hasWormhole = false;
	/** Can this gate teleport entities through the wormhole? */
	private boolean canTeleport = false;
	/** destination dimension */
	private int destD = 0;
	/** destination X coordinate */
	private int destX = 0;
	/** destination Y coordinate */
	private int destY = 0;
	/** destination Z coordinate */
	private int destZ = 0;
	/** How many ticks until the wormhole disconnects */
	private int wormholeTimer = -1;
	/** Iris position. Not yet in use. */
	private short irisState = 0;
	/** This gate's address */
	public Address myAddress;
	
	public StargateTE(){}
	
	@Override
	public void validate(){
		super.validate();
		if(!worldObj.isRemote){
			myAddress = StargateNetwork.instance(worldObj).getMyAddress(worldObj, xCoord, yCoord, zCoord);
			if(myAddress != null) System.out.println("NEW ADDR: " + myAddress.getName());
		}
	}
	
	@Override
	public void updateEntity(){
		if(hasWormhole && canTeleport && (!worldObj.isRemote)){
			AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(xCoord - 2, yCoord, zCoord, xCoord + 3, yCoord + 5, zCoord + 1);
			List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class, aabb);
			for(Entity entity : entities){
				StargateTeleporter.teleportEntity(entity, destD, destX, destY+1, destZ);
			}
		}
		if(wormholeTimer != -1){
			if(wormholeTimer == 0){
				hasWormhole = false;
				canTeleport = false;
				destD = destX = destY = destZ = 0;
				updateClients();
			}
			wormholeTimer--;
		}
	}
	
	public void dial(Address target) {
		StargateNetwork.dial(myAddress, target);
	}
	
	/**
	 * Open a connection to another gate.
	 * @param time How many ticks the connection should remain open.
	 * @param transport Will this gate teleport entities through the wormhole?
	 * @param destination Where this gate will teleport entities to.
	 */
	public void openWormhole(int time, boolean transport, AddressHandler destination){
		hasWormhole = true;
		wormholeTimer = time;
		canTeleport = transport;
		if(transport){
			destD = destination.getD();
			destX = destination.getX();
			destY = destination.getY();
			destZ = destination.getZ();
		}
		updateClients();
	}
	
	public int getDirection(){
		return worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
	}
	
	public void setDirection(int d){
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, d, Helper.SETBLOCK_UPDATE);
	}
	
	public boolean hasWormhole(){
		return hasWormhole;
	}
	
	public short getIrisState(){
		return irisState;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox(){
		return AxisAlignedBB.getAABBPool().getAABB(xCoord - 3, yCoord, zCoord - 3, xCoord + 4, yCoord + 7, zCoord + 4);
	}
	
	@Override
	public double getMaxRenderDistanceSquared() {
		return 65536D;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		canTeleport = nbt.getBoolean("canTeleport");
		hasWormhole = nbt.getBoolean("hasWormhole");
		wormholeTimer = nbt.getInteger("wormholeTimer");
		destD = nbt.getInteger("destD");
		destX = nbt.getInteger("destX");
		destY = nbt.getInteger("destY");
		destZ = nbt.getInteger("destZ");
	}

    @Override
    public void writeToNBT(NBTTagCompound nbt){
    	super.writeToNBT(nbt);
    	nbt.setBoolean("canTeleport", canTeleport);
    	nbt.setBoolean("hasWormhole", hasWormhole);
    	nbt.setInteger("wormholeTimer", wormholeTimer);
    	nbt.setInteger("destD", destD);
    	nbt.setInteger("destX", destX);
    	nbt.setInteger("destY", destY);
    	nbt.setInteger("destZ", destZ);
    }
}
