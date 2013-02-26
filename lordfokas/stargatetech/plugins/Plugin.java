package lordfokas.stargatetech.plugins;

import cpw.mods.fml.common.Loader;
import lordfokas.stargatetech.ClientProxy;
import lordfokas.stargatetech.StargateTech;

public abstract class Plugin{
	private boolean isLoaded = false;
	private boolean enabled;
	private String mod;
	
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
			System.out.println("[StargateTech] Mod " + mod + " was detected: initializing plugin.");
			try{
				initCommon();
				if(StargateTech.proxy instanceof ClientProxy){
					initClient();
				}
				isLoaded = true;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	protected abstract void initClient();
	protected abstract void initCommon();
}