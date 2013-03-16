package lordfokas.stargatetech;

import java.awt.image.BufferedImage;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.TextureFXManager;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import lordfokas.stargatetech.machine.StargateTE;
import lordfokas.stargatetech.packet.PacketHandler;
import lordfokas.stargatetech.packet.PacketHandlerClient;
import lordfokas.stargatetech.rendering.RenderIonTube;
import lordfokas.stargatetech.rendering.RenderOre;
import lordfokas.stargatetech.rendering.RenderParticleIonizer;
import lordfokas.stargatetech.rendering.RenderPowerConduit;
import lordfokas.stargatetech.rendering.RenderShieldEmitter;
import lordfokas.stargatetech.rendering.RenderStargate;
import lordfokas.stargatetech.rendering.RenderStargateBlock;
import lordfokas.stargatetech.util.Config;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {	
	public static final String GUI_IONIZER = "/mods/StargateTech/textures/ionizer.png";
	public static final String GUI_GENERATOR = "/mods/StargateTech/textures/generator.png";
	public static final String GUI_DIALER = "/mods/StargateTech/textures/dialer.png";
	
	public static final String GATE_SYMBOLS = "/mods/StargateTech/textures/symbols.png";
	
	@Override
	public void init(){
		super.init();
		registerRenderers();
		NetworkRegistry.instance().registerChannel(PacketHandlerClient.instance, PacketHandler.CHANNEL_STARGATE, Side.CLIENT);
	}
	
	private void registerRenderers(){
		RenderingRegistry.registerBlockHandler(RenderIonTube.instance());
		RenderingRegistry.registerBlockHandler(RenderParticleIonizer.instance());
		RenderingRegistry.registerBlockHandler(RenderShieldEmitter.instance());
		RenderingRegistry.registerBlockHandler(RenderOre.instance());
		RenderingRegistry.registerBlockHandler(RenderPowerConduit.instance());
		RenderingRegistry.registerBlockHandler(RenderStargateBlock.instance());
		
		ClientRegistry.bindTileEntitySpecialRenderer(StargateTE.class, RenderStargate.instance);
	}
}
