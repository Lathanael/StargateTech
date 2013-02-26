package lordfokas.stargatetech;

import java.awt.image.BufferedImage;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.TextureFXManager;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.network.NetworkRegistry;
import lordfokas.stargatetech.machine.StargateTE;
import lordfokas.stargatetech.rendering.RenderIonTube;
import lordfokas.stargatetech.rendering.RenderOre;
import lordfokas.stargatetech.rendering.RenderParticleIonizer;
import lordfokas.stargatetech.rendering.RenderPowerConduit;
import lordfokas.stargatetech.rendering.RenderShieldEmitter;
import lordfokas.stargatetech.rendering.RenderStargate;
import lordfokas.stargatetech.rendering.RenderStargateBlock;
import lordfokas.stargatetech.util.Config;
import lordfokas.stargatetech.util.PacketHandler;
import lordfokas.stargatetech.util.PacketHandlerClient;
import lordfokas.stargatetech.util.TextureIndex;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.src.ModTextureAnimation;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {
	public static final String ITEM_TEXTURES = "/lordfokas/stargatetech/textures/sprite_items.png";
	public static final String BLOCK_TEXTURES = "/lordfokas/stargatetech/textures/sprite_blocks.png";
	
	public static final String NAQUADAH_GLOW = "/lordfokas/stargatetech/textures/naquadah.png";
	public static final String NAQUADRIA_GLOW = "/lordfokas/stargatetech/textures/naquadria.png";
	public static final String SHIELD_PULSAR = "/lordfokas/stargatetech/textures/shield.png";
	
	public static final String GUI_IONIZER = "/lordfokas/stargatetech/textures/ionizer.png";
	public static final String GUI_GENERATOR = "/lordfokas/stargatetech/textures/generator.png";
	public static final String GUI_DIALER = "/lordfokas/stargatetech/textures/dialer.png";
	
	public static final String GATE_SYMBOLS = "/lordfokas/stargatetech/textures/symbols.png";
	
	@Override
	public void init(){
		super.init();
		preloadTextures();
		if(Config.animationsOn)
			registerAnimatedTextures();
		registerBlockRenderers();
		NetworkRegistry.instance().registerChannel(PacketHandlerClient.instance, PacketHandler.CHANNEL_STARGATE, Side.CLIENT);
	}
	
	private void preloadTextures(){
		MinecraftForgeClient.preloadTexture(ITEM_TEXTURES);
		MinecraftForgeClient.preloadTexture(BLOCK_TEXTURES);
		MinecraftForgeClient.preloadTexture(NAQUADAH_GLOW);
		MinecraftForgeClient.preloadTexture(NAQUADRIA_GLOW);
		MinecraftForgeClient.preloadTexture(SHIELD_PULSAR);
		MinecraftForgeClient.preloadTexture(GATE_SYMBOLS);
	}
	
	private void registerAnimatedTextures(){
		tryAddAnimation(BLOCK_TEXTURES, TextureIndex.naquadahGlow, NAQUADAH_GLOW, 3);
		tryAddAnimation(BLOCK_TEXTURES, TextureIndex.naquadriaGlow, NAQUADRIA_GLOW, 3);
		tryAddAnimation(BLOCK_TEXTURES, TextureIndex.shield, SHIELD_PULSAR, 5);
	}
	
	private void registerBlockRenderers(){
		RenderingRegistry.registerBlockHandler(RenderIonTube.instance());
		RenderingRegistry.registerBlockHandler(RenderParticleIonizer.instance());
		RenderingRegistry.registerBlockHandler(RenderShieldEmitter.instance());
		RenderingRegistry.registerBlockHandler(RenderOre.instance());
		RenderingRegistry.registerBlockHandler(RenderPowerConduit.instance());
		RenderingRegistry.registerBlockHandler(RenderStargateBlock.instance());
		
		ClientRegistry.bindTileEntitySpecialRenderer(StargateTE.class, RenderStargate.instance);
	}
	
	private boolean tryAddAnimation(String file, int index, String frames, int delay){
		RenderEngine renderEngine = FMLClientHandler.instance().getClient().renderEngine;
		try{
			BufferedImage img = TextureFXManager.instance().loadImageFromTexturePack(renderEngine, frames);
			TextureFXManager.instance().addAnimation(new ModTextureAnimation(index, 1, file, img, delay));
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
