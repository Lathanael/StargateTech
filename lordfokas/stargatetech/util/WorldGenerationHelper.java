package lordfokas.stargatetech.util;

import net.minecraft.world.World;

public final class WorldGenerationHelper {
	private WorldGenerationHelper(){}
	
	public static void worldgenHorizontalRect(World w, int x, int y, int z, int depth, int width, int block, int meta){
		for(int xi = 0; xi < depth; xi++){
			for(int zi = 0; zi < width; zi++){
				w.setBlockAndMetadata(x+xi, y, z+zi, block, meta);
			}
		}
	}
	
	public static void worldgenHorizontalRectFrame(World w, int x, int y, int z, int depth, int width, int block, int meta){
		for(int xi = 0; xi < depth; xi++){
			for(int zi = 0; zi < width; zi++){
				if(xi == 0 || xi == depth-1 || zi == 0 || zi == width -1){
					w.setBlockAndMetadata(x+xi, y, z+zi, block, meta);
				}
			}
		}
	}
	
	public static void worldgenHorizontalRectCorner(World w, int x, int y, int z, int depth, int width, int fid, int fmeta, int cid, int cmeta){
		for(int xi = 0; xi < depth; xi++){
			for(int zi = 0; zi < width; zi++){
				if((xi == 0 || xi == depth-1) && (zi == 0 || zi == width -1)){
					w.setBlockAndMetadata(x+xi, y, z+zi, cid, cmeta);
				}else{
					w.setBlockAndMetadata(x+xi, y, z+zi, fid, fmeta);
				}
			}
		}
	}
	
	public static void worldgenVerticalRectZ(World w, int x, int y, int z, int xSpan, int ySpan, int block, int meta){
		for(int xi = 0; xi < xSpan; xi++){
			for(int yi = 0; yi < ySpan; yi++){
				w.setBlockAndMetadata(x+xi, y+yi, z, block, meta);
			}
		}
	}
	
	public static void worldgenVerticalRectX(World w, int x, int y, int z, int ySpan, int zSpan, int block, int meta){
		for(int zi = 0; zi < zSpan; zi++){
			for(int yi = 0; yi < ySpan; yi++){
				w.setBlockAndMetadata(x, y+yi, z+zi, block, meta);
			}
		}
	}
}