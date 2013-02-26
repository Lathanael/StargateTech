package lordfokas.stargatetech.machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import lordfokas.stargatetech.StargateTech;
import lordfokas.stargatetech.common.BaseBlockContainer;
import lordfokas.stargatetech.util.GUIHandler;
import lordfokas.stargatetech.util.TextureIndex;

public class DialingComputer extends BaseBlockContainer {

	public DialingComputer(int id) {
		super(id, TextureIndex.dialingComputer);
		setBlockName("dialingComputer");
	}
	
	@Override
	public int getBlockTextureFromSide(int side){
		return side == 5 ? TextureIndex.dialingComputer : TextureIndex.blockSingle;
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

}
