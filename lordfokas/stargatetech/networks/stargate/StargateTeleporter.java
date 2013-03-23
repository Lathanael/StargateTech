package lordfokas.stargatetech.networks.stargate;

import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.PortalPosition;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class StargateTeleporter extends Teleporter {
	private int x, y, z;
	
	public StargateTeleporter(WorldServer par1WorldServer) {
		super(par1WorldServer);
	}
	
	public static void teleportEntity(Entity entity, int targetDimension, int x, int y, int z) {
		MinecraftServer server = MinecraftServer.getServer();
		ServerConfigurationManager scm = server.getConfigurationManager();
		StargateTeleporter teleporter = new StargateTeleporter(server.worldServerForDimension(targetDimension));
		teleporter.setCoords(x, y, z);
		if(entity instanceof EntityPlayerMP){
			EntityPlayerMP player = (EntityPlayerMP) entity;
			scm.transferPlayerToDimension(player, targetDimension, teleporter);
		}else{
			WorldServer sourceWorld = DimensionManager.getWorld(entity.dimension);
			WorldServer targetWorld = DimensionManager.getWorld(targetDimension);
			scm.transferEntityToWorld(entity, targetDimension, sourceWorld, targetWorld, teleporter);
		}
	}
	
	public void setCoords(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public void placeInPortal(Entity entity, double par2, double par4, double par6, float par8){
		entity.posX = entity.lastTickPosX = x;
		entity.posY = entity.lastTickPosY = y;
		entity.posZ = entity.lastTickPosZ = z;
	}
	
	@Override
    public boolean placeInExistingPortal(Entity par1Entity, double par2, double par4, double par6, float par8){ return false; }
    
    @Override
    public boolean makePortal(Entity par1Entity){ return false; }
    
    @Override
    public void func_85189_a(long par1){}
}