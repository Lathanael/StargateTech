package lordfokas.stargatetech.world;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import lordfokas.stargatetech.StargateTech;
import lordfokas.stargatetech.common.IDisintegrable;
import lordfokas.stargatetech.rendering.RenderOre;
import lordfokas.stargatetech.util.TextureIndex;

public class NaquadriaOre extends NaquadriaExplosive implements IDisintegrable{

	public NaquadriaOre(int id) {
		super(id, TextureIndex.oreNaquadria, 5.0F);
		this.setBlockName("naquadriaOre");
		this.setTickRandomly(true);
	}
	
	@Override // Transform adjacent Naquadah Ore into Naquadria Ore over time.
	public void updateTick(World w, int x, int y, int z, Random r){
		int naquadah = StargateTech.naquadahOre.blockID;
		if(r.nextBoolean() && w.getBlockId(x+1, y, z) == naquadah) w.setBlock(x+1, y, z, this.blockID);
		if(r.nextBoolean() && w.getBlockId(x-1, y, z) == naquadah) w.setBlock(x-1, y, z, this.blockID);
		if(r.nextBoolean() && w.getBlockId(x, y+1, z) == naquadah) w.setBlock(x, y+1, z, this.blockID);
		if(r.nextBoolean() && w.getBlockId(x, y-1, z) == naquadah) w.setBlock(x, y-1, z, this.blockID);
		if(r.nextBoolean() && w.getBlockId(x, y, z+1) == naquadah) w.setBlock(x, y, z+1, this.blockID);
		if(r.nextBoolean() && w.getBlockId(x, y, z-1) == naquadah) w.setBlock(x, y, z-1, this.blockID);
	}
	
	@Override // Tick fast
	public int tickRate(){
		return 1;
	}

	@Override
	public boolean disintegrate(World w, int x, int y, int z){
		if(w.isRemote) return true;
		w.setBlock(x, y, z, 0);
		w.spawnEntityInWorld(new EntityItem(w, (double)x, (double)y, (double)z, new ItemStack(StargateTech.naquadriaShard, 2)));
		return true;
	}
	
	@Override
    public int getRenderType(){
    	return RenderOre.instance().getRenderId();
    }
}
