package lordfokas.stargatetech.machine;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import lordfokas.stargatetech.StargateTech;
import lordfokas.stargatetech.common.BaseBlockContainer;
import lordfokas.stargatetech.common.IDismantleable;
import lordfokas.stargatetech.networks.ion.IonNetBlock.IIonNetSource;
import lordfokas.stargatetech.networks.power.PowerNetBlock.IPowerNetComponent;
import lordfokas.stargatetech.rendering.RenderParticleIonizer;
import lordfokas.stargatetech.util.CoordinateSet;
import lordfokas.stargatetech.util.GUIHandler;
import lordfokas.stargatetech.util.Helper;
import lordfokas.stargatetech.util.IconRegistry;
import lordfokas.stargatetech.util.UnlocalizedNames;

public class ParticleIonizer extends BaseBlockContainer implements IIonNetSource, IPowerNetComponent, IDismantleable{

	public ParticleIonizer(int id) {
		super(id, UnlocalizedNames.BLOCK_IONIZER);
	}
	
	@Override
	public Icon getTextureFromSide(int side){
		if(side < 2) return this.field_94336_cN;
		else return IconRegistry.particleIonizerSide;
	}
	
	@Override
    public int getRenderType(){
    	return RenderParticleIonizer.instance().getRenderId();
    }
	
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int i0, float f1, float f2, float f3){
		if(player.isSneaking()) return false;
		player.openGui(StargateTech.instance, GUIHandler.guiParticleIonizer, w, x, y, z);
		return true;
	}
	
	private ParticleIonizerTE getTileEntity(World w, int x, int y, int z){ return (ParticleIonizerTE) w.getBlockTileEntity(x, y, z); }
	@Override public int requestIons(World w, int x, int y, int z, int ions){ return getTileEntity(w, x, y, z).requestIons(ions); }
	@Override public void giveBack(World w, int x, int y, int z, int ions){ getTileEntity(w, x, y, z).returnIons(ions); }
	@Override public float getFill(World w, int x, int y, int z){ return getTileEntity(w, x, y, z).getIonFill(); }
	@Override public EIonComponentType getIonComponentType(){ return EIonComponentType.SOURCE; }
	@Override public boolean canTubeConnectOnSide(IBlockAccess w, int x, int y, int z, int side){ return !(side == Helper.dirTop || side == Helper.dirBottom); }
	@Override public EPowerComponentType getPowerComponentType(){ return EPowerComponentType.SINK; }
	@Override public boolean canConduitConnectOnSide(IBlockAccess w, int x, int y, int z, int side){ return !(side == Helper.dirTop || side == Helper.dirBottom); }
	
	@Override
	public ParticleIonizerTE createNewTileEntity(World w) {
		return new ParticleIonizerTE();
	}
	
	@Override
	public boolean dismantle(World w, int x, int y, int z){
		if(w.isRemote) return false;
		w.spawnEntityInWorld(new EntityItem(w, x, y, z, new ItemStack(this)));
		w.setBlockAndMetadataWithNotify(x, y, z, 0, 0, Helper.SETBLOCK_NO_UPDATE);
		return false;
	}
}
