package lordfokas.stargatetech;

import net.minecraftforge.common.Configuration;
import lordfokas.stargatetech.common.BaseItem;
import lordfokas.stargatetech.common.StargateTab;
import lordfokas.stargatetech.items.AddressMemoryCrystal;
import lordfokas.stargatetech.items.Disintegrator;
import lordfokas.stargatetech.items.Dismantler;
import lordfokas.stargatetech.items.MechanusClavia;
import lordfokas.stargatetech.items.PersonalShield;
import lordfokas.stargatetech.machine.DialingComputer;
import lordfokas.stargatetech.machine.IonTube;
import lordfokas.stargatetech.machine.NaquadahGenerator;
import lordfokas.stargatetech.machine.ParticleIonizer;
import lordfokas.stargatetech.machine.PowerConduit;
import lordfokas.stargatetech.machine.Shield;
import lordfokas.stargatetech.machine.ShieldEmitter;
import lordfokas.stargatetech.machine.Stargate;
import lordfokas.stargatetech.plugins.PluginBC3;
import lordfokas.stargatetech.plugins.PluginCC;
import lordfokas.stargatetech.plugins.PluginForestry;
import lordfokas.stargatetech.plugins.PluginIC2;
import lordfokas.stargatetech.plugins.PluginRC;
import lordfokas.stargatetech.plugins.PluginTC3;
import lordfokas.stargatetech.plugins.PluginTE;
import lordfokas.stargatetech.util.Config;
import lordfokas.stargatetech.util.TextureIndex;
import lordfokas.stargatetech.world.WorldGenerator;
import lordfokas.stargatetech.world.NaquadahOre;
import lordfokas.stargatetech.world.NaquadriaOre;
import lordfokas.stargatetech.world.Placeholder;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="StargateTech", name="Stargate Tech", version="Alpha 0.7")
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
	
	// Items
	public static BaseItem naquadahShard;
	public static BaseItem naquadriaShard;
	public static BaseItem naquadahShardCluster;
	public static BaseItem naquadriaShardCluster;
	public static BaseItem naquadahIngot;
	public static BaseItem naquadriaIngot;
	public static Disintegrator disintegrator;
	public static Dismantler dismantler;
	public static MechanusClavia mechanusClavia;
	public static PersonalShield personalShield;
	public static AddressMemoryCrystal addressMemoryCrystal;
	
	// Mod Compatibility Plugins
	public PluginBC3 buildcraft3;
	public PluginCC computercraft;
	public PluginForestry forestry;
	public PluginIC2 industrialcraft2;
	public PluginRC railcraft;
	public PluginTC3 thaumcraft3;
	public PluginTE thermalexpansion;
	
	@Instance("StargateTech")
	public static StargateTech instance;
	
	@SidedProxy(clientSide="lordfokas.stargatetech.ClientProxy", serverSide="lordfokas.stargatetech.CommonProxy")
	public static CommonProxy proxy;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
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
		
		// Items
		naquadahShard 	= (BaseItem) new BaseItem(Config.naquadahShard, TextureIndex.naquadahShard).setItemName("naquadahShard");
		naquadriaShard 	= (BaseItem) new BaseItem(Config.naquadriaShard, TextureIndex.naquadriaShard).setItemName("naquadriaShard");
		naquadahShardCluster 	= (BaseItem) new BaseItem(Config.naquadahShardCluster, TextureIndex.naquadahShardCluster).setItemName("naquadahShardCluster");
		naquadriaShardCluster 	= (BaseItem) new BaseItem(Config.naquadriaShardCluster, TextureIndex.naquadriaShardCluster).setItemName("naquadriaShardCluster");
		naquadahIngot 	= (BaseItem) new BaseItem(Config.naquadahIngot, TextureIndex.naquadahIngot).setItemName("naquadahIngot");
		naquadriaIngot 	= (BaseItem) new BaseItem(Config.naquadriaIngot, TextureIndex.naquadriaIngot).setItemName("naquadriaIngot");
		disintegrator 	= new Disintegrator(Config.disintegrator);
		dismantler 		= new Dismantler(Config.dismantler);
		mechanusClavia 	= new MechanusClavia(Config.mechanusClavia);
		personalShield	= new PersonalShield(Config.personalShield);
		addressMemoryCrystal	= new AddressMemoryCrystal(Config.addressMemoryCrystal);
	}
	
	@Init
	public void init(FMLInitializationEvent event){
		buildcraft3			= new PluginBC3();
		computercraft		= new PluginCC();
		forestry			= new PluginForestry();
		industrialcraft2	= new PluginIC2();
		railcraft			= new PluginRC();
		thaumcraft3			= new PluginTC3();
		thermalexpansion	= new PluginTE();
		proxy.init();
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event){
		buildcraft3.init();
		computercraft.init();
		forestry.init();
		industrialcraft2.init();
		railcraft.init();
		thaumcraft3.init();
		thermalexpansion.init();
	}
}
