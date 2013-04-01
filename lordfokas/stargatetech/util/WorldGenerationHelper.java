package lordfokas.stargatetech.util;

import java.util.ArrayList;

import net.minecraft.world.World;

public final class WorldGenerationHelper {
	public static final WorldGenerationHelper instance = new WorldGenerationHelper();
	private WorldGenerationHelper(){}
	
	public class WorldGenBlock{
		public int x, y, z;
		
		public WorldGenBlock(int x, int y, int z){
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		@Override
		public boolean equals(Object o){
			if(o instanceof WorldGenBlock){
				WorldGenBlock wgb = (WorldGenBlock) o;
				return x == wgb.x && y == wgb.y && z == wgb.z;
			}
			return false;
		}
	}
	
	public void generate(ArrayList<WorldGenBlock> blocks, World w, int x, int y, int z, int id, int meta){
		for(WorldGenBlock block : blocks){
			w.setBlock(block.x + x, block.y + y, block.z + z, id, meta, Helper.SETBLOCK_UPDATE);
		}
	}
	
	/** Returns a full circle as a list of block coordinates. */
	public ArrayList<WorldGenBlock> getFullHorizontalCircle(int radius){
		ArrayList<WorldGenBlock> blocks = new ArrayList<WorldGenBlock>();
		double rad = (double) radius;
		int[][] record = new int[radius + 1][2];
		for(double theta = 0; theta < 90; theta += 0.25D){
			int genX = (int) (rad * Math.cos(theta));
			int genZ = (int) (rad * Math.sin(theta));
			WorldGenBlock pp = new WorldGenBlock( genX, 0,  genZ);
			WorldGenBlock np = new WorldGenBlock(-genX, 0,  genZ);
			WorldGenBlock pn = new WorldGenBlock( genX, 0, -genZ);
			WorldGenBlock nn = new WorldGenBlock(-genX, 0, -genZ);
			if(!blocks.contains(pp)) blocks.add(pp);
			if(!blocks.contains(pn)) blocks.add(pn);
			if(!blocks.contains(np)) blocks.add(np);
			if(!blocks.contains(nn)) blocks.add(nn);
			
			int z = genZ;
			if(z < 1) z*=-1;
			if(record[z][0] > -genX) record[z][0] = -genX;
			if(record[z][1] <  genX) record[z][1] =  genX;
		}
		
		for(int z = 0; z <= radius; z++){
			int minX = record[z][0];
			int maxX = record[z][1];
			for(int i = minX; i <= maxX; i++){
				WorldGenBlock fp = new WorldGenBlock(i, 0,  z);
				WorldGenBlock fn = new WorldGenBlock(i, 0, -z);
				if(!blocks.contains(fp)) blocks.add(fp);
				if(!blocks.contains(fn)) blocks.add(fn);
			}
		}
		
		return blocks;
	}
	
	/** Returns the outline of a circle as a list of block coordinates. */
	public ArrayList<WorldGenBlock> getHorizontalCircle(int radius){
		ArrayList<WorldGenBlock> blocks = new ArrayList<WorldGenBlock>();
		double rad = (double) radius;
		for(double theta = 0; theta < 90; theta += 0.25D){
			int genX = (int) (rad * Math.cos(theta));
			int genZ = (int) (rad * Math.sin(theta));
			WorldGenBlock pp = new WorldGenBlock( genX, 0,  genZ);
			WorldGenBlock np = new WorldGenBlock(-genX, 0,  genZ);
			WorldGenBlock pn = new WorldGenBlock( genX, 0, -genZ);
			WorldGenBlock nn = new WorldGenBlock(-genX, 0, -genZ);
			if(!blocks.contains(pp)) blocks.add(pp);
			if(!blocks.contains(pn)) blocks.add(pn);
			if(!blocks.contains(np)) blocks.add(np);
			if(!blocks.contains(nn)) blocks.add(nn);
		}
		return blocks;
	}
	
	/** Generate a rectangle of blocks in the horizontal (XZ) plane */
	public void generateHRect(World w, int x, int y, int z, int depth, int width, int block, int meta){
		for(int xi = 0; xi < depth; xi++){
			for(int zi = 0; zi < width; zi++){
				w.setBlock(x+xi, y, z+zi, block, meta, Helper.SETBLOCK_UPDATE);
			}
		}
	}
	
	/** Generate the outline of a rectangle in the horizontal (XZ) plane */
	public void generateHRectFrame(World w, int x, int y, int z, int depth, int width, int block, int meta){
		for(int xi = 0; xi < depth; xi++){
			for(int zi = 0; zi < width; zi++){
				if(xi == 0 || xi == depth-1 || zi == 0 || zi == width -1){
					w.setBlock(x+xi, y, z+zi, block, meta, Helper.SETBLOCK_UPDATE);
				}
			}
		}
	}
	
	/** Generate a rectangle with different blocks on the corners in the horizontal (XZ) plane */
	public void generateHRectCorner(World w, int x, int y, int z, int depth, int width, int fid, int fmeta, int cid, int cmeta){
		for(int xi = 0; xi < depth; xi++){
			for(int zi = 0; zi < width; zi++){
				if((xi == 0 || xi == depth-1) && (zi == 0 || zi == width -1)){
					w.setBlock(x+xi, y, z+zi, cid, cmeta, Helper.SETBLOCK_UPDATE);
				}else{
					w.setBlock(x+xi, y, z+zi, fid, fmeta, Helper.SETBLOCK_UPDATE);
				}
			}
		}
	}
	
	/** Generate a rectangle of blocks on the XY plane */
	public void generateVRectZ(World w, int x, int y, int z, int xSpan, int ySpan, int block, int meta){
		for(int xi = 0; xi < xSpan; xi++){
			for(int yi = 0; yi < ySpan; yi++){
				w.setBlock(x+xi, y+yi, z, block, meta, Helper.SETBLOCK_UPDATE);
			}
		}
	}
	
	/** Generate a rectangle of blocks on the YZ plane */
	public void generateVRectX(World w, int x, int y, int z, int ySpan, int zSpan, int block, int meta){
		for(int zi = 0; zi < zSpan; zi++){
			for(int yi = 0; yi < ySpan; yi++){
				w.setBlock(x, y+yi, z+zi, block, meta, Helper.SETBLOCK_UPDATE);
			}
		}
	}
}