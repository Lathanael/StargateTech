package lordfokas.stargatetech.util;

import net.minecraftforge.common.Configuration;

/**
 * Configuration handler. Takes care of every config-related values.
 * @author LordFokas
 */
public final class Config {
	private Config(){}
	
	//****************************************************************************
	// BLOCKS
	public static int shieldEmitter;
	public static int shield;
	public static int particleIonizer;
	public static int ionTube;
	public static int naquadahOre;
	public static int naquadahBlock;
	public static int naquadriaOre;
	public static int naquadriaBlock;
	public static int powerConduit;
	public static int naquadahGenerator;
	public static int stargate;
	public static int placeholder;
	public static int dialingComputer;
	public static int lanteanBlock;
	public static int busCable;
	
	//****************************************************************************
	// ITEMS
	public static int naquadahShard;
	public static int naquadriaShard;
	public static int naquadahCluster;
	public static int naquadriaCluster;
	public static int naquadahIngot;
	public static int naquadriaIngot;
	public static int disintegrator;
	public static int dismantler;
	public static int mechanusClavia;
	public static int personalShield;
	public static int addressMemoryCrystal;
	public static int addressReaderCrystal;
	public static int addressReader;
	
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
		
		shieldEmitter	= cfg.getBlock(BLOCK, 	"shieldEmitter",	block++).getInt();
		shield			= cfg.getBlock(BLOCK, 	"shield",			block++).getInt();
		particleIonizer = cfg.getBlock(BLOCK, 	"particleIonizer",	block++).getInt();
		ionTube 		= cfg.getBlock(BLOCK, 	"ionTube",			block++).getInt();
		powerConduit 	= cfg.getBlock(BLOCK, 	"powerConduit",		block++).getInt();
		naquadahGenerator=cfg.getBlock(BLOCK,	"naquadahGenerator",block++).getInt();
		stargate		= cfg.getBlock(BLOCK,	"stargateBase",		block++).getInt();
		dialingComputer = cfg.getBlock(BLOCK,	"dialingComputer",	block++).getInt();
		busCable		= cfg.getBlock(BLOCK,	"busCable",			block++).getInt();
		
		// World:
		naquadahOre 	= cfg.getBlock(BLOCK, 	"naquadahOre",		block++).getInt();
		naquadahBlock 	= cfg.getBlock(BLOCK, 	"naquadahBlock",	block++).getInt();
		naquadriaOre 	= cfg.getBlock(BLOCK, 	"naquadriaOre",		block++).getInt();
		naquadriaBlock 	= cfg.getBlock(BLOCK, 	"naquadriaBlock",	block++).getInt();
		placeholder		= cfg.getBlock(BLOCK,	"placeholder",		block++).getInt();
		lanteanBlock	= cfg.getBlock(BLOCK,	"lanteanBlock",		block++).getInt();
		
		
		//***********************************************************************
		// # ITEMS #
		naquadahShard 	= cfg.getItem(ITEM,		"naquadahShard",	item++).getInt();
		naquadriaShard 	= cfg.getItem(ITEM,		"naquadriaShard",	item++).getInt();
		
		naquadahCluster	= cfg.getItem(ITEM, "naquadahShardCluster",	item++).getInt();
		naquadriaCluster	= cfg.getItem(ITEM, "naquadriaShardCluster",item++).getInt();
		
		naquadahIngot 	= cfg.getItem(ITEM,		"naquadahIngot",	item++).getInt();
		naquadriaIngot 	= cfg.getItem(ITEM,		"naquadriaIngot",	item++).getInt();
		disintegrator 	= cfg.getItem(ITEM,		"disintegrator",	item++).getInt();
		dismantler 		= cfg.getItem(ITEM,		"dismantler",		item++).getInt();
		mechanusClavia 	= cfg.getItem(ITEM,		"mechanusClavia",	item++).getInt();
		personalShield 	= cfg.getItem(ITEM,		"personalShield",	item++).getInt();
		addressMemoryCrystal = cfg.getItem(ITEM, "addressMemoryCrystal", item++).getInt();
		addressReaderCrystal = cfg.getItem(ITEM, "addressReaderCrystal", item++).getInt();
		addressReader = cfg.getItem(ITEM, "addressReader", item++).getInt();
		
		
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
	
	// Cap some values to hardcoded limits.
	private static void enforceConstraints(){
		if(maxShieldGap > 10) maxShieldGap = 10;
		if(maxShieldGap < 3) maxShieldGap = 3;
	}
	
	// Allow the player to disable specific mod integration plugins.
	private static void processPlugins(){
		pluginBC3 = cfg.get(PLUGINS, "enableBuildcraft3", true).getBoolean(true);
		pluginCC = cfg.get(PLUGINS, "enableComputerCraft", true).getBoolean(true);
		pluginForestry = cfg.get(PLUGINS, "enableForestry", true).getBoolean(true);
		pluginIC2 = cfg.get(PLUGINS, "enableIndustrialCraft2", true).getBoolean(true);
		pluginRC = cfg.get(PLUGINS, "enableRailcraft", true).getBoolean(true);
		pluginTE = cfg.get(PLUGINS, "enableThermalExpansion", true).getBoolean(true);
	}
}