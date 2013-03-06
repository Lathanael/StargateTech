package lordfokas.stargatetech.networks.stargate;

import java.io.IOException;
import java.io.RandomAccessFile;

import net.minecraft.world.World;

/**
 * Maps Stargate Addresses to physical locations.
 * Used by the StargateNetwork,
 * @author LordFokas
 */
public class AddressHandler{
	private Address address;
	private int dim = 0;
	private int x = 0;
	private int y = 0;
	private int z = 0;
	
	public AddressHandler(Address addr, boolean debug){
		address = addr;
		if(debug) System.out.println("CREATING ADDR: " + address.getName());
	}
	
	public AddressHandler(RandomAccessFile file) throws IOException{
		Symbol[] symbols = new Symbol[9];
		for(int i = 0; i < 9; i++){
			symbols[i] = Symbol.symbols[file.readShort()];
		}
		address = new Address(symbols);
		System.out.println("LOADING ADDR: " + address.getName());
		
		dim = file.readInt();
		x = file.readInt();
		y = file.readInt();
		z = file.readInt();
	}
	
	public void save(RandomAccessFile file) throws IOException{
		for(Symbol sym : address.getSymbols()){
			file.writeShort(sym.getID());
		}
		file.writeInt(dim);
		file.writeInt(x);
		file.writeInt(y);
		file.writeInt(z);
		
		System.out.println("SAVING ADDR: " + address.getName());
	}
	
	public void setCoordinates(World w, int x, int y, int z){
		dim = w.provider.dimensionId;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public boolean is(World w, int x, int y, int z){
		if(w.provider.dimensionId != dim) return false;
		return this.x == x && this.y == y && this.z == z;
	}
	
	public Address getAddress(){
		return address;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof AddressHandler)) return false;
		return address.equals(((AddressHandler)o).address);
	}
}
