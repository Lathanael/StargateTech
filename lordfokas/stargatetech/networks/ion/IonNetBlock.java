package lordfokas.stargatetech.networks.ion;

import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * IonNet Block Specification.
 * Blocks that belong to the IonNet should implement at least IIonNetComponent,
 * no matter what TileEntities those blocks might have.
 * 
 * @author LordFokas
 */
public final class IonNetBlock {
	private IonNetBlock(){}
	
	/** An IonNet component. Every IonNet block MUST implement this. */
	public interface IIonNetComponent {
		/** Available types of components for IonNetComponent. */
		public enum EIonComponentType {
			/** A tube. Transports ions between components. */
			TUBE,
			
			/** A sink. Uses ions from sources. */
			SINK,
			
			/** A source. Outputs ions into the network. May be an ion generator. */
			SOURCE,
			
			/** A relay. It's both a sink and a source. Shouldn't be an ion generator. */
			RELAY
		}
		
		/** What type of component this IonNetComponent is. */
		public EIonComponentType getIonComponentType();
		/** Whether an ion tube can connect on a specific side or not. */
		public boolean canTubeConnectOnSide(IBlockAccess w, int x, int y, int z, int side);
	}
	
	/** An IonNet Source. It's a component that can output ions into the network upon request. */
	public interface IIonNetSource extends IIonNetComponent{
		/** Request ions from a block. Return as many ions as possible, up to the amount requested. */
		public int requestIons(World w, int x, int y, int z, int ions);
		/** Put ions back after a request. It's related to StaticIonNetRouter's design. */
		public void giveBack(World w, int x, int y, int z, int ions);
		/** How full this component is (0.0F - 1.0F). StaticIonNetRouter will always get ions from the fullest component available first. */
		public float getFill(World w, int x, int y, int z);
	}
}