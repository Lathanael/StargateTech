package lordfokas.stargatetech.networks.ion;

import net.minecraft.world.World;

public interface IIonNetSource extends IIonNetComponent{
	public int requestIons(World w, int x, int y, int z, int ions);
	public void giveBack(World w, int x, int y, int z, int ions);
	public float getFill(World w, int x, int y, int z);
}
