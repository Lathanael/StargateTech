package lordfokas.stargatetech.world;

import java.util.ArrayList;
import java.util.Random;

import lordfokas.stargatetech.StargateTech;
import lordfokas.stargatetech.machine.Shield;
import lordfokas.stargatetech.util.Helper;
import lordfokas.stargatetech.util.WorldGenerationHelper;
import lordfokas.stargatetech.util.WorldGenerationHelper.WorldGenBlock;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public final class WorldGeneratorOutpost {
	private final static int OUTPOST_RARITY_MASTER	= 2;
	private final static int OUTPOST_RARITY_DOME	= 2;
	
	private static ArrayList<WorldGenBlock> base = WorldGenerationHelper.instance.getFullHorizontalCircle(11);
	private static ArrayList<WorldGenBlock> ring0 = WorldGenerationHelper.instance.getHorizontalCircle(12);
	private static ArrayList<WorldGenBlock> ring1 = WorldGenerationHelper.instance.getHorizontalCircle(11);
	private static ArrayList<WorldGenBlock> ring2 = WorldGenerationHelper.instance.getHorizontalCircle(10);
	private static ArrayList<WorldGenBlock> ring3 = WorldGenerationHelper.instance.getHorizontalCircle(9);
	private static ArrayList<WorldGenBlock> ring4 = WorldGenerationHelper.instance.getHorizontalCircle(8);
	private static ArrayList<WorldGenBlock> ring5 = WorldGenerationHelper.instance.getFullHorizontalCircle(7);
	private static ArrayList<WorldGenBlock> ring0c = WorldGenerationHelper.instance.getFullHorizontalCircle(12);
	private static ArrayList<WorldGenBlock> ring1c = WorldGenerationHelper.instance.getFullHorizontalCircle(11);
	private static ArrayList<WorldGenBlock> ring2c = WorldGenerationHelper.instance.getFullHorizontalCircle(10);
	private static ArrayList<WorldGenBlock> ring3c = WorldGenerationHelper.instance.getFullHorizontalCircle(9);
	private static ArrayList<WorldGenBlock> ring4c = WorldGenerationHelper.instance.getFullHorizontalCircle(8);
	
	private WorldGeneratorOutpost(){}
	
	public static void generate(Random r, World w, int x, int y, int z){
		try{
			if(r.nextInt(OUTPOST_RARITY_MASTER) == 0){
				int block = w.getBlockId(x, y, z);
				if(block == Block.waterStill.blockID && w.getBiomeGenForCoords(x, z).biomeName == "Ocean"){
					generateDome(r, w, x, y, z);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void generateDome(Random r, World w, int x, int y, int z){
		while(w.getBlockId(x, y, z) == Block.waterStill.blockID){
			y--;
		}
		y++;
		if(y > 50) return;
		if(r.nextInt(OUTPOST_RARITY_DOME) == 0){
			WorldGenerationHelper wgen = WorldGenerationHelper.instance;
			int shield = StargateTech.shield.blockID;
			int meta = ~Shield.BLOCK_PLAYER;
			
			wgen.generate(base, w, x, y, z, StargateTech.lanteanBlock.blockID, 0);
			wgen.generate(ring0, w, x, y, z, StargateTech.lanteanBlock.blockID, 1);
			
			// ** Get rid of water *****************\\
			wgen.generate(ring0c, w, x, y+1, z, 0, 0);
			wgen.generate(ring0c, w, x, y+2, z, 0, 0);
			wgen.generate(ring0c, w, x, y+3, z, 0, 0);
			wgen.generate(ring1c, w, x, y+4, z, 0, 0);
			wgen.generate(ring1c, w, x, y+5, z, 0, 0);
			wgen.generate(ring2c, w, x, y+6, z, 0, 0);
			wgen.generate(ring3c, w, x, y+7, z, 0, 0);
			wgen.generate(ring4c, w, x, y+8, z, 0, 0);
			
			// ** Place Shields ***************************\\
			wgen.generate(ring0, w, x, y+1, z, shield, meta);
			wgen.generate(ring0, w, x, y+2, z, shield, meta);
			wgen.generate(ring0, w, x, y+3, z, shield, meta);
			wgen.generate(ring1, w, x, y+4, z, shield, meta);
			wgen.generate(ring1, w, x, y+5, z, shield, meta);
			wgen.generate(ring2, w, x, y+6, z, shield, meta);
			wgen.generate(ring3, w, x, y+7, z, shield, meta);
			wgen.generate(ring4, w, x, y+8, z, shield, meta);
			wgen.generate(ring5, w, x, y+9, z, shield, meta);
			
			// Open space for the Stargate
			w.setBlockAndMetadataWithNotify(x-2, y, z-7, 0, 0, Helper.SETBLOCK_NO_UPDATE);
			w.setBlockAndMetadataWithNotify(x-1, y, z-7, 0, 0, Helper.SETBLOCK_NO_UPDATE);
			w.setBlockAndMetadataWithNotify( x , y, z-7, 0, 0, Helper.SETBLOCK_NO_UPDATE);
			w.setBlockAndMetadataWithNotify(x+1, y, z-7, 0, 0, Helper.SETBLOCK_NO_UPDATE);
			w.setBlockAndMetadataWithNotify(x+2, y, z-7, 0, 0, Helper.SETBLOCK_NO_UPDATE);
			w.setBlockAndMetadataWithNotify(x, y, z-7, StargateTech.stargate.blockID, 2, Helper.SETBLOCK_NO_UPDATE);
			StargateTech.stargate.placeStargateWithRotation(w, x, y, z-7, 2);
		}
	}
}
