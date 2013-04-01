package lordfokas.stargatetech.plugins;

import cpw.mods.fml.common.Loader;
import lordfokas.stargatetech.StargateTech;
import lordfokas.stargatetech.util.StargateLogger;

/**
 * A base for Mod integration plugins.
 * @author LordFokas
 */
public abstract class Plugin{
	private boolean isLoaded = false;
	private boolean enabled;
	private String mod;
	
	/**
	 * Creates a Integration Plugin.
	 * It will only be enabled if the required mod is loaded and this plugin is enabled in the config.
	 * @param mod The modID for the mod we're integrating with.
	 * @param enabled Wether this plugin is enabled in the config or not.
	 */
	protected Plugin(String mod, boolean enabled){
		this.enabled = enabled && Loader.isModLoaded(mod);
		this.mod = mod;
	}
	
	public final boolean isLoaded(){
		return isLoaded;
	}
	
	public final boolean isEnabled(){
		return enabled;
	}
	
	public final void init(){
		if(enabled){
			StargateLogger.info("Mod \"" + mod + "\" was detected: initializing plugin.");
			try{
				initCommon();
				if(!StargateTech.environment.isDedicated()){
					initClient();
				}
				isLoaded = true;
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			StargateLogger.info("Skipping plugin for Mod \"" + mod + "\"");
		}
	}
	
	protected abstract void initClient();
	protected abstract void initCommon();
}