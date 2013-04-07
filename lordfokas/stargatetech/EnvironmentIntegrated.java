package lordfokas.stargatetech;

import lordfokas.stargatetech.machine.StargateTE;
import lordfokas.stargatetech.packet.PacketHandler;
import lordfokas.stargatetech.packet.PacketHandlerClient;
import lordfokas.stargatetech.rendering.RenderBusCable;
import lordfokas.stargatetech.rendering.RenderIonTube;
import lordfokas.stargatetech.rendering.RenderOre;
import lordfokas.stargatetech.rendering.RenderParticleIonizer;
import lordfokas.stargatetech.rendering.RenderPowerConduit;
import lordfokas.stargatetech.rendering.RenderShieldEmitter;
import lordfokas.stargatetech.rendering.RenderStargate;
import lordfokas.stargatetech.rendering.RenderStargateBlock;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;

public class EnvironmentIntegrated implements IEnvironmentProxy {

	@Override
	public boolean isDedicated() {
		return false;
	}

	@Override
	public void runClientSide() {
		setupRenderers();
		NetworkRegistry.instance().registerChannel(PacketHandlerClient.instance, PacketHandler.CHANNEL_STARGATE, Side.CLIENT);
	}
	
	private void setupRenderers() {
		RenderingRegistry.registerBlockHandler(RenderIonTube.instance());
		RenderingRegistry.registerBlockHandler(RenderParticleIonizer.instance());
		RenderingRegistry.registerBlockHandler(RenderShieldEmitter.instance());
		RenderingRegistry.registerBlockHandler(RenderOre.instance());
		RenderingRegistry.registerBlockHandler(RenderPowerConduit.instance());
		RenderingRegistry.registerBlockHandler(RenderStargateBlock.instance());
		RenderingRegistry.registerBlockHandler(RenderBusCable.instance());
		ClientRegistry.bindTileEntitySpecialRenderer(StargateTE.class, RenderStargate.instance);
	}
}