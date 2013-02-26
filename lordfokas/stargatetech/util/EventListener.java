package lordfokas.stargatetech.util;

import java.util.ArrayList;

import lordfokas.stargatetech.StargateTech;
import lordfokas.stargatetech.networks.stargate.StargateNetwork;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.WorldEvent;

public final class EventListener {
	private static final EventListener instance = new EventListener();
	private static boolean registered = false;
	private ArrayList<DamageSource> blocked = new ArrayList<DamageSource>();
	
	private EventListener(){
		blocked.add(DamageSource.anvil);
		blocked.add(DamageSource.cactus);
		blocked.add(DamageSource.explosion);
		blocked.add(DamageSource.fall);
		blocked.add(DamageSource.fallingBlock);
		blocked.add(DamageSource.field_76375_l);
		blocked.add(DamageSource.inFire);
		blocked.add(DamageSource.lava);
		blocked.add(DamageSource.magic);
		blocked.add(DamageSource.onFire);
		blocked.add(DamageSource.wither);
	}
	
	public static void register(){
		if(registered) return;
		MinecraftForge.EVENT_BUS.register(instance);
		registered = true;
	}
	
	@ForgeSubscribe
	public void onPlayerDamaged(LivingHurtEvent event){
		if(event.entity instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) event.entity;
			int shieldID = StargateTech.personalShield.shiftedIndex;
			if(player.inventory.hasItem(shieldID)){
				if(blocked.contains(event.source) || event.source.isProjectile() || event.source instanceof EntityDamageSource){
					boolean deny = false;
					for(int i = 0; i < 36; i++){
						ItemStack stack = player.inventory.mainInventory[i];
						if(stack != null){
							if(stack.itemID == shieldID){
								stack.damageItem(event.ammount, player);
								deny = true;
								break;
							}
						}
					}
					if(deny){
						player.extinguish();
						event.setCanceled(true);
					}
				}
			}
		}
	}
	
	@ForgeSubscribe
	public void onWorldLoad(WorldEvent.Load event){
		StargateNetwork.load(event.world);
	}
	
	@ForgeSubscribe
	public void onWorldUnload(WorldEvent.Unload event){
		StargateNetwork.unload(event.world);
	}
}