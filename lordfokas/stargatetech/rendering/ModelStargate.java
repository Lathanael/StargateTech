package lordfokas.stargatetech.rendering;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import lordfokas.stargatetech.machine.StargateTE;
import lordfokas.stargatetech.util.Helper;

public class ModelStargate extends ModelBase {
	private ModelRenderer ring, chevron, base;
	
	public ModelStargate(){
		ring = new ModelRenderer(this).setTextureSize(192, 96);
		ring.setRotationPoint(0, 44, 0);
		ring.setTextureOffset(0, 0).addBox(3F, -48F, -12F, 10, 4, 24);
		ring.setTextureOffset(0, 28).addBox(4F, -44F, -11F, 8, 4, 22);
		chevron = new ModelRenderer(this, 0, 0).setTextureSize(192, 96);
		chevron.setRotationPoint(0, 44, 0);
		chevron.addBox(2F, 42F, -3.5F, 4, 8, 6);
		base = new ModelRenderer(this, 0, 0).setTextureSize(192, 96);
		base.addBox(0, -8F, -40F, 16, 16, 80);
	}
	
	public void render(StargateTE stargate, float scale){
		for(int i = 0; i < 13; i++){ 
			ring.rotateAngleX = (float)(((360 * i) / 13) / Helper.deg2rad);
			ring.render(scale / 16);
		}
		for(int i = 0; i < 9; i++){
			chevron.rotateAngleX = (float)(((360 * i) / 9) / Helper.deg2rad);
			chevron.render(scale / 16);
		}
		base.render(scale / 16);
	}
}