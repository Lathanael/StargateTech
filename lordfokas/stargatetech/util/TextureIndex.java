package lordfokas.stargatetech.util;

/**
 * Holds the texture index for everything in every sprite.
 * It provides an easier way to manage these values in case the sprite sheet indexes need to be changed.
 * @author LordFokas
 */
public final class TextureIndex {
	private TextureIndex(){}
	
	/* ########################
	** BLOCKS
	*/
	
	// Common Block
	public final static int voidTexture = 0x0F;
	public final static int blockSingle = 0x00;
	public final static int blockTop = 0x10;
	public final static int blockMiddle = 0x20;
	public final static int blockBottom = 0x30;
	
	// Lantean Block
	public final static int lanteanHexFloor = 0x44;
	public final static int lanteanQuadEmit	= 0x46;
	public final static int lanteanQuadSide	= 0x36;
	
	// Ores
	public final static int oreBase = 0x1F;
	public final static int naquadahGlow = 0x0E;
	public final static int naquadriaGlow = 0x1E;
	public final static int oreNaquadah = 0x0D;
	public final static int oreNaquadria = 0x1D;
	
	// Networking Interfaces
	public final static int networkSlot = 0x02;
	public final static int ionTubeBlueLight = 0x2E;
	public final static int powerConduitYellowLight = 0x3E;
	public final static int slotRedLight = 0x4E;
	
	// Power Transport
	public final static int ionTube = 0x3F;
	public final static int ionTubeJoint = 0x4F;
	public final static int powerConduit = 0x5F;
	public final static int powerConduitJoint = 0x6F;
	
	// Shields
	public final static int shield = 0x2F;
	public final static int shieldEmitterSingle = 0x01;
	public final static int shieldEmitterTop = 0x11;
	public final static int shieldEmitterMiddle = 0x21;
	public final static int shieldEmitterBottom = 0x31;
	public final static int shieldEmitterYFace = 0x41;
	
	// Particle Ionizer
	public final static int particleIonizerYFace = 0x42;
	public final static int particleIonizerSide = 0x52;
	
	// Naquadah Generator
	public final static int naquadahGenerator = 0x04;
	
	// Naquadah Rail
	public final static int naquadahRail = 0xFF;
	
	// Dialing Computer
	public final static int dialingComputer = 0x24;
	
	
	
	/* ########################
	** ITEMS
	*/
	
	// Ores
	public final static int naquadahShard = 0x00;
	public final static int naquadriaShard = 0x01;
	public final static int naquadahShardCluster = 0x10;
	public final static int naquadriaShardCluster = 0x11;
	public final static int naquadahIngot = 0x20;
	public final static int naquadriaIngot = 0x21;
	
	// Tools
	public final static int disintegrator = 0x24;
	public final static int dismantler = 0x26;
	public final static int mechanusClavia = 0x15;
	public final static int personalShield = 0x03;
	
	// Stargate
	public final static int stargate = 0x44;
	
	// Crystal
	public final static int addressMemoryCrystal = 0x35;
	
}