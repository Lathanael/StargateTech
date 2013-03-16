package lordfokas.stargatetech.world;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;

/**
 * A base for all Naquadria-derived explosives.
 * Since Naquadria is a radioactive isotope of Naquadah, this also extends NaquadahExplosive
 * @author LordFokas
 */
public class NaquadriaExplosive extends NaquadahExplosive {
	
	/**
	 * @param id Block ID
	 * @param textureIndex Index in the texture sheet
	 * @param p Explosion Radius
	 */
	public NaquadriaExplosive(int id, String name, float p) {
		super(id, name, p);
	}
	
	@Override // Blow up on your face. Naquadria is meant to be disintegrated [only!].
	public void onBlockDestroyedByPlayer(World w, int x, int y, int z, int meta){
		explode(w, x, y, z);
	}
	
	@Override // There's no need to drop anything.
	public int quantityDropped(Random r){
		return 0;
	}
	
	@Override // Also react violently when hit by arrows. Beware of skeletons when mining!
	public void onEntityCollidedWithBlock(World w, int x, int y, int z, Entity e){
		if(e instanceof EntityArrow){
			explode(w, x, y, z);
		}
	}

}
