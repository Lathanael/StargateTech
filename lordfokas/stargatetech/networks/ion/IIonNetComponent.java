package lordfokas.stargatetech.networks.ion;

import net.minecraft.world.IBlockAccess;

public interface IIonNetComponent {
	public enum EIonComponentType {
		TUBE,
		SINK,
		SOURCE,
		RELAY
	}
	
	public EIonComponentType getIonComponentType();
	public boolean canTubeConnectOnSide(IBlockAccess w, int x, int y, int z, int side);
}
