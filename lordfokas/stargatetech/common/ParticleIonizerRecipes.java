package lordfokas.stargatetech.common;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

/**
 * A manager for items usable in the Particle Ionizer.
 * It still needs some work.
 * @author LordFokas
 */
public final class ParticleIonizerRecipes {
	public static final ParticleIonizerRecipes instance = new ParticleIonizerRecipes();
	private ParticleIonizerRecipes(){}
	
	private ArrayList<IonizerRecipe> recipes = new ArrayList();
	
	public class IonizerRecipe implements Cloneable{
		public int id, dmg, ionsPerTick, power;
		
		public IonizerRecipe(int i, int d, int t, int p){
			id = i;
			dmg = d;
			ionsPerTick = t;
		}
		
		@Override
		public boolean equals(Object o){
			if(!(o instanceof IonizerRecipe)) return false;
			IonizerRecipe ir = (IonizerRecipe) o;
			return (ir.id == id && ir.dmg == dmg);
		}
		
		@Override public IonizerRecipe clone()
			{ return new IonizerRecipe(id, dmg, ionsPerTick, power); }
	}
	
	public static void add(int id, int dmg, int ions, int p){ instance.addNew(id, dmg, ions, p); }
	public static void add(ItemStack stack, int ions, int p){ instance.addNew(stack.itemID, stack.getItemDamage(), ions, p); }
	public static void add(int id, int dmg, int ions){ instance.addNew(id, dmg, ions); }
	public static void add(ItemStack stack, int ions){ instance.addNew(stack.itemID, stack.getItemDamage(), ions); }
	
	private void addNew(int i, int d, int t)
		{ addNew(i, d, t, 100); }
	
	private void addNew(int i, int d, int t, int p){
		if(p < 1) p = 100;
		IonizerRecipe ir = new IonizerRecipe(i, d, t, p);
		if(!recipes.contains(ir))
			recipes.add(ir);
	}
	
	public static IonizerRecipe get(ItemStack stack){ return instance.get(stack.itemID, stack.getItemDamage()); }
	
	private IonizerRecipe get(int i, int d){
		IonizerRecipe ir = new IonizerRecipe(i, d, 0, 100);
		if(!recipes.contains(ir)) return null;
		else return recipes.get(recipes.indexOf(ir)).clone();
	}
}
