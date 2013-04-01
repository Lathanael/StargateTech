package lordfokas.stargatetech.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;

/**
 * The logger for this mod.
 * Edited by LordFokas for level-specific methods.
 * 
 * @author Lathanael (aka Philippe Leipold)
 */
public class StargateLogger {
	private static Logger logger = Logger.getLogger("StargateTech");

	public static void init() {
		logger.setParent(FMLLog.getLogger());
	}
	
	/* The other methods are easier to use, but this one will
	 * be kept to allow the use of other logging levels
	 */
	public static void log(Level level, String message) {
		logger.log(level, message);
	}
	
	public static void config(String msg){
		logger.log(Level.CONFIG, msg);
	}
	
	public static void info(String msg){
		logger.log(Level.INFO, msg);
	}
	
	public static void warning(String msg){
		logger.log(Level.WARNING, msg);
	}
	
	public static void severe(String msg){
		logger.log(Level.SEVERE, msg);
	}
}