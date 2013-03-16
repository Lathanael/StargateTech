package lordfokas.stargatetech.world;

import net.minecraft.entity.Entity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import lordfokas.stargatetech.common.BaseBlock;

/**
 * A naquadah-based explosive.
 * All blocks with naquadah-derived properties should extend this.
 * @author LordFokas
 */
public class NaquadahExplosive extends BaseBlock {
	/** Explosion Radius */
	protected float power;
	
	/**
	 * @param id Block ID
	 * @param textureIndex Texture Sheet Index
	 * @param p Explosion Radius
	 */
	public NaquadahExplosive(int id, String name, float p) {
		super(id, name, false);
		this.setResistance(-1.0F);
		this.setHardness(3.0F);
		this.power = p;
	}
	
	@Override
	public void onBlockDestroyedByExplosion(World w, int x, int y, int z, Explosion explosion){
		explode(w, x, y, z);
	}
	
	protected void explode(World w, int x, int y, int z){
		w.createExplosion((Entity)null, x, y, z, power, true);
	}
	
	@Override
	public boolean isGenMineableReplaceable(World w, int x, int y, int z, int target){
		return false;
	}
}
