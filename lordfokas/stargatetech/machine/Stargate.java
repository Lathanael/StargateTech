package lordfokas.stargatetech.machine;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import lordfokas.stargatetech.ClientProxy;
import lordfokas.stargatetech.StargateTech;
import lordfokas.stargatetech.common.BaseBlockContainer;
import lordfokas.stargatetech.common.IDismantleable;
import lordfokas.stargatetech.networks.stargate.Address;
import lordfokas.stargatetech.networks.stargate.StargateNetwork;
import lordfokas.stargatetech.rendering.RenderStargateBlock;
import lordfokas.stargatetech.util.Helper;
import lordfokas.stargatetech.util.TextureIndex;

public class Stargate extends BaseBlockContainer implements IDismantleable {

	public Stargate(int id) {
		super(id, TextureIndex.stargate);
		setBlockName("stargate");
		setLightOpacity(0);
	}
	
	@Override
	public void onBlockPlacedBy(World w, int x, int y, int z, EntityLiving l){
		int dir = Helper.yaw2dir(l.rotationYaw);
		dir = Helper.oppositeDirection(dir);
		checkPlace(w, x, y, z, dir);
		Address addr = StargateNetwork.instance().getNewRandomAddress(w, x, y, z);
		StargateTE stargate = (StargateTE) w.getBlockTileEntity(x, y, z);
		stargate.addr = addr;
	}
	
	@Override
	public int getRenderType(){
		return RenderStargateBlock.instance().getRenderId();
	}
	
	@Override
	public String getTextureFile(){
		return ClientProxy.ITEM_TEXTURES;
	}
	
	public void checkPlace(World w, int x, int y, int z, int dir){
		boolean placed = false;
		boolean dirX = (dir == Helper.dirXNeg || dir == Helper.dirXPos);
		if(dirX){
			if(w.isAirBlock(x, y, z+1) && w.isAirBlock(x, y, z-1) && w.isAirBlock(x, y, z+2) && w.isAirBlock(x, y, z-2)){
				placed = true;
				for(int lz = -3; lz <= 3; lz++){
					for(int ly = 1; ly <= 6; ly++){
						if(!w.isAirBlock(x, y + ly, z + lz)){
							placed = false;
							break;
						}
					}
					if(!placed){
						break;
					}
				}
			}
		} else {
			if(w.isAirBlock(x+1, y, z) && w.isAirBlock(x-1, y, z) && w.isAirBlock(x+2, y, z) && w.isAirBlock(x-2, y, z)){
				placed = true;
				for(int lx = -3; lx <= 3; lx++){
					for(int ly = 1; ly <= 6; ly++){
						if(!w.isAirBlock(x + lx, y + ly, z)){
							placed = false;
							break;
						}
					}
					if(!placed){
						break;
					}
				}
			}
		}
		if(placed == false){
			if(!w.isRemote){
				w.setBlock(x, y, z, 0);
				w.spawnEntityInWorld(new EntityItem(w, x+0.5, y+0.25, z+0.5, new ItemStack(this)));
			}
		}else{
			TileEntity te = w.getBlockTileEntity(x, y, z);
			StargateTE sg = (StargateTE) te;
			sg.setDirection(dir);
			setPlaceholders(w, x, y ,z, dir, true);
		}
	}
	
	@Override
	public void breakBlock(World w, int x, int y, int z, int id, int meta){
		setPlaceholders(w, x, y, z, meta, false);
		Address addr = ((StargateTE) w.getBlockTileEntity(x, y, z)).addr;
		StargateNetwork.instance().remove(w, addr);
	}
	
	public void setPlaceholders(World w, int x, int y, int z, int dir, boolean set){
		int bid = set ? StargateTech.placeholder.blockID : 0;
		boolean dirX = (dir == Helper.dirXNeg || dir == Helper.dirXPos);
		if(dirX){
			// base
			w.setBlock(x, y, z-2, bid);
			w.setBlock(x, y, z-1, bid);
			w.setBlock(x, y, z+1, bid);
			w.setBlock(x, y, z+2, bid);
			// sides
			w.setBlock(x, y+1, z-3, bid);
			w.setBlock(x, y+1, z+3, bid);
			w.setBlock(x, y+2, z-3, bid);
			w.setBlock(x, y+2, z+3, bid);
			w.setBlock(x, y+3, z-3, bid);
			w.setBlock(x, y+3, z+3, bid);
			w.setBlock(x, y+4, z-3, bid);
			w.setBlock(x, y+4, z+3, bid);
			w.setBlock(x, y+5, z-3, bid);
			w.setBlock(x, y+5, z+3, bid);
			w.setBlock(x, y+5, z-2, bid);
			w.setBlock(x, y+5, z+2, bid);
			// top
			w.setBlock(x, y+6, z-3, bid);
			w.setBlock(x, y+6, z-2, bid);
			w.setBlock(x, y+6, z-1, bid);
			w.setBlock(x, y+6, z, bid);
			w.setBlock(x, y+6, z+1, bid);
			w.setBlock(x, y+6, z+2, bid);
			w.setBlock(x, y+6, z+3, bid);
		}else{
			// base
			w.setBlock(x-2, y, z, bid);
			w.setBlock(x-1, y, z, bid);
			w.setBlock(x+1, y, z, bid);
			w.setBlock(x+2, y, z, bid);
			// sides
			w.setBlock(x-3, y+1, z, bid);
			w.setBlock(x+3, y+1, z, bid);
			w.setBlock(x-3, y+2, z, bid);
			w.setBlock(x+3, y+2, z, bid);
			w.setBlock(x-3, y+3, z, bid);
			w.setBlock(x+3, y+3, z, bid);
			w.setBlock(x-3, y+4, z, bid);
			w.setBlock(x+3, y+4, z, bid);
			w.setBlock(x-3, y+5, z, bid);
			w.setBlock(x+3, y+5, z, bid);
			w.setBlock(x-2, y+5, z, bid);
			w.setBlock(x+2, y+5, z, bid);
			// top
			w.setBlock(x-3, y+6, z, bid);
			w.setBlock(x-2, y+6, z, bid);
			w.setBlock(x-1, y+6, z, bid);
			w.setBlock(x, y+6, z, bid);
			w.setBlock(x+1, y+6, z, bid);
			w.setBlock(x+2, y+6, z, bid);
			w.setBlock(x+3, y+6, z, bid);
		}
	}
	
	@Override public boolean isOpaqueCube(){ return false; }
	@Override public TileEntity createNewTileEntity(World w){ return new StargateTE(); }
	
	@Override
	public boolean dismantle(World w, int x, int y, int z){
		if(w.isRemote) return false;
		w.spawnEntityInWorld(new EntityItem(w, x, y, z, new ItemStack(this)));
		w.setBlock(x, y, z, 0);
		return false;
	}
}