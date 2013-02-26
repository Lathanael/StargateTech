package lordfokas.stargatetech.util;

import net.minecraft.world.World;
import lordfokas.stargatetech.util.NBTAutomation.NBTField;
import lordfokas.stargatetech.util.NBTAutomation.NBTFieldCompound;

public class CoordinateSet implements Cloneable{
	public World w;
	public int x;
	public int y;
	public int z;
	
	public CoordinateSet(int x, int y, int z)
		{ this(null, x, y, z); }
	
	public CoordinateSet(World w, int x, int y, int z){
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
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
	
	@Override
	public String toString(){
		return "CoordinateSet{" + x + ", " + y + ", " + z +"}";
	}
}
