package lordfokas.stargatetech.world;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;

public class NaquadriaExplosive extends NaquadahExplosive {

	public NaquadriaExplosive(int id, int textureIndex, float p) {
		super(id, textureIndex, p);
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World w, int x, int y, int z, int meta){
		explode(w, x, y, z);
	}
	
	@Override
	public int quantityDropped(Random r){
		return 0;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World w, int x, int y, int z, Entity e){
		if(e instanceof EntityArrow){
			explode(w, x, y, z);
		}
	}

}
