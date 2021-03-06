package lordfokas.stargatetech.util;

import java.util.Iterator;

import lordfokas.stargatetech.networks.stargate.StargateTeleporter;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet41EntityEffect;
import net.minecraft.network.packet.Packet9Respawn;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.DimensionManager;

/**
 * Your everyday helper, with bits of common and useful code.
 * Mostly direction handling and Block class fetching.
 * @author LordFokas
 */
public final class Helper {
	// Cardinal Orientation
	public static final int dirBottom = 0;
	public static final int dirTop = 1;
	public static final int dirEast = 2;
	public static final int dirWest = 3;
	public static final int dirNorth = 4;
	public static final int dirSouth = 5;
	
	// Axial Orientation
	public static final int dirYNeg = 0;
	public static final int dirYPos = 1;
	public static final int dirZNeg = 2;
	public static final int dirZPos = 3;
	public static final int dirXNeg = 4;
	public static final int dirXPos = 5;
	
	// Adjacent Block Cycler
	public static final int[][]
		adjacentBlockCoords = {
		{ 0, -1,  0}, // Y Neg : Bottom
		{ 0,  1,  0}, // Y Pos : Top
		{ 0,  0, -1}, // Z Neg : East
		{ 0,  0,  1}, // Z Pos : West
		{-1,  0,  0}, // X Neg : North
		{ 1,  0,  0}  // X Pos : South
	};
	
	public final static double deg2rad = 180/Math.PI;
	
	public final static int SETBLOCK_NO_UPDATE = 0;
	public final static int SETBLOCK_UPDATE = 2;
	
	private Helper(){}
	
	public static Block getBlockInstance(IBlockAccess w, int x, int y, int z){
		return Block.blocksList[w.getBlockId(x, y, z)];
	}
	
	public static Block getBlockInstance(CoordinateSet cs){
		return Block.blocksList[cs.w.getBlockId(cs.x, cs.y, cs.z)];
	}
	
	public static int oppositeDirection(int dir){
		switch(dir){
			case dirXPos: return dirXNeg;
			case dirXNeg: return dirXPos;
			case dirYPos: return dirZNeg;
			case dirYNeg: return dirZPos;
			case dirZPos: return dirZNeg;
			case dirZNeg: return dirZPos;
			default: return -1;
		}
	}
	
	public static int yaw2dir(float yaw){
		int dir = (MathHelper.floor_double((double)(yaw * 4.0F / 360.0F) + 0.5D) & 3)+3;
		if(dir > 4) dir -= 4;
		switch(dir){
			case 1: return dirZPos;
			case 2: return dirXNeg;
			case 3: return dirZNeg;
			case 4: return dirXPos;
			default: return 0;
		}
	}
	
	public static void bindTexture(String texture){
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
	}
}
