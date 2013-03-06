package lordfokas.stargatetech.machine;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import lordfokas.stargatetech.StargateTech;
import lordfokas.stargatetech.common.BaseBlockContainer;
import lordfokas.stargatetech.common.IDismantleable;
import lordfokas.stargatetech.networks.power.IPowerNetSource;
import lordfokas.stargatetech.util.GUIHandler;
import lordfokas.stargatetech.util.TextureIndex;

public class NaquadahGenerator extends BaseBlockContainer implements IPowerNetSource, IDismantleable{

	public NaquadahGenerator(int id) {
		super(id, TextureIndex.naquadahGenerator);
		setBlockName("naquadahGenerator");
	}
	
	@Override // Power conduits can only connect on the sides, not on the top or bottom.
	public boolean canConduitConnectOnSide(IBlockAccess w, int x, int y, int z, int side) {
		return side > 1;
	}
	
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int i0, float f1, float f2, float f3){
		if(player.isSneaking()) return false;
		player.openGui(StargateTech.instance, GUIHandler.guiNaquadahGenerator, w, x, y, z);
		return true;
	}
	
	private NaquadahGeneratorTE getTileEntity(World w, int x, int y, int z){ return (NaquadahGeneratorTE) w.getBlockTileEntity(x, y, z); }
	@Override public EPowerComponentType getPowerComponentType(){ return EPowerComponentType.SOURCE; }
	@Override public int requestPower(World w, int x, int y, int z, int p){ return getTileEntity(w, x, y, z).requestPower(p); }
	@Override public void giveBack(World w, int x, int y, int z, int p){ getTileEntity(w, x, y, z).giveBack(p); }
	@Override public float getFill(World w, int x, int y, int z){ return getTileEntity(w, x, y, z).getFill(); }
	@Override public NaquadahGeneratorTE createNewTileEntity(World w){ return new NaquadahGeneratorTE(); }
	
	@Override
	public boolean dismantle(World w, int x, int y, int z){
		if(w.isRemote) return false;
		w.spawnEntityInWorld(new EntityItem(w, x, y, z, new ItemStack(this)));
		w.setBlock(x, y, z, 0);
		return false;
	}
}
