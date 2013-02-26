package lordfokas.stargatetech.util;

import net.minecraftforge.common.Configuration;

public final class Config {
	private Config(){}
	
	//****************************************************************************
	// BLOCKS
	public static int shieldEmitter;
	public static int shield;
	public static int particleIonizer;
	public static int ionTube;
	public static int naquadahOre;
	public static int naquadriaOre;
	public static int powerConduit;
	public static int naquadahGenerator;
	public static int stargate;
	public static int placeholder;
	public static int dialingComputer;
	
	//****************************************************************************
	// ITEMS
	public static int naquadahShard;
	public static int naquadriaShard;
	public static int naquadahShardCluster;
	public static int naquadriaShardCluster;
	public static int naquadahIngot;
	public static int naquadriaIngot;
	public static int disintegrator;
	public static int dismantler;
	public static int mechanusClavia;
	public static int personalShield;
	public static int addressMemoryCrystal;
	
	//****************************************************************************
	// CLIENT
	public static boolean animationsOn;
	
	//****************************************************************************
	// COMMON
	public static int maxShieldGap;
	
	//****************************************************************************
	// MOD PLUGINS
	public static boolean pluginBC3;
	public static boolean pluginCC;
	public static boolean pluginForestry;
	public static boolean pluginIC2;
	public static boolean pluginRC;
	public static boolean pluginTC3;
	public static boolean pluginTE;
	
	//****************************************************************************
	// Config Mechanics
	private static Configuration cfg = null;
	private static final String CLIENT = "cfg.client";
	private static final String COMMON = "cfg.common";
	private static final String BLOCK = "ids.block";
	private static final String ITEM = "ids.item";
	private static final String PLUGINS = "plugins";
	private static final String PLUGIN_BC3 = "plugins.buildcraft3";
	private static final String PLUGIN_CC = "plugins.computercraft";
	private static final String PLUGIN_FORESTRY = "plugins.forestry";
	private static final String PLUGIN_IC2 = "plugins.industrialcraft2";
	private static final String PLUGIN_RC = "plugins.railcraft";
	private static final String PLUGIN_TC3 = "plugins.thaumcraft3";
	private static final String PLUGIN_TE = "plugins.thermalexpansion";
	
	public static void loadAll(Configuration c){
		if(cfg == null) cfg = c;
		else return;
		int block = 3974;
		int item = 5001;
		cfg.load();
		
		//************************************************************************
		// # BLOCKS #
		// Machine:
		shieldEmitter	= cfg.get(BLOCK, 	"shieldEmitter",	block++).getInt();
		shield			= cfg.get(BLOCK, 	"shield",			block++).getInt();
		particleIonizer = cfg.get(BLOCK, 	"particleIonizer",	block++).getInt();
		ionTube 		= cfg.get(BLOCK, 	"ionTube",			block++).getInt();
		powerConduit 	= cfg.get(BLOCK, 	"powerConduit",		block++).getInt();
		naquadahGenerator = cfg.get(BLOCK,	"naquadahGenerator",block++).getInt();
		stargate		= cfg.get(BLOCK,	"stargateBase",		block++).getInt();
		dialingComputer = cfg.get(BLOCK,	"dialingComputer",	block++).getInt();
		
		// World:
		naquadahOre 	= cfg.get(BLOCK, 	"naquadahOre",		block++).getInt();
		naquadriaOre 	= cfg.get(BLOCK, 	"naquadriaOre",		block++).getInt();
		placeholder		= cfg.get(BLOCK,	"placeholder",		block++).getInt();
		
		
		//***********************************************************************
		// # ITEMS #
		naquadahShard 	= cfg.get(ITEM,		"naquadahShard",	item++).getInt();
		naquadriaShard 	= cfg.get(ITEM,		"naquadriaShard",	item++).getInt();
		
		naquadahShardCluster = cfg.get(ITEM, "naquadahShardCluster", item++).getInt();
		naquadriaShardCluster= cfg.get(ITEM, "naquadriaShardCluster",item++).getInt();
		
		naquadahIngot 	= cfg.get(ITEM,		"naquadahIngot",	item++).getInt();
		naquadriaIngot 	= cfg.get(ITEM,		"naquadriaIngot",	item++).getInt();
		disintegrator 	= cfg.get(ITEM,		"disintegrator",	item++).getInt();
		dismantler 		= cfg.get(ITEM,		"dismantler",		item++).getInt();
		mechanusClavia 	= cfg.get(ITEM,		"mechanusClavia",	item++).getInt();
		personalShield 	= cfg.get(ITEM,		"personalShield",	item++).getInt();
		addressMemoryCrystal = cfg.get(ITEM, "addressMemoryCrystal", item++).getInt();
		
		
		//***********************************************************************
		// # CLIENT #
		animationsOn 	= cfg.get(CLIENT,	"enableAnimations",	true).getBoolean(true);
		
		
		//***********************************************************************
		// # COMMON #
		maxShieldGap 	= cfg.get(COMMON,	"maxShieldGap",		5).getInt();
		
		
		enforceConstraints();
		processPlugins();
		cfg.save();
	}
	
	private static void enforceConstraints(){
		if(maxShieldGap > 10) maxShieldGap = 10;
		if(maxShieldGap < 3) maxShieldGap = 3;
	}
	
	private static void processPlugins(){
		pluginBC3 = cfg.get(PLUGINS, "enableBuildcraft3", true).getBoolean(true);
		pluginCC = cfg.get(PLUGINS, "enableComputerCraft", true).getBoolean(true);
		pluginForestry = cfg.get(PLUGINS, "enableForestry", true).getBoolean(true);
		pluginIC2 = cfg.get(PLUGINS, "enableIndustrialCraft2", true).getBoolean(true);
		pluginRC = cfg.get(PLUGINS, "enableRailcraft", true).getBoolean(true);
		pluginTC3 = cfg.get(PLUGINS, "enableThaumcraft3", true).getBoolean(true);
		pluginTE = cfg.get(PLUGINS, "enableThermalExpansion", true).getBoolean(true);
	}
}