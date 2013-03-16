package lordfokas.stargatetech.machine;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import lordfokas.stargatetech.StargateTech;
import lordfokas.stargatetech.networks.ion.BaseIonNetSinkTE;
import lordfokas.stargatetech.util.CoordinateSet;
import lordfokas.stargatetech.util.Helper;

public class ShieldEmitterTE extends BaseIonNetSinkTE{
	public static final String ID = "ShieldEmitterTE";
	
	// Cumulative costs for the several shield modes:
	public static final int COST_PLAYER		= 13;
	public static final int COST_MOBS		= 2;
	public static final int COST_FRIENDLY	= 5;
	
	/** How far away can this emitter's pair be */
	private int range = StargateTech.shieldEmitter.getMaxShieldRange();
	/** The instance of this emitter's pair */
	private ShieldEmitterTE pair = null;
	/** Is this emitter and it's pair enabled? */
	private boolean disabled = true;
	/** Wether this emitter has enough ions to be enabled again or not */
	private boolean canEnable = false;
	/** Metadata value for Shields. Tells them what to block. Default is all entities except players */
	private int shieldMode = ~Shield.BLOCK_PLAYER;
	/** Ion cost per tick. Based on shieldMode */
	private int cost = COST_MOBS + COST_FRIENDLY;
	
	public ShieldEmitterTE() {
		super(50000);
	}
	
	@Override
	public void invalidate(){
		super.invalidate();
		shutdown();
		if(this.pair != null) this.pair.setPair(null);
	}
	
	public void updateEntity(){
		// Use ions. If the buffer is empty, stop this emitter and it's pair.
		if(ionAmount >= cost && !disabled){
			ionAmount -= cost;
			if(ionAmount < cost){
				shutdown();
			}
		}
		refillIonBuffer();
		if(disabled && ionAmount >= cost){
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
		ionSearchTicks = 0;
		disabled = !enabled;
	}
	
	private void shutdown(){
		canEnable = false;
		findPair();
		destroyShields(position);
		setEnabled(false);
		if(pair != null)
			pair.setEnabled(false);
	}
	
	private void restart(){
		canEnable = true;
		findPair();
		if(pair != null){
			pair.setModeAndRecalculate(shieldMode);
			if(!pair.canEnable) return;
			setEnabled(true);
			pair.setEnabled(true);
			createShields(position.w, position.x, position.y, position.z);
		}
	}
	
	private void setModeAndRecalculate(int mode){
		shieldMode = mode;
		cost = 0;
		if((mode & Shield.BLOCK_PLAYER) != 0){
			cost += COST_PLAYER;
		}
		if((mode & Shield.BLOCK_MOBS) != 0){
			cost += COST_MOBS;
		}
		if((mode & Shield.BLOCK_FRIENDLY) != 0){
			cost += COST_FRIENDLY;
		}
	}
	
	public void setMode(int mode){
		setModeAndRecalculate(mode & 15);
		// Half a byte just changed, reboot the whole system. Is this Microsoft code?
		shutdown();
		restart();
	}
	
	public void findPair(){
		int dir = position.w.getBlockMetadata(position.x, position.y, position.z);
		int tMeta;
		switch(dir){
			case Helper.dirXPos: tMeta = Helper.dirXNeg; break;
			case Helper.dirXNeg: tMeta = Helper.dirXPos; break;
			case Helper.dirZPos: tMeta = Helper.dirZNeg; break;
			case Helper.dirZNeg: tMeta = Helper.dirZPos; break;
			default: return;
		}
		CoordinateSet cs = position.clone();
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
	
	private void createShields(CoordinateSet cs){
		createShields(cs.w, cs.x, cs.y, cs.z);
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
					world.setBlockAndMetadataWithNotify(x + (xInc * i), y, z + (zInc * i), StargateTech.shield.blockID, shieldMode, Helper.SETBLOCK_NO_UPDATE);
				}
			}
		}
	}
	
	private void destroyShields(CoordinateSet cs){
		int dir = cs.w.getBlockMetadata(cs.x, cs.y, cs.z);
		for(int i = 1; i <= range; i++){
			cs = cs.fromDirection(dir);
			if(cs.w.getBlockId(cs.x, cs.y, cs.z) == StargateTech.shield.blockID){
				cs.w.setBlockAndMetadataWithNotify(cs.x, cs.y, cs.z, 0, 0, Helper.SETBLOCK_NO_UPDATE);
			}
			if(cs.w.getBlockId(cs.x, cs.y, cs.z) == StargateTech.shieldEmitter.blockID)
				break;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		canEnable = nbt.getBoolean("canEnable");
		cost = nbt.getInteger("cost");
		disabled = nbt.getBoolean("disabled");
		shieldMode = nbt.getInteger("shieldMode");
	}

    @Override
    public void writeToNBT(NBTTagCompound nbt){
    	super.writeToNBT(nbt);
    	nbt.setBoolean("canEnable", canEnable);
    	nbt.setInteger("cost", cost);
    	nbt.setBoolean("disabled", disabled);
    	nbt.setInteger("shieldMode", shieldMode);
    }
}
