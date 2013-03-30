package lordfokas.stargatetech.items;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import lordfokas.stargatetech.StargateTech;
import lordfokas.stargatetech.common.BaseItem;
import lordfokas.stargatetech.machine.Stargate;
import lordfokas.stargatetech.machine.StargateTE;
import lordfokas.stargatetech.networks.stargate.Address;
import lordfokas.stargatetech.util.Helper;

public class AddressReaderCrystal extends BaseItem {

	public AddressReaderCrystal(int id) {
		super(id, "addressReaderCrystal");
		this.maxStackSize = 1;
	}
	
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player){
        MovingObjectPosition target = this.getMovingObjectPositionFromPlayer(world, player, true);
        if (target == null || world.isRemote) return itemStack;
        if (target.typeOfHit == EnumMovingObjectType.TILE){
            int x = target.blockX;
            int y = target.blockY;
            int z = target.blockZ;
            int k = world.getBlockId(x, y, z);
            if(Helper.getBlockInstance(world, x, y, z) instanceof Stargate){
            	TileEntity te = world.getBlockTileEntity(x, y, z);
            	if(te != null && te instanceof StargateTE){
            		Address address = ((StargateTE)te).myAddress;
            		if(address != null){
            			ItemStack stack = new ItemStack(StargateTech.addressMemoryCrystal, 1);
            			AddressMemoryCrystal.setAddress(stack, address);
            			world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, stack));
            			return new ItemStack(StargateTech.addressReader, 1);
            		}
            	}
            }
        }
        return itemStack;
    }
}