package lordfokas.stargatetech;

public class EnvironmentDedicated implements IEnvironmentProxy {

	@Override
	public boolean isDedicated() {
		return true;
	}

	@Override // Dedicated servers have no client side.
	public void runClientSide() {}
}