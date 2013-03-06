package lordfokas.stargatetech.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import lordfokas.stargatetech.StargateTech;

/**
 * The tab under which all our items and blocks will show up.
 * @author LordFokas
 */
public class StargateTab extends CreativeTabs {
	public static final StargateTab instance = new StargateTab();
	
	public StargateTab() {
		super("stargateTech.creative");
	}
	
	@SideOnly(Side.CLIENT)
	public int getTabIconItemIndex()
    {
        return StargateTech.naquadriaIngot.itemID;
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
