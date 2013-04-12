package lordfokas.stargatetech.world;

import lordfokas.stargatetech.rendering.RenderMetalBlock;

public class NaquadriaBlock extends NaquadriaExplosive {
	public NaquadriaBlock(int id){
		super(id, "naquadriaBlock", 10F);
		texturename = "blockNaquadria";
	}
	
	@Override
    public int getRenderType(){
    	return RenderMetalBlock.instance().getRenderId();
    }
}
