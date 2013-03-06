package lordfokas.stargatetech.world;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import lordfokas.stargatetech.StargateTech;
import lordfokas.stargatetech.common.IDisintegrable;
import lordfokas.stargatetech.rendering.RenderOre;
import lordfokas.stargatetech.util.TextureIndex;

public class NaquadahOre extends NaquadahExplosive implements IDisintegrable{
	public NaquadahOre(int id) {
		super(id, TextureIndex.oreNaquadah, 2.5F);
		this.setBlockName("naquadahOre");
	}
	
	@Override
	public int idDropped(int i, Random r, int j){
		return StargateTech.naquadahShard.itemID;
	}
	
	@Override // Drop 1 or 2 shards when broken
	public int quantityDropped(Random r){
		return 1 + r.nextInt(2);
	}
	
	@Override // Drop 3 shards when disintegrated
	public boolean disintegrate(World w, int x, int y, int z){
		if(w.isRemote) return true;
		w.setBlock(x, y, z, 0);
		w.spawnEntityInWorld(new EntityItem(w, (double)x, (double)y, (double)z, new ItemStack(StargateTech.naquadahShard, 3)));
		return true;
	}
	
	@Override
    public int getRenderType(){
    	return RenderOre.instance().getRenderId();
    }
}
