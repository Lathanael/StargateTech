package lordfokas.stargatetech.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemManager {
	private static Hashtable<String, ItemStack> stacks= new Hashtable<String, ItemStack>();
	private static List<String> identifiers = new ArrayList<String>();
	
	public static void putItem(String name, Item item){
		putStack(name, new ItemStack(item, 1));
	}
	
	public static void putBlock(String name, Block block){
		putStack(name, new ItemStack(block, 1));
	}
	
	private static void putStack(String name, ItemStack stack){
		stacks.put(name, stack);
		identifiers.add(name);
	}
	
	public static ItemStack getItemStack(String name){
		return stacks.get(name);
	}
	
	public static List<String> getIdentifierList(){
		return identifiers;
	}
}