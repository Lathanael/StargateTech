/**
 * 
 */
package lathanael.stargatetech.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;

/**
 * 
 * 
 * @author Lathanael (aka Philippe Leipold)
 * 
 */
public class StargateLogger {
	private static Logger logger = Logger.getLogger("StargateTech");

	public static void init() {
		logger.setParent(FMLLog.getLogger());
	}

	public static void log(Level level, String message) {
		logger.log(level, message);
	}
}
