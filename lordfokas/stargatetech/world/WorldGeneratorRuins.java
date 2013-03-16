package lordfokas.stargatetech.world;

import java.util.ArrayList;
import java.util.Random;

import lordfokas.stargatetech.StargateTech;
import lordfokas.stargatetech.machine.Shield;
import lordfokas.stargatetech.util.Helper;
import lordfokas.stargatetech.util.WorldGenerationHelper;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public final class WorldGeneratorRuins {
	private final static int RUIN_RARITY_GRASS	= 8;
	private final static int RUIN_RARITY_DESERT	= 7;
	private final static int RUIN_RARITY_WATER	= 15;
	
	private final static int LOOT_RARITY_SHIELD			= 25;
	private final static int LOOT_RARITY_NAQUADAH		= 2;
	private final static int LOOT_RARITY_NAQUADRIA		= 3;
	private final static int LOOT_RARITY_MECHANUS		= 15;
	private final static int LOOT_RARITY_DISINTEGRATOR	= 6;
	private final static int LOOT_RARITY_DISMANTLER		= 6;
	
	private WorldGeneratorRuins(){}
	
	public static void generate(Random r, World w, int x, int y, int z){
		int block = w.getBlockId(x, y, z);
		if(block == Block.dirt.blockID || block == Block.grass.blockID){
			generateGrassRuin(r,w,x,y,z);
		}else if(block == Block.sand.blockID){
			generateDesertRuin(r,w,x,y,z);
		}else if(block == Block.waterStill.blockID){
			generateWaterRuin(r,w,x,y,z);
		}
	}
	
	private static void generateLootChest(Random r, World w, int x, int y, int z){
		w.setBlockAndMetadataWithNotify(x, y, z, Block.chest.blockID, 0, Helper.SETBLOCK_NO_UPDATE);
		IInventory chest = (IInventory) w.getBlockTileEntity(x, y, z);
		ArrayList<ItemStack> loot = new ArrayList<ItemStack>();
		int value = 0;
		while (value < 4){
			if(r.nextInt(LOOT_RARITY_SHIELD) == 0){
				int damage = (StargateTech.personalShield.getMaxDamage() / 4) + r.nextInt(StargateTech.personalShield.getMaxDamage() / 2);
				ItemStack shield = new ItemStack(StargateTech.personalShield.itemID, 1, damage);
				loot.add(shield);
				value += LOOT_RARITY_SHIELD;
			}
			if(r.nextInt(LOOT_RARITY_NAQUADAH) == 0){
				int size = 1 + r.nextInt(32);
				ItemStack naquadah = new ItemStack(StargateTech.naquadahIngot, size);
				loot.add(naquadah);
				value += LOOT_RARITY_NAQUADAH;
			}
			if(r.nextInt(LOOT_RARITY_NAQUADRIA) == 0){
				int size = 1 + r.nextInt(16);
				ItemStack naquadria = new ItemStack(StargateTech.naquadriaIngot, size);
				loot.add(naquadria);
				value += LOOT_RARITY_NAQUADRIA;
			}
			if(r.nextInt(LOOT_RARITY_MECHANUS) == 0){
				ItemStack mechanus = new ItemStack(StargateTech.mechanusClavia.itemID, 1, 0);
				loot.add(mechanus);
				value += LOOT_RARITY_MECHANUS;
			}
			if(r.nextInt(LOOT_RARITY_DISINTEGRATOR) == 0){
				ItemStack disintegrator = new ItemStack(StargateTech.disintegrator.itemID, 1, 0);
				loot.add(disintegrator);
				value += LOOT_RARITY_DISINTEGRATOR;
			}
			if(r.nextInt(LOOT_RARITY_DISMANTLER) == 0){
				ItemStack dismantler = new ItemStack(StargateTech.dismantler.itemID, 1, 0);
				loot.add(dismantler);
				value += LOOT_RARITY_DISMANTLER;
			}
		}
		for(ItemStack stack : loot){
			boolean placed = false;
			while(!placed){
				int slot = r.nextInt(27);
				if(chest.getStackInSlot(slot) == null){
					chest.setInventorySlotContents(slot, stack);
					placed = true;
				}
			}
		}
	}
	
	private static void generateGrassRuin(Random r, World w, int x, int y, int z){
		if(r.nextInt(RUIN_RARITY_GRASS) != 0) return;
		int block = Block.stoneBrick.blockID;
		w.setBlockAndMetadataWithNotify(x, y-2, z, block, 0, Helper.SETBLOCK_NO_UPDATE);
		WorldGenerationHelper.instance.worldgenHorizontalRect(w, x-1, y-1, z-1, 3, 3, block, 0);
		WorldGenerationHelper.instance.worldgenHorizontalRect(w, x-2, y+0, z-2, 5, 5, block, 0);
		WorldGenerationHelper.instance.worldgenHorizontalRectCorner(w, x-2, y+1, z-2, 5, 5, 0, 0, block, 3);
		WorldGenerationHelper.instance.worldgenHorizontalRectCorner(w, x-2, y+2, z-2, 5, 5, 0, 0, block, 3);
		WorldGenerationHelper.instance.worldgenHorizontalRectCorner(w, x-2, y+3, z-2, 5, 5, 0, 0, block, 3);
		WorldGenerationHelper.instance.worldgenHorizontalRectCorner(w, x-2, y+4, z-2, 5, 5, 0, 0, block, 3);
		WorldGenerationHelper.instance.worldgenHorizontalRect(w, x-2, y+5, z-2, 5, 5, block, 0);
		generateLootChest(r, w, x, y, z);
	}
	
	private static void generateDesertRuin(Random r, World w, int x, int y, int z){
		if(r.nextInt(RUIN_RARITY_DESERT) != 0) return;
		int block = Block.sandStone.blockID;
		w.setBlockAndMetadataWithNotify(x, y-2, z, block, 0, Helper.SETBLOCK_NO_UPDATE);
		WorldGenerationHelper.instance.worldgenHorizontalRect(w, x-1, y-1, z-1, 3, 3, block, 0);
		WorldGenerationHelper.instance.worldgenHorizontalRect(w, x-2, y+0, z-2, 5, 5, block, 0);
		WorldGenerationHelper.instance.worldgenHorizontalRectCorner(w, x-2, y+1, z-2, 5, 5, 0, 0, block, 1);
		WorldGenerationHelper.instance.worldgenHorizontalRectCorner(w, x-2, y+2, z-2, 5, 5, 0, 0, block, 1);
		WorldGenerationHelper.instance.worldgenHorizontalRectCorner(w, x-2, y+3, z-2, 5, 5, 0, 0, block, 1);
		WorldGenerationHelper.instance.worldgenHorizontalRectCorner(w, x-2, y+4, z-2, 5, 5, 0, 0, block, 1);
		WorldGenerationHelper.instance.worldgenHorizontalRect(w, x-2, y+5, z-2, 5, 5, block, 2);
		generateLootChest(r, w, x, y, z);
	}
	
	private static void generateWaterRuin(Random r, World w, int x, int y, int z){
		while(w.getBlockId(x, y, z) == Block.waterStill.blockID){
			y--;
		}
		y++;
		if(y > 56) return;
		if(r.nextInt(RUIN_RARITY_WATER) != 0) return;
		
		int shield = StargateTech.shield.blockID;
		int meta = ~Shield.BLOCK_PLAYER;
		int stone = Block.stoneBrick.blockID;
		w.setBlockAndMetadataWithNotify(x, y-2, z, shield, 0, Helper.SETBLOCK_NO_UPDATE);
		WorldGenerationHelper.instance.worldgenHorizontalRect(w, x-1, y-1, z-1, 3, 3, stone, 0);
		WorldGenerationHelper.instance.worldgenHorizontalRect(w, x-2, y+0, z-2, 5, 5, stone, 0);
		WorldGenerationHelper.instance.worldgenHorizontalRectCorner(w, x-2, y+1, z-2, 5, 5, 0, 0, stone, 3);
		WorldGenerationHelper.instance.worldgenHorizontalRectCorner(w, x-2, y+2, z-2, 5, 5, 0, 0, stone, 3);
		WorldGenerationHelper.instance.worldgenHorizontalRectCorner(w, x-2, y+3, z-2, 5, 5, 0, 0, stone, 3);
		WorldGenerationHelper.instance.worldgenHorizontalRectCorner(w, x-2, y+4, z-2, 5, 5, 0, 0, stone, 3);
		WorldGenerationHelper.instance.worldgenHorizontalRect(w, x-1, y+5, z-1, 3, 3, shield, meta);
		WorldGenerationHelper.instance.worldgenHorizontalRectFrame(w, x-2, y+5, z-2, 5, 5, stone, 0);
		
		WorldGenerationHelper.instance.worldgenVerticalRectX(w, x-2, y+1, z-1, 4, 3, shield, meta);
		WorldGenerationHelper.instance.worldgenVerticalRectX(w, x+2, y+1, z-1, 4, 3, shield, meta);
		WorldGenerationHelper.instance.worldgenVerticalRectZ(w, x-1, y+1, z-2, 3, 4, shield, meta);
		WorldGenerationHelper.instance.worldgenVerticalRectZ(w, x-1, y+1, z+2, 3, 4, shield, meta);
		
		generateLootChest(r, w, x, y, z);
	}
}