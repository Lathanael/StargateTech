package lordfokas.stargatetech.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import lordfokas.stargatetech.common.BaseItem;
import lordfokas.stargatetech.networks.stargate.Address;
import lordfokas.stargatetech.networks.stargate.Symbol;
import lordfokas.stargatetech.util.UnlocalizedNames;

/**
 * Stores a Stargate Address
 * @author LordFokas
 */
public class AddressMemoryCrystal extends BaseItem {
	public AddressMemoryCrystal(int id){
		super(id, UnlocalizedNames.ITEM_MEM_CRYSTAL);
		setUnlocalizedName("addressMemoryCrystal");
		setMaxStackSize(1);
		setMaxDamage(0);
	}
	
	@Override
	public EnumRarity getRarity(ItemStack ignored){
		return EnumRarity.uncommon;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool){
		Address addr = getAddr(stack);
		if(addr != null){
			list.add(addr.getName());
		}else{
			list.add("No Address Stored");
		}
	}
	
	public static void setAddress(ItemStack stack, Address addr){
		if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
		if(addr.isValid()){
			NBTTagCompound nbt = stack.stackTagCompound;
			Symbol[] sym = addr.getSymbols();
			nbt.setBoolean("hasAddr", true);
			nbt.setShort("sym0", sym[0].getID());
			nbt.setShort("sym1", sym[1].getID());
			nbt.setShort("sym2", sym[2].getID());
			nbt.setShort("sym3", sym[3].getID());
			nbt.setShort("sym4", sym[4].getID());
			nbt.setShort("sym5", sym[5].getID());
			nbt.setShort("sym6", sym[6].getID());
			nbt.setShort("sym7", sym[7].getID());
			nbt.setShort("sym8", sym[8].getID());
		}else{
			stack.stackTagCompound.setBoolean("hasAddr", false);
		}
	}
	
	public static Address getAddr(ItemStack stack){
		if(stack.hasTagCompound() && stack.stackTagCompound.hasKey("hasAddr") && stack.stackTagCompound.getBoolean("hasAddr") == true){
			NBTTagCompound nbt = stack.stackTagCompound;
			short[] symbols = new short[9];
			symbols[0] = nbt.getShort("sym0");
			symbols[1] = nbt.getShort("sym1");
			symbols[2] = nbt.getShort("sym2");
			symbols[3] = nbt.getShort("sym3");
			symbols[4] = nbt.getShort("sym4");
			symbols[5] = nbt.getShort("sym5");
			symbols[6] = nbt.getShort("sym6");
			symbols[7] = nbt.getShort("sym7");
			symbols[8] = nbt.getShort("sym8");
			return new Address(symbols);
		}
		return null;
	}
}
