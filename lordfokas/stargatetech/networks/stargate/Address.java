package lordfokas.stargatetech.networks.stargate;

/**
 * Represents a Stargate Address
 * @author LordFokas
 *
 */
public class Address {
	private Symbol[] symbol = new Symbol[9];
	private boolean valid = false;
	
	public Address(Symbol[] symbols){
		init(symbols);
	}
	
	public Address(short[] ids){
		Symbol[] symbols = new Symbol[ids.length];
		for(short i = 0; i < ids.length; i++){
			symbols[i] = Symbol.symbols[ids[i]];
		}
		init(symbols);
	}
	
	private void init(Symbol[] symbols){
		for(int i = 0; i < 9; i++){
			symbol[i] = Symbol.NONE;
		}
		if(symbols.length < 7) return;
		if(symbols.length > 9) return;
		boolean[] used = new boolean[40];
		for(int i = 0; i < 40; i++){
			used[i] = false;
		}
		valid = true;
		int count = 0;
		for(Symbol sym : symbols){
			if(sym != null){
				count++;
				short id = sym.getID();
				if(used[id]) valid = false;
				used[id] = true;
			}
		}
		valid = (count >= 7 && count <= 9);
		if(valid){
			int p = 0;
			for(int i = 0; i < symbols.length; i++){
				if(symbols[i] != null){
					symbol[p] = symbols[i];
					p++;
				}
			}
		}
	}
	
	/**
	 * @return The name for this address, based on the symbol components.
	 */
	public String getName(){
		String w1 = new StringBuffer(symbol[0].getName()).append(symbol[1].getName().toLowerCase()).append(symbol[2].getName().toLowerCase()).toString();
		String w2 = new StringBuffer(symbol[3].getName()).append(symbol[4].getName().toLowerCase()).append(symbol[5].getName().toLowerCase()).toString();
		String w3 = new StringBuffer(symbol[6].getName()).append(symbol[7].getName().toLowerCase()).append(symbol[8].getName().toLowerCase()).toString();
		return new StringBuffer(w1).append(" ").append(w2).append(" ").append(w3).toString();
	}
	
	public boolean isValid(){
		return valid;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Address)) return false;
		Address address = (Address) o;
		for(int i = 0; i < 9; i++){
			if(this.symbol[i] == null || address.symbol[i] == null){
				boolean localVoid = (this.symbol[i] == null || this.symbol[i].getID() == 0);
				boolean extVoid = (address.symbol[i] == null || address.symbol[i].getID() == 0);
				if(localVoid != extVoid) return false;
			}else{
				if(!this.symbol[i].equals(address.symbol[i])) return false;
			}
		}
		return true;
	}
	
	public Symbol[] getSymbols(){
		return symbol;
	}
}
