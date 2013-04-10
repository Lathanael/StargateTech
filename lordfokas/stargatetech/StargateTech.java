package lordfokas.stargatetech;

import lordfokas.stargatetech.common.BaseItem;
import lordfokas.stargatetech.common.ParticleIonizerRecipes;
import lordfokas.stargatetech.common.StargateTab;
import lordfokas.stargatetech.items.AddressMemoryCrystal;
import lordfokas.stargatetech.items.AddressReaderCrystal;
import lordfokas.stargatetech.items.Disintegrator;
import lordfokas.stargatetech.items.Dismantler;
import lordfokas.stargatetech.items.MechanusClavia;
import lordfokas.stargatetech.items.PersonalShield;
import lordfokas.stargatetech.machine.BusCable;
import lordfokas.stargatetech.machine.DialingComputer;
import lordfokas.stargatetech.machine.DialingComputerTE;
import lordfokas.stargatetech.machine.IonTube;
import lordfokas.stargatetech.machine.NaquadahGenerator;
import lordfokas.stargatetech.machine.NaquadahGeneratorTE;
import lordfokas.stargatetech.machine.ParticleIonizer;
import lordfokas.stargatetech.machine.ParticleIonizerTE;
import lordfokas.stargatetech.machine.PowerConduit;
import lordfokas.stargatetech.machine.Shield;
import lordfokas.stargatetech.machine.ShieldEmitter;
import lordfokas.stargatetech.machine.ShieldEmitterTE;
import lordfokas.stargatetech.machine.Stargate;
import lordfokas.stargatetech.machine.StargateTE;
import lordfokas.stargatetech.networks.bus.BusPacketManager;
import lordfokas.stargatetech.networks.stargate.StargateNetwork;
import lordfokas.stargatetech.plugins.PluginBC3;
import lordfokas.stargatetech.plugins.PluginCC;
import lordfokas.stargatetech.plugins.PluginForestry;
import lordfokas.stargatetech.plugins.PluginIC2;
import lordfokas.stargatetech.plugins.PluginRC;
import lordfokas.stargatetech.plugins.PluginTE;
import lordfokas.stargatetech.util.APIImplementation;
import lordfokas.stargatetech.util.Config;
import lordfokas.stargatetech.util.EventListener;
import lordfokas.stargatetech.util.GUIHandler;
import lordfokas.stargatetech.util.ItemManager;
import lordfokas.stargatetech.util.PacketHandler;
import lordfokas.stargatetech.util.PacketHandlerServer;
import lordfokas.stargatetech.util.StargateLogger;
import lordfokas.stargatetech.util.UnlocalizedNames;
import lordfokas.stargatetech.world.LanteanBlock;
import lordfokas.stargatetech.world.NaquadahOre;
import lordfokas.stargatetech.world.NaquadriaOre;
import lordfokas.stargatetech.world.Placeholder;
import lordfokas.stargatetech.world.WorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerAboutToStart;
import cpw.mods.fml.common.Mod.ServerStopping;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid="StargateTech", name="Stargate Tech", version="Alpha 0.9.2")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class StargateTech {
	// General Stuff
	public static final WorldGenerator worldGen = new WorldGenerator();
	public static final StargateTab tab = StargateTab.instance;
	
	// Blocks
	public static ShieldEmitter shieldEmitter;
	public static Shield shield;
	public static ParticleIonizer particleIonizer;
	public static IonTube ionTube;
	public static NaquadahOre naquadahOre;
	public static NaquadriaOre naquadriaOre;
	public static PowerConduit powerConduit;
	public static NaquadahGenerator naquadahGenerator;
	public static Stargate stargate;
	public static Placeholder placeholder;
	public static DialingComputer dialingComputer;
	public static LanteanBlock lanteanBlock;
	public static BusCable busCable;
	
	// Items
	public static BaseItem naquadahShard;
	public static BaseItem naquadriaShard;
	public static BaseItem naquadahCluster;
	public static BaseItem naquadriaCluster;
	public static BaseItem naquadahIngot;
	public static BaseItem naquadriaIngot;
	public static Disintegrator disintegrator;
	public static Dismantler dismantler;
	public static MechanusClavia mechanusClavia;
	public static PersonalShield personalShield;
	public static AddressMemoryCrystal addressMemoryCrystal;
	public static AddressReaderCrystal addressReaderCrystal;
	public static BaseItem addressReader;
	
	// Mod Integration Plugins
	public PluginBC3 buildcraft3;
	public PluginCC computercraft;
	public PluginForestry forestry;
	public PluginIC2 industrialcraft2;
	public PluginRC railcraft;
	public PluginTE thermalexpansion;
	
	@Instance("StargateTech")
	public static StargateTech instance;
	
	@SidedProxy(clientSide="lordfokas.stargatetech.EnvironmentIntegrated", serverSide="lordfokas.stargatetech.EnvironmentDedicated")
	public static IEnvironmentProxy environment;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		StargateLogger.init();
		BusPacketManager.init();
		APIImplementation.init();
		Config.loadAll(new Configuration(event.getSuggestedConfigurationFile()));
		
		// Blocks
		shieldEmitter	= new ShieldEmitter(Config.shieldEmitter, Config.maxShieldGap);
		shield			= new Shield(Config.shield);
		particleIonizer = new ParticleIonizer(Config.particleIonizer);
		ionTube 		= new IonTube(Config.ionTube);
		naquadahOre 	= new NaquadahOre(Config.naquadahOre);
		naquadriaOre 	= new NaquadriaOre(Config.naquadriaOre);
		powerConduit	= new PowerConduit(Config.powerConduit);
		naquadahGenerator	= new NaquadahGenerator(Config.naquadahGenerator);
		stargate		= new Stargate(Config.stargate);
		placeholder		= new Placeholder(Config.placeholder);
		dialingComputer = new DialingComputer(Config.dialingComputer);
		lanteanBlock	= new LanteanBlock(Config.lanteanBlock);
		busCable 		= new BusCable(Config.busCable);
		
		// Items
		naquadahShard 	= (BaseItem) new BaseItem(Config.naquadahShard, UnlocalizedNames.ITEM_NQH_SHARD);
		naquadriaShard 	= (BaseItem) new BaseItem(Config.naquadriaShard, UnlocalizedNames.ITEM_NQI_SHARD);
		naquadahCluster 	= (BaseItem) new BaseItem(Config.naquadahCluster, UnlocalizedNames.ITEM_NQH_CLUSTER);
		naquadriaCluster 	= (BaseItem) new BaseItem(Config.naquadriaCluster, UnlocalizedNames.ITEM_NQI_CLUSTER);
		naquadahIngot 	= (BaseItem) new BaseItem(Config.naquadahIngot, UnlocalizedNames.ITEM_NQH_INGOT);
		naquadriaIngot 	= (BaseItem) new BaseItem(Config.naquadriaIngot, UnlocalizedNames.ITEM_NQI_INGOT);
		disintegrator 	= new Disintegrator(Config.disintegrator);
		dismantler 		= new Dismantler(Config.dismantler);
		mechanusClavia 	= new MechanusClavia(Config.mechanusClavia);
		personalShield	= new PersonalShield(Config.personalShield);
		addressMemoryCrystal	= new AddressMemoryCrystal(Config.addressMemoryCrystal);
		addressReaderCrystal	= new AddressReaderCrystal(Config.addressReaderCrystal);
		addressReader = new BaseItem(Config.addressReader, "addressReader");
	}
	
	@Init
	public void init(FMLInitializationEvent event){
		buildcraft3			= new PluginBC3();
		computercraft		= new PluginCC();
		forestry			= new PluginForestry();
		industrialcraft2	= new PluginIC2();
		railcraft			= new PluginRC();
		thermalexpansion	= new PluginTE();
		addBlocks();
		addItems();
		addRecipes();
		addTileEntities();
		refineContentProperties();
		GameRegistry.registerWorldGenerator(worldGen);
		NetworkRegistry.instance().registerGuiHandler(instance, GUIHandler.instance);
		NetworkRegistry.instance().registerChannel(PacketHandlerServer.instance, PacketHandler.CHANNEL_STARGATE, Side.SERVER);
		EventListener.register();
		environment.runClientSide();
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event){
		buildcraft3.init();
		computercraft.init();
		forestry.init();
		industrialcraft2.init();
		railcraft.init();
		thermalexpansion.init();
	}
	
	@ServerAboutToStart
	public void onServerStart(FMLServerAboutToStartEvent event){
		StargateNetwork.init();
	}
	
	@ServerStopping
	public void onServerStop(FMLServerStoppingEvent event){
		StargateNetwork.unload();
	}
	
	private void addBlocks(){
		registerBlock(shieldEmitter, "Lantean Shield Emitter");
		registerBlock(shield, "Lantean Shield");
		registerBlock(particleIonizer, "Particle Ionizer");
		registerBlock(ionTube, "Ion Tube");
		registerBlock(naquadahOre, "Naquadah Ore");
		registerBlock(naquadriaOre, "Naquadria Ore");
		registerBlock(powerConduit, "Power Conduit");
		registerBlock(naquadahGenerator, "Naquadah Generator");
		registerBlock(stargate, "Stargate");
		registerBlock(dialingComputer, "Dialing Computer");
		registerBlock(lanteanBlock, "Lantean Block");
		registerBlock(busCable, "Bus Cable");
	}
	
	private void addItems(){
		registerItem(naquadahShard, "Naquadah Shard");
		registerItem(naquadriaShard, "Naquadria Shard");
		registerItem(naquadahCluster, "Naquadah Shard Cluster");
		registerItem(naquadriaCluster, "Naquadria Shard Cluster");
		registerItem(naquadahIngot, "Naquadah Ingot");
		registerItem(naquadriaIngot, "Naquadria Ingot");
		registerItem(disintegrator, "Disintegrator");
		registerItem(dismantler, "Dismantler");
		registerItem(mechanusClavia, "Mechanus Clavia");
		registerItem(personalShield, "Personal Shield");
		registerItem(addressMemoryCrystal, "Address Memory Crystal");
		registerItem(addressReaderCrystal, "Address Reader Crystal");
		registerItem(addressReader, "Address Reader");
	}
	
	private void addRecipes(){
		ItemStack nqhShard = new ItemStack(naquadahShard);
		ItemStack nqiShard = new ItemStack(naquadriaShard);
		ItemStack nqhCluster = new ItemStack(naquadahCluster);
		ItemStack nqiCluster = new ItemStack(naquadriaCluster);
		ItemStack nqhIngot = new ItemStack(naquadahIngot);
		ItemStack nqiIngot = new ItemStack(naquadriaIngot);
		GameRegistry.addShapelessRecipe(nqhCluster, new Object[]{nqhShard, nqhShard, nqhShard, nqhShard, nqhShard, nqhShard, nqhShard, nqhShard});
		GameRegistry.addShapelessRecipe(nqiCluster, new Object[]{nqiShard, nqiShard, nqiShard, nqiShard, nqiShard, nqiShard, nqiShard, nqiShard});
		GameRegistry.addSmelting(nqhCluster.itemID, nqhIngot, 0);
		GameRegistry.addSmelting(nqiCluster.itemID, nqiIngot, 0);
		GameRegistry.addShapelessRecipe(new ItemStack(addressReaderCrystal, 1), new Object[]{new ItemStack(addressMemoryCrystal, 1), new ItemStack(addressReader, 1)});
		ParticleIonizerRecipes.add(nqhIngot, 10);
		ParticleIonizerRecipes.add(nqiIngot, 40);
	}
	
	private void addTileEntities(){
		GameRegistry.registerTileEntity(ShieldEmitterTE.class, ShieldEmitterTE.ID);
		GameRegistry.registerTileEntity(ParticleIonizerTE.class, ParticleIonizerTE.ID);
		GameRegistry.registerTileEntity(NaquadahGeneratorTE.class, NaquadahGeneratorTE.ID);
		GameRegistry.registerTileEntity(StargateTE.class, StargateTE.ID);
		GameRegistry.registerTileEntity(DialingComputerTE.class, DialingComputerTE.ID);
	}
	
	private void refineContentProperties(){
		MinecraftForge.setBlockHarvestLevel(naquadahOre, "pickaxe", 2);
	}
	
	private void registerBlock(Block block, String name){
		GameRegistry.registerBlock(block);
		LanguageRegistry.addName(block, name);
		ItemManager.putBlock(block.getUnlocalizedName(), block);
	}
	
	private void registerItem(Item item, String name){
		LanguageRegistry.addName(item, name);
		ItemManager.putItem(item.getUnlocalizedName(), item);
	}
}
