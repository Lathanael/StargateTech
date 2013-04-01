package lordfokas.stargatetech.common;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * A base for all the tile entity container blocks used by this mod.
 * @author LordFokas
 */
public abstract class BaseBlockContainer extends BaseBlock implements ITileEntityProvider {
	public BaseBlockContainer(int id, String name) {
		super(id, name);
	}
	
	public BaseBlockContainer(int id, String name, boolean hard) {
		super(id, name, hard);
	}
	
	public BaseBlockContainer(int id, String name, Material material, boolean hard) {
		super(id, name, material, hard);
	}
	
	@Override
	public void breakBlock(World w, int x, int y, int z, int id, int meta){
		super.breakBlock(w, x, y, z, id, meta);
		w.removeBlockTileEntity(x, y, z);
	}
	
	@Override
	public boolean onBlockEventReceived(World w, int x, int y, int z, int block, int event){
		super.onBlockEventReceived(w, x, y, z, block, event);
		TileEntity te = w.getBlockTileEntity(x, y, z);
		return te == null ? false : te.receiveClientEvent(block, event);
	}
}