package lordfokas.stargatetech.api;

import java.util.List;

import net.minecraft.item.ItemStack;

/**
 * Represents the features available in the API.
 * If the underlying systems change, and the API's implementation has to change
 * it still doesn't mean anyone using the API has to download a newer version
 * or that there needs to be a newer version at all.
 * 
 * This is intended to have the minimum impact possible on mods using the API,
 * as well as to maximize the compatibility between older and newer versions
 * of mods that interface with StargateTech.
 * 
 * @author LordFokas
 */
public interface IStargateTechAPI{
	
	/**
	 * A version number reflecting changes in the stargatetech.api package.
	 * Changes every time something changes in the package.
	 * @return The API version number.
	 */
	public String getVersion();
	
	/**
	 * Same thing as String IStargateTechAPI.getVersion(), but done in
	 * an Integer Array, so that API users can check if the current version
	 * supports a certain feature without the need to parse a String.
	 * 
	 * @return
	 */
	public int[] getParsedVersion();
	
	/**
	 * Used to get items / blocks (under the format of an ItemStack of size 1)
	 * added by this mod. Check for null values or call getValidNames() first
	 * to get a list of valid item names you can use on this call.
	 * 
	 * @param itemName The internal name of the item you want.
	 * @return An ItemStack of size 1 with the item or null if the name isn't valid.
	 */
	public ItemStack getItemStack(String itemName);
	
	/**
	 * @return a list of all valid item names you can use on getItemStack()
	 */
	public List<String> getValidItemNames();
	
	/**
	 * Register a new PacketID for use in the Bus system.
	 * The name must be unique in the entire game.
	 * 
	 * @param name The packet name.
	 * @return An id for the packet ranging 1 - 255, or 0 in case of error.
	 */
	public int getNewBusPacketID(String name);
}