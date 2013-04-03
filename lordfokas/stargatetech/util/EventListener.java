package lordfokas.stargatetech.util;

import java.util.ArrayList;
import java.util.logging.Level;

import lathanael.stargatetech.entity.EntityStargatePainting;
import lathanael.stargatetech.util.BlockUtils;
import lathanael.stargatetech.util.StargateLogger;
import lordfokas.stargatetech.StargateTech;
import lordfokas.stargatetech.networks.stargate.StargateNetwork;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.WorldEvent;

public final class EventListener {
	private static final EventListener instance = new EventListener();
	private static boolean registered = false;
	
	/** What types of damage the Personal Shield blocks. */
	private ArrayList<DamageSource> blocked = new ArrayList<DamageSource>();
	
	private EventListener(){
		// Add Damage Sources to the blocked list.
		blocked.add(DamageSource.anvil);
		blocked.add(DamageSource.cactus);
		blocked.add(DamageSource.fall);
		blocked.add(DamageSource.fallingBlock);
		blocked.add(DamageSource.inFire);
		blocked.add(DamageSource.lava);
		blocked.add(DamageSource.magic);
		blocked.add(DamageSource.onFire);
		blocked.add(DamageSource.wither);
	}
	
	// Register itself to Forge Event Bus
	public static void register(){
		if(registered) return;
		MinecraftForge.EVENT_BUS.register(instance);
		registered = true;
	}
	
	@ForgeSubscribe // Block damage if the player has any Personal Shield.
	public void onPlayerDamaged(LivingHurtEvent event){
		if(event.entity instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) event.entity;
			int shieldID = StargateTech.personalShield.itemID;
			if(player.inventory.hasItem(shieldID)){ // Does the Player have a Personal Shield?
				if(blocked.contains(event.source) || event.source.isProjectile() || event.source instanceof EntityDamageSource){
					boolean deny = false;
					for(int i = 0; i < 36; i++){ // Find the shield. Discharge it a bit.
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
	
	/**
	 * Painting Listener. Changes the painting if it is placed on a StargateTech block.
	 * 
	 * @param event The Forge {@link net.minecraftforge.event.entity.EntityJoinWorldEvent EntityJoinWorldEvent}
	 */
	@ForgeSubscribe
	public void onEntityJoinWorldEvent(final EntityJoinWorldEvent event) {
		StargateLogger.log(Level.INFO, "Checking entity...");
		if (event.entity instanceof EntityPainting && !(event.entity instanceof EntityStargatePainting)) {
			StargateLogger.log(Level.INFO, "Is painting, checking block...");
			if (BlockUtils.onValidStargateBlock((EntityPainting) event.entity)) {
				StargateLogger.log(Level.INFO, "Replacing painting");
				event.entity.worldObj.spawnEntityInWorld(new EntityStargatePainting((EntityPainting) event.entity));
				if (event.isCancelable()) event.setCanceled(true);
				// TODO: Get player who placed the painting and remove 1 painting from his invetory when not in creative
			}
		}
	}
}
