package lordfokas.stargatetech.machine;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import lordfokas.stargatetech.StargateTech;
import lordfokas.stargatetech.common.BaseBlockContainer;
import lordfokas.stargatetech.networks.bus.BusBlock.IBusComponent;
import lordfokas.stargatetech.util.GUIHandler;
import lordfokas.stargatetech.util.Helper;
import lordfokas.stargatetech.util.IconRegistry;
import lordfokas.stargatetech.util.UnlocalizedNames;

public class DialingComputer extends BaseBlockContainer implements IBusComponent{

	public DialingComputer(int id) {
		super(id, UnlocalizedNames.BLOCK_DIALING);
	}
	
	@Override
	public Icon getTextureFromSide(int side, int meta){
		if(side == meta) return this.blockIcon;
		else return IconRegistry.machine;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving living, ItemStack stack){
		int dir = -1;
		if(living instanceof EntityPlayer){
			dir = Helper.yaw2dir(living.rotationYaw);
			world.setBlockMetadataWithNotify(x, y, z, dir, Helper.SETBLOCK_UPDATE);
		}
	}
	
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int i0, float f1, float f2, float f3){
		if(player.isSneaking()) return false;
		player.openGui(StargateTech.instance, GUIHandler.guiDialingComputer, w, x, y, z);
		return true;
	}
	
	@Override
	public DialingComputerTE createNewTileEntity(World var1) {
		return new DialingComputerTE();
	}

	@Override
	public boolean canBusPlugOnSide(IBlockAccess w, int x, int y, int z, int side) {
		return side > 1 && side != w.getBlockMetadata(x, y, z);
	}
}