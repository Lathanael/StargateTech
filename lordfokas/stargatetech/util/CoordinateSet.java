package lordfokas.stargatetech.util;

import net.minecraft.world.World;

/**
 * A custom coordinate handler. Easier way to navigate in 3D.
 * Has some methods to ease navigation in the world.
 * @author LordFokas
 */
public class CoordinateSet implements Cloneable{
	public World w;
	public int x;
	public int y;
	public int z;
	
	// Create a coordinate set with no World.
	public CoordinateSet(int x, int y, int z)
		{ this(null, x, y, z); }
	
	// Create a coordinate set with a World.
	public CoordinateSet(World w, int x, int y, int z){
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	// Return a new CoordinateSet, adjacent to this one.
	// Used by routing systems to navigate in 3D for targets.
	public CoordinateSet fromDirection(int dir){
		int[] d = Helper.adjacentBlockCoords[dir];
		return new CoordinateSet(w, x+d[0], y+d[1], z+d[2]);
	}
	
	@Override public CoordinateSet clone()
		{ return new CoordinateSet(w, x, y, z); }
	
	@Override public boolean equals(Object o){
		if(!(o instanceof CoordinateSet)) return false;
		CoordinateSet c = (CoordinateSet) o;
		if(c.w.provider.dimensionId != w.provider.dimensionId) return false;
		if(c.x != x || c.y != y || c.z != z) return false;
		return true;
	}
	
	@Override // Used in debug. It has proven itself useful already.
	public String toString(){
		return "CoordinateSet{" + x + ", " + y + ", " + z +"}";
	}
}
