package lordfokas.stargatetech.api.networks;


/**
 * PowerNet TileEntity Specification
 * PowerNet Tile Entities don't have to implement any of these interfaces, but any PowerNet TileEntity
 * from this mod will implement any of these interfaces if they match what the TileEntity does.
 * 
 * @author LordFokas
 */
public final class PowerNetTE {
	private PowerNetTE(){}
	public final static int SEARCH_INTERVAL = 20;
	
	/** A power storage machine. */
	public interface IPowerStorage extends INetworkTE{
		/** How much power this machine has stored. */
		public int getPowerAmount();
		/** How much power this machine can store. */
		public int getPowerBufferSize();
	}
	
	/** A Power Sink. Must be refilled by Sources in the network. */
	public interface IPowerSink extends IPowerStorage{
		/** Refill the power buffer if all conditions are met. Call this every tick. */
		public void refillPowerBuffer();
	}
	
	/** A Power Source. Refills Sinks in the network. */
	public interface IPowerSource extends IPowerStorage{
		/** Make a power request. Returns as much power as possible. */
		public int requestPower(int power);
		/** Return excess power from the last request back into the power buffer. */
		public void returnPower(int power);
		/** Return how full the Power Buffer is. Generators should return a number > 1.0F to get priority over other sources. */
		public float getPowerFill();
	}
	
	/** A power relay. It's both a sink and a source. */
	public interface IPowerRelay extends IPowerSink, IPowerSource{}
	
	/** A power generator. It's a source that generates it's own power. */
	public interface IPowerGenerator extends IPowerSource{
		/** How much power is in the overflow buffer. */
		public int getPowerOverflow();
		/** Try to drain as much power as possible from the overflow buffer to the main buffer. */
		public void drainPowerOverflow();
	}
}