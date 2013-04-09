package lordfokas.stargatetech.util;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import lordfokas.stargatetech.StargateTech;
import lordfokas.stargatetech.api.StargateTechAPI;
import lordfokas.stargatetech.networks.bus.BusPacketManager;

public final class APIImplementation extends StargateTechAPI{
	public static void init(){
		new APIImplementation();
	}
	
	int[] parsedVersion = new int[]{0, 0, 0, 0};
	
	private APIImplementation(){
		StargateLogger.info("Initializing StargateTech with API version " + getVersion());
	}
	
	@Override
	public String getVersion(){
		return "0.0.0.0-dev";
	}
	
	@Override
	public int[] getParsedVersion() {
		return parsedVersion;
	}
	
	@Override
	public ItemStack getItemStack(String itemName){
		return ItemManager.getItemStack(itemName);
	}
	
	@Override
	public List<String> getValidItemNames(){
		return ItemManager.getIdentifierList();
	}
	
	@Override
	public int getNewBusPacketID(String name) {
		return BusPacketManager.manager().getNewPacketID(name);
	}

	@Override
	public CreativeTabs getCreativeTab() {
		return StargateTech.tab;
	}
}