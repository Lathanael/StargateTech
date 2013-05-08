package lordfokas.stargatetech.api;

import net.minecraft.item.ItemStack;

/**
 * An API is an Abstract Programming Interface.
 * What matters is that you can interface with StargateTech and not how it is implemented.
 * Given the mod is in constant mutation, and so is the API, even the API itself is abstract,
 * in an attempt at minimizing the impact all those mutations may have in interfacing mods.
 * 
 * This class will never change.
 * 
 * @author LordFokas
 */
public abstract class StargateTechAPI implements IStargateTechAPI{
	private static IStargateTechAPI apiInstance;
	
	protected StargateTechAPI(){
		apiInstance = this;
	}
	
	/**
	 * @return An instance of the current implementation of the API.
	 */
	public static IStargateTechAPI api(){
		return apiInstance;
	}
}