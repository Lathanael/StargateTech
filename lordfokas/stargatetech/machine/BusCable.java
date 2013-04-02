package lordfokas.stargatetech.machine;

import net.minecraft.world.World;
import lordfokas.stargatetech.common.BaseBlock;
import lordfokas.stargatetech.networks.bus.BusBlock.IBusComponent;

public class BusCable extends BaseBlock implements IBusComponent{
	public BusCable(int id) {
		super(id, "busCable");
	}

	@Override
	public boolean isPropagator() {
		return true;
	}

	@Override
	public boolean isConnector() {
		return false;
	}

	@Override
	public boolean canBusPlugOnSide(World w, int x, int y, int z, int side) {
		return true;
	}
}
