package lordfokas.stargatetech.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import lordfokas.stargatetech.StargateTech;

import cpw.mods.fml.common.IWorldGenerator;

public class LanteanWorldGen implements IWorldGenerator {

	@Override
	public void generate(Random r, int cX, int cZ, World w, IChunkProvider cGen, IChunkProvider cProv) {
		if(w.provider.dimensionId != 0) return;
		generateNaquadah(r, cX, cZ, w);
		generateLanteanBase(r, cX, cZ, w);
	}
	
	private void generateNaquadah(Random r, int cX, int cZ, World w){
		if(r.nextInt(10) != 0) return;
		for(int i = 0; i < 2; i++){
			int x = cX*16 + r.nextInt(16);
			int y = r.nextInt(32) + 8 + (i*32);
			int z = cZ*16 + r.nextInt(16);
			(new WorldGenMinable(StargateTech.naquadahOre.blockID, 50 - (i*20))).generate(w, r, x, y, z);
		}
		generateNaquadria(r, cX, cZ, w);
	}
	
	private void generateNaquadria(Random r, int cX, int cZ, World w){
		for(int i = 0; i < 8; i++){
			int x = cX*16 + r.nextInt(16);
			int y = 10 * (i+1) + r.nextInt(10);
			int z = cZ*16 + r.nextInt(16);
			(new WorldGenMinable(StargateTech.naquadriaOre.blockID, r.nextInt(8) + 1)).generate(w, r, x, y, z);
		}
	}
	
	private void generateLanteanBase(Random r, int cX, int cZ, World w){}
}
