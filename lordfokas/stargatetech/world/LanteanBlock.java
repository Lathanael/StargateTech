package lordfokas.stargatetech.world;

import static net.minecraftforge.common.ForgeDirection.UP;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import lordfokas.stargatetech.common.BaseBlock;
import lordfokas.stargatetech.util.Helper;
import lordfokas.stargatetech.util.IconRegistry;
import lordfokas.stargatetech.util.UnlocalizedNames;

/**
 * This was a block which would have several metadata sub blocks (16, actually)
 * It would be used as the mod's "Fancy Block". I still want to implement it,
 * so this stub stays. For now, at least.
 * @author LordFokas
 */
public class LanteanBlock extends BaseBlock {
	public LanteanBlock(int id) {
		super(id, UnlocalizedNames.BLOCK_LANTEAN);
		this.texturename = "lantean-00-simple";
	}
	
	@Override
	public Icon getBlockTextureFromSideAndMetadata(int side, int meta){
		if(side != Helper.dirTop) return IconRegistry.lantean00simple;
		if(meta == 0) return IconRegistry.lantean01hexFloor;
		return IconRegistry.lantean02verticalEmitter;
	}
	
	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, World world, int x, int y, int z){
        return false;
    }
}
