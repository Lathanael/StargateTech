package lordfokas.stargatetech.networks.ion;

import net.minecraft.world.IBlockAccess;

/**
 * A component in the IonNet. Should be implemented by Blocks.
 * @author LordFokas
 */
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
