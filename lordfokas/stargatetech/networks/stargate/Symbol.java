package lordfokas.stargatetech.networks.stargate;

public final class Symbol {
	private short id;
	private String name;
	
	public static Symbol[] symbols = new Symbol[40];
	
	public static final Symbol NONE	= new Symbol( 0, "");
	
	public static final Symbol AT	= new Symbol( 1, "At");
	public static final Symbol AL	= new Symbol( 2, "Al");
	public static final Symbol CLA	= new Symbol( 3, "Cla");
	public static final Symbol UR 	= new Symbol( 4, "Ur");
	public static final Symbol ON 	= new Symbol( 5, "On");
	public static final Symbol DEH 	= new Symbol( 6, "Deh");
	public static final Symbol EC 	= new Symbol( 7, "Ec");
	public static final Symbol MIG 	= new Symbol( 8, "Mig");
	public static final Symbol ALM 	= new Symbol( 9, "Alm");
	public static final Symbol RUM 	= new Symbol(10, "Rum");
	public static final Symbol AR 	= new Symbol(11, "Ar");
	public static final Symbol VA	= new Symbol(12, "Va");
	public static final Symbol COR 	= new Symbol(13, "Cor");
	public static final Symbol PRA 	= new Symbol(14, "Pra");
	public static final Symbol OM	= new Symbol(15, "Om");
	public static final Symbol ET	= new Symbol(16, "Et");
	public static final Symbol AS	= new Symbol(17, "As");
	public static final Symbol US	= new Symbol(18, "Us");
	public static final Symbol GON	= new Symbol(19, "Gon");
	public static final Symbol ORM	= new Symbol(20, "Orm");
	public static final Symbol EM	= new Symbol(21, "Em");
	public static final Symbol AC	= new Symbol(22, "Ac");
	public static final Symbol OTH	= new Symbol(23, "Oth");
	public static final Symbol LOS	= new Symbol(24, "Los");
	public static final Symbol LAN	= new Symbol(25, "Lan");
	public static final Symbol EST	= new Symbol(26, "Est");
	public static final Symbol CRO	= new Symbol(27, "Cro");
	public static final Symbol SIL	= new Symbol(28, "Sil");
	public static final Symbol TA	= new Symbol(29, "Ta");
	public static final Symbol BREI	= new Symbol(30, "Brei");
	public static final Symbol RUSH	= new Symbol(31, "Rush");
	public static final Symbol ERP	= new Symbol(32, "Erp");
	public static final Symbol SET	= new Symbol(33, "Set");
	public static final Symbol ULF	= new Symbol(34, "Ulf");
	public static final Symbol PRO	= new Symbol(35, "Pro");
	public static final Symbol SAL	= new Symbol(36, "Sal");
	public static final Symbol TIS	= new Symbol(37, "Tis");
	public static final Symbol MAC	= new Symbol(38, "Mac");
	public static final Symbol IRT	= new Symbol(39, "Irt");
	
	private Symbol(int id, String name){
		this.id = (short)id;
		this.name = name;
		symbols[id] = this;
	}
	
	public short getID(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean equals(Object s){
		if(!(s instanceof Symbol)) return false;
		Symbol symbol = (Symbol) s;
		return symbol.getID() == this.id;
	}
}
