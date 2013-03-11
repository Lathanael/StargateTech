package lordfokas.stargatetech.networks.ion;

import lordfokas.stargatetech.networks.INetworkTE;

/**
 * IonNet TileEntity Specification
 * IonNet Tile Entities don't have to implement any of these interfaces, but any IonNet TileEntity
 * from this mod will implement any of these interfaces if they match what the TileEntity does.
 * 
 * @author LordFokas
 */
public final class IonNetTE {
	private IonNetTE(){}
	public static final int SEARCH_INTERVAL = 20;
	
	/** An Ion Storage machine. */
	public interface IIonStorage extends INetworkTE{
		/** A getter for the current amount of ions in this machine. */
		public int getIonAmount();
		/** A getter for the maximum amount of ions this machine can store. */
		public int getIonBufferSize();
	}
	
	/** An Ion Sink. Needs to be refilled by Sources in the network. */
	public interface IIonSink extends IIonStorage{
		/** Refill the ion storage if all the conditions are met. Call every tick. */
		public void refillIonBuffer();
	}
	
	/** An Ion Source. Refills Sinks in the network. */
	public interface IIonSource extends IIonStorage{
		/** Make an ion request. Returns as many ions as possible. */
		public int requestIons(int ions);
		/** Return excess ions from the last request back to the machine. */
		public void returnIons(int ions);
		/** Return how full the Ion Buffer is. Generators should return a number > 1.0F to get priority over other sources. */
		public float getIonFill();
	}
	
	/** An Ion Relay. Is both a sink and a source. */
	public interface IIonRelay extends IIonSink, IIonSource{}
	
	/** An Ion Generator. It's a source that produces it's own ions. */
	public interface IIonGenerator extends IIonSource{
		/** How many ions are in the overflow buffer */
		public int getIonOverflow();
		/** Try to drain the ion overflow buffer into the main storage buffer. */
		public void drainIonOverflow();
	}
}