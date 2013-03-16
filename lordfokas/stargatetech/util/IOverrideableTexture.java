package lordfokas.stargatetech.util;

import net.minecraft.util.Icon;

/**
 * Used by custom block renderers to override block textures before rendering
 * and then restore the original textures afterwards.
 * Unlike the RenderBlock's override, this supports overriding every block face
 * with a diferent texture at once.
 * @author LordFokas
 */
public interface IOverrideableTexture {
	public void overrideTextures(Icon[] tmap);
	public void restoreTextures();
}
