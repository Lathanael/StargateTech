package lordfokas.stargatetech.common;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import lordfokas.stargatetech.StargateTech;

public class StargateTab extends CreativeTabs {
	public static final StargateTab instance = new StargateTab();
	
	public StargateTab() {
		super("stargateTech.creative");
	}
	
	@SideOnly(Side.CLIENT)
	public int getTabIconItemIndex()
    {
        return StargateTech.naquadriaIngot.shiftedIndex;
    }
	
	@SideOnly(Side.CLIENT)
    public String getTabLabel()
    {
        return "Stargate Tech";
    }
	 
	@SideOnly(Side.CLIENT)
    public String getTranslatedTabLabel()
    {
        return this.getTabLabel();
    }
}
