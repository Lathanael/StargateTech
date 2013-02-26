package lordfokas.stargatetech.world;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import lordfokas.stargatetech.common.BaseBlock;
import lordfokas.stargatetech.util.Helper;

public class NaquadahExplosive extends BaseBlock {
	protected float power; 
	
	public NaquadahExplosive(int id, int textureIndex, float p) {
		super(id, textureIndex, false);
		this.setResistance(-1.0F);
		this.setHardness(3.0F);
		this.power = p;
	}
	
	@Override
	public void onBlockDestroyedByExplosion(World w, int x, int y, int z){
		explode(w, x, y, z);
	}
	
	protected void explode(World w, int x, int y, int z){
		w.createExplosion((Entity)null, x, y, z, power, true);
	}
	
	@Override
	public boolean isGenMineableReplaceable(World w, int x, int y, int z){
		return false;
	}
}
