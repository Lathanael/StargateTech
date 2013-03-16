package lordfokas.stargatetech;

import lordfokas.stargatetech.common.ParticleIonizerRecipes;
import lordfokas.stargatetech.machine.DialingComputerTE;
import lordfokas.stargatetech.machine.NaquadahGeneratorTE;
import lordfokas.stargatetech.machine.ParticleIonizerTE;
import lordfokas.stargatetech.machine.ShieldEmitterTE;
import lordfokas.stargatetech.machine.StargateTE;
import lordfokas.stargatetech.packet.PacketHandler;
import lordfokas.stargatetech.packet.PacketHandlerServer;
import lordfokas.stargatetech.util.EventListener;
import lordfokas.stargatetech.util.GUIHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy {	
	public void init(){
		addBlocks();
		addItems();
		addRecipes();
		addTileEntities();
		refineContentProperties();
		GameRegistry.registerWorldGenerator(StargateTech.worldGen);
		NetworkRegistry.instance().registerGuiHandler(StargateTech.instance, GUIHandler.instance);
		NetworkRegistry.instance().registerChannel(PacketHandlerServer.instance, PacketHandler.CHANNEL_STARGATE, Side.SERVER);
		EventListener.register();
	}
	
	private void addBlocks(){
		registerBlock(StargateTech.shieldEmitter, "Lantean Shield Emitter");
		registerBlock(StargateTech.shield, "Lantean Shield");
		registerBlock(StargateTech.particleIonizer, "Particle Ionizer");
		registerBlock(StargateTech.ionTube, "Ion Tube");
		registerBlock(StargateTech.naquadahOre, "Naquadah Ore");
		registerBlock(StargateTech.naquadriaOre, "Naquadria Ore");
		registerBlock(StargateTech.powerConduit, "Power Conduit");
		registerBlock(StargateTech.naquadahGenerator, "Naquadah Generator");
		registerBlock(StargateTech.stargate, "Stargate");
		registerBlock(StargateTech.dialingComputer, "Dialing Computer");
		registerBlock(StargateTech.lanteanBlock, "Lantean Block");
	}
	
	private void addItems(){
		registerItem(StargateTech.naquadahShard, "Naquadah Shard");
		registerItem(StargateTech.naquadriaShard, "Naquadria Shard");
		registerItem(StargateTech.naquadahCluster, "Naquadah Shard Cluster");
		registerItem(StargateTech.naquadriaCluster, "Naquadria Shard Cluster");
		registerItem(StargateTech.naquadahIngot, "Naquadah Ingot");
		registerItem(StargateTech.naquadriaIngot, "Naquadria Ingot");
		registerItem(StargateTech.disintegrator, "Disintegrator");
		registerItem(StargateTech.dismantler, "Dismantler");
		registerItem(StargateTech.mechanusClavia, "Mechanus Clavia");
		registerItem(StargateTech.personalShield, "Personal Shield");
		registerItem(StargateTech.addressMemoryCrystal, "Address Memory Crystal");
	}
	
	private void addRecipes(){
		ItemStack nqhShard = new ItemStack(StargateTech.naquadahShard);
		ItemStack nqiShard = new ItemStack(StargateTech.naquadriaShard);
		ItemStack nqhCluster = new ItemStack(StargateTech.naquadahCluster);
		ItemStack nqiCluster = new ItemStack(StargateTech.naquadriaCluster);
		ItemStack nqhIngot = new ItemStack(StargateTech.naquadahIngot);
		ItemStack nqiIngot = new ItemStack(StargateTech.naquadriaIngot);
		GameRegistry.addShapelessRecipe(nqhCluster, new Object[]{nqhShard, nqhShard, nqhShard, nqhShard, nqhShard, nqhShard, nqhShard, nqhShard});
		GameRegistry.addShapelessRecipe(nqiCluster, new Object[]{nqiShard, nqiShard, nqiShard, nqiShard, nqiShard, nqiShard, nqiShard, nqiShard});
		GameRegistry.addSmelting(nqhCluster.itemID, nqhIngot, 0);
		GameRegistry.addSmelting(nqiCluster.itemID, nqiIngot, 0);
		ParticleIonizerRecipes.add(nqhIngot, 10);
		ParticleIonizerRecipes.add(nqiIngot, 40);
	}
	
	private void addTileEntities(){
		GameRegistry.registerTileEntity(ShieldEmitterTE.class, ShieldEmitterTE.ID);
		GameRegistry.registerTileEntity(ParticleIonizerTE.class, ParticleIonizerTE.ID);
		GameRegistry.registerTileEntity(NaquadahGeneratorTE.class, NaquadahGeneratorTE.ID);
		GameRegistry.registerTileEntity(StargateTE.class, StargateTE.ID);
		GameRegistry.registerTileEntity(DialingComputerTE.class, DialingComputerTE.ID);
	}
	
	private void refineContentProperties(){
		MinecraftForge.setBlockHarvestLevel(StargateTech.naquadahOre, "pickaxe", 2);
	}
	
	private void registerBlock(Block block, String name){
		GameRegistry.registerBlock(block);
		LanguageRegistry.addName(block, name);
	}
	
	private void registerItem(Item item, String name){
		LanguageRegistry.addName(item, name);
	}
}
