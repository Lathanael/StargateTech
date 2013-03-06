package lordfokas.stargatetech.machine;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import lordfokas.stargatetech.StargateTech;
import lordfokas.stargatetech.networks.ion.IonNetStaticRouter;
import lordfokas.stargatetech.util.CoordinateSet;
import lordfokas.stargatetech.util.Helper;

public class ShieldEmitterTE extends TileEntity{
	public static final String ID = "ShieldEmitterTE";
	public static final int SEARCH_TICKS = 20;
	
	/** How far away can this emitter's pair be */
	private int range = StargateTech.shieldEmitter.getMaxShieldRange();
	/** The instance of this emitter's pair */
	private ShieldEmitterTE pair = null;
	/** How many ions do we have inside.*/
	private int ionAmount = 0;
	/** How many ions can be stored in the internal buffer */
	private int bufferSize = 2000;
	/** This emitter's position */
	private CoordinateSet pos = null;
	/** How many ticks since we last searched for ions */
	private int searchTicks = 0;
	/** Is this emitter and it's pair enabled? */
	private boolean disabled = true;
	/** Wether this emitter has enough ions to be enabled again or not */
	private boolean canEnable = false;
	
	@Override
	public void invalidate(){
		super.invalidate();
		shutdown();
		if(this.pair != null) this.pair.setPair(null);
	}
	
	public void updateEntity(){
		// Use ions. If the buffer is empty, stop this emitter and it's pair.
		if(ionAmount > 0 && !disabled){
			ionAmount--;
			if(ionAmount == 0){
				shutdown();
			}
		}
		// If the buffer is below 10%, request more ions.
		if(ionAmount < (bufferSize/10)){
			if(searchTicks == 0){
				if(pos == null) updatePos();
				ionAmount += IonNetStaticRouter.route(bufferSize - ionAmount, pos, true);
			}
			searchTicks++;
			if(searchTicks == SEARCH_TICKS){
				searchTicks = 0;
			}
		}
		if(disabled && ionAmount > 0){
			restart();
		}
	}
	
	public void setPair(ShieldEmitterTE p){
		pair = p;
	}
	
	public boolean isEnabled(){
		return !disabled;
	}
	
	public void setEnabled(boolean enabled){
		searchTicks = 0;
		disabled = !enabled;
	}
	
	private void shutdown(){
		canEnable = false;
		findPair();
		if(pos == null) updatePos();
		destroyShields(pos);
		setEnabled(false);
		if(pair != null)
			pair.setEnabled(false);
	}
	
	private void restart(){
		canEnable = true;
		findPair();
		if(pair != null){
			if(pos == null) updatePos();
			if(!pair.canEnable) return;
			createShields(pos.w, pos.x, pos.y, pos.z);
			setEnabled(true);
			pair.setEnabled(true);
		}
	}
	
	public void findPair(){
		if(pos == null) updatePos();
		int dir = pos.w.getBlockMetadata(pos.x, pos.y, pos.z);
		int tMeta;
		switch(dir){
			case Helper.dirXPos: tMeta = Helper.dirXNeg; break;
			case Helper.dirXNeg: tMeta = Helper.dirXPos; break;
			case Helper.dirZPos: tMeta = Helper.dirZNeg; break;
			case Helper.dirZNeg: tMeta = Helper.dirZPos; break;
			default: return;
		}
		CoordinateSet cs = pos.clone();
		for(int i = 1; i <= range+1; i++){
			cs = cs.fromDirection(dir);
			int bid = cs.w.getBlockId(cs.x, cs.y, cs.z);
			if(bid != 0 && bid != StargateTech.shield.blockID && bid != StargateTech.shieldEmitter.blockID){
				pair = null;
				return;
			}
			if(bid == StargateTech.shieldEmitter.blockID && cs.w.getBlockMetadata(cs.x, cs.y, cs.z) == tMeta){
				pair = (ShieldEmitterTE) cs.w.getBlockTileEntity(cs.x, cs.y, cs.z);
				pair.setPair(this);
				return;
			}
		}
	}
	
	public void updatePos(){
		pos = new CoordinateSet(worldObj, xCoord, yCoord, zCoord);
	}
	
	private void createShields(World world, int x, int y, int z){
		int dir = world.getBlockMetadata(x, y, z);
		int xInc = 0;
		int zInc = 0;
		int tMeta;
		switch(dir){
			case Helper.dirXPos:
				tMeta = Helper.dirXNeg;
				xInc = 1;
				break;
			case Helper.dirXNeg:
				tMeta = Helper.dirXPos;
				xInc = -1;
				break;
			case Helper.dirZPos:
				tMeta = Helper.dirZNeg;
				zInc = 1;
				break;
			case Helper.dirZNeg:
				tMeta = Helper.dirZPos;
				zInc = -1;
				break;
			default: return;
		}
		if(world.isAirBlock(x + xInc, y, z + zInc)){
			int spawnCycles = 1;
			for(int i = 2; i <= range; i++){
				if(world.isAirBlock(x + (xInc * i), y, z + (zInc * i))){
					spawnCycles++;
				}else if(world.getBlockId(x + (xInc * i), y, z + (zInc * i)) == StargateTech.shieldEmitter.blockID && world.getBlockMetadata(x + (xInc * i), y, z + (zInc * i)) == tMeta){
					break;
				}else{
					spawnCycles = 0;
					break;
				}
			}
			if(spawnCycles != 0 && world.getBlockId(x + (spawnCycles+1) * xInc, y, z + (spawnCycles+1) * zInc) == StargateTech.shieldEmitter.blockID){
				for(int i = 1; i<=spawnCycles; i++){
					world.setBlock(x + (xInc * i), y, z + (zInc * i), Shield.id);
				}
			}
		}
	}
	
	private void destroyShields(CoordinateSet cs){
		int dir = cs.w.getBlockMetadata(cs.x, cs.y, cs.z);
		for(int i = 1; i <= range; i++){
			cs = cs.fromDirection(dir);
			if(cs.w.getBlockId(cs.x, cs.y, cs.z) == Shield.id){
				cs.w.setBlock(cs.x, cs.y, cs.z, 0);
			}
			if(cs.w.getBlockId(cs.x, cs.y, cs.z) == StargateTech.shieldEmitter.blockID)
				break;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		canEnable = nbt.getBoolean("canEnable");
		disabled = nbt.getBoolean("disabled");
		ionAmount = nbt.getInteger("power");
		searchTicks = nbt.getInteger("searchTicks");
	}

    @Override
    public void writeToNBT(NBTTagCompound nbt){
    	super.writeToNBT(nbt);
    	nbt.setBoolean("canEnable", canEnable);
    	nbt.setBoolean("disabled", disabled);
    	nbt.setInteger("power", ionAmount);
    	nbt.setInteger("searchTicks", searchTicks);
    }
}
