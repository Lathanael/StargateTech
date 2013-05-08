package lordfokas.stargatetech.world;

import lordfokas.stargatetech.rendering.RenderMetalBlock;

public class NaquadahBlock extends NaquadahExplosive {

	public NaquadahBlock(int id){
		super(id, "naquadahBlock", 7.5F);
		texturename = "blockNaquadah";
	}
	
	@Override
    public int getRenderType(){
    	return RenderMetalBlock.instance().getRenderId();
    }
}