package lordfokas.stargatetech.networks.stargate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import lordfokas.stargatetech.machine.StargateTE;
import lordfokas.stargatetech.util.StargateLogger;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

/**
 * Manages -ALL- the Stargate Addresses.
 * Stargates dial, but it's still a bit buggy.
 * @author LordFokas
 */
public final class StargateNetwork {
	private static class SpecialAddresses{
		public static final Address Earth				= new Address(new short[]{28, 26,  5, 36, 11, 29,  1});
		public static final Address ProclarushTaonas	= new Address(new short[]{35,  3, 31, 29,  5, 17,  1});
		public static final Address Abydos				= new Address(new short[]{27,  7, 15, 32, 12, 30,  1});
		public static final Address Camelot				= new Address(new short[]{20,  2, 35,  8, 26, 15,  1});
	}
	
	private static StargateNetwork network = null;
	private ArrayList<Address> specialAddresses = new ArrayList<Address>();
	private CopyOnWriteArrayList<AddressHandler> addresses = new CopyOnWriteArrayList<AddressHandler>();
	private boolean loaded = false;
	private boolean canSave = false;
	private RandomAccessFile file = null;
	
	private StargateNetwork(){
		StargateLogger.info("Initializing the StargateNetwork...");
		specialAddresses.add(SpecialAddresses.Earth);
		specialAddresses.add(SpecialAddresses.ProclarushTaonas);
		specialAddresses.add(SpecialAddresses.Abydos);
		specialAddresses.add(SpecialAddresses.Camelot);
		load();
	}
	
	public static StargateNetwork instance(){
		return network;
	}
	
	public static void init(){
		network = new StargateNetwork();
	}
	
	public static void unload(){
		if(network != null){
			network.shutdown();
		}else{
			StargateLogger.severe("Couldn't unload StargateNetwork because the instance is null!");
		}
	}
	
	public static void dial(Address src, Address tgt){
		if(network != null){
			network.tryDial(src, tgt);
		}else{
			StargateLogger.severe("Couldn't dial because the StargateNetwork instance is null!");
		}
	}
	
	private AddressHandler getHandler(Address address){
		for(AddressHandler handler : addresses){
			if(handler.getAddress().equals(address)){
				return handler;
			}
		}
		return null;
	}
	
	private void shutdown(){
		if(file != null){
			try {
				file.close();
			}catch(IOException e){
				StargateLogger.severe("An Exception occurred when trying to shutdown the StargateNetwork!");
				e.printStackTrace();
			}
		}
	}
	
	public Address getNewRandomAddress(World w, int x, int y, int z){
		if(w.isRemote) return null;
		Address addr;
		boolean ok = false;
		do{
			ArrayList<Short> used = new ArrayList<Short>();
			short[] symbols = new short[8];
			Random r = new Random();
			for(int i = 0; i < 8; i++){
				boolean unique = false;
				do{
					short s = (short) (1 + r.nextInt(39));
					if(s > 0 && s < 40 && !used.contains(s)){
						used.add(s);
						symbols[i] = s;
						unique = true;
					}
				}while(!unique);
			}
			addr = new Address(symbols);
		}while(specialAddresses.contains(addr) || addresses.contains(new AddressHandler(addr, false)));
		AddressHandler ah = new AddressHandler(addr, true);
		ah.setCoordinates(w, x, y, z);
		addresses.add(ah);
		save();
		return addr;
	}
	
	public Address getMyAddress(World w, int x, int y, int z){
		for(AddressHandler ah : addresses){
			if(ah.is(w, x, y, z)) return ah.getAddress();
		}
		return null;
	}
	
	public void remove(World w, int x, int y, int z){
		for(AddressHandler handler : addresses){
			if(handler.is(w, x, y, z)){
				addresses.remove(handler);
			}
		}
	}
	
	private String getStargateAddressFileName(){
		String dir = DimensionManager.getCurrentSaveRootDirectory().getAbsolutePath();
		return dir.concat("/stargates.dat");
	}
	
	private void load(){
		String filepath = getStargateAddressFileName();
		boolean problem = false;
		try{
			System.out.println("Loading Stargate address list...");
			file = new RandomAccessFile(filepath, "r");
			if(file.length() != 0){
				int count = file.readInt();
				if(count > 0){
					for(int i = 0; i < count; i++){
						addresses.add(new AddressHandler(file));
					}
				}
				System.out.println("Successfully loaded all the addresses. Address count: " + count);
			}
		}catch(Exception e){
			if(e instanceof FileNotFoundException){
				System.out.println("Unable to load the Stargate Network save file. This is normal when the mod runs for the first time in a world.");
				File dummy = new File(filepath);
				boolean error = false;
				try{
					dummy.createNewFile();
				}catch(IOException ioe){
					error = true;
					System.err.println("Failed to create the Stargate Network save file. Perhaps something is wrong?");
					ioe.printStackTrace();
				}
				if(!error){
					System.out.println("Successfully created the Stargate Network save file (" + filepath + ")");
					canSave = true;
				}
			}else{
				problem = true;
				e.printStackTrace();
			}
		}
		if(!problem){
			System.out.println("...Stargate address list loaded with no problems. All is OK!");
			canSave = true;
		}else{
			System.err.println("Because of errors loading the network file, it will not save to prevent corruption.");
		}
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		loaded = true;
	}
	
	private void save(){
		if(!canSave) return;
		boolean problem = false;
		try{
			System.out.println("Saving Stargate address list...");
			file = new RandomAccessFile(getStargateAddressFileName(), "rw");
			file.writeInt(addresses.size());
			int count = 0;
			for(AddressHandler ah : addresses){
				ah.save(file);
				count++;
			}
			System.out.println("Successfully saved all the addresses. Address count: " + count);
		}catch(Exception e){
			problem = true;
			e.printStackTrace();
		}
		if(!problem) System.out.println("...Stargate address list saved with no problems.");
		try{
			file.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void tryDial(Address src, Address tgt){
		AddressHandler source = getHandler(src);
		AddressHandler target = getHandler(tgt);
		if(source != null && target != null){
			if(source.getD() != target.getD()){
				World srcWorld = DimensionManager.getWorld(source.getD());
				World tgtWorld = DimensionManager.getWorld(target.getD());
				if(srcWorld != null && tgtWorld != null){
					TileEntity srcte = srcWorld.getBlockTileEntity(source.getX(), source.getY(), source.getZ());
					TileEntity tgtte = tgtWorld.getBlockTileEntity(target.getX(), target.getY(), target.getZ());
					if(srcte != null && srcte instanceof StargateTE && tgtte != null && tgtte instanceof StargateTE){
						StargateTE srcGate = (StargateTE) srcte;
						StargateTE tgtGate = (StargateTE) tgtte;
						int time = 38 * 20;
						srcGate.openWormhole(time, true, target);
						tgtGate.openWormhole(time, false, null);
					}else{
						System.out.println("Couldn't dial because some gates weren't there!");
					}
				}else{
					System.out.println("Couldn't dial because some worlds were null!");
				}
			}else{
				System.out.println("Can't teleport in the same dimension!");
			}
		}else{
			System.out.println("Couldn't dial because some addresses were null!");
		}
	}
}
