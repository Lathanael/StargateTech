package lordfokas.stargatetech.util;

/**
 * This is supposed to be used along with packets as an utility to read and write values to byte arrays.
 * Very early WIP... (Isn't the whole mod?)
 * @author LordFokas
 */
public class ByteUtil {
	private ByteUtil(){}
	
	public class ByteArrayPointer{
		private int pointer = 0;
		
		public void rewind(){
			pointer = 0;
		}
		
		public void advance(int c){
			pointer += c;
		}
		
		public void seek(int c){
			pointer = c;
		}
		
		public int getPointer(){
			return pointer;
		}
		
	}
	
	public static int readInt(byte[] array, ByteArrayPointer bap){
		int pointer = bap.getPointer();
		byte b0, b1, b2, b3;
		b0 = array[pointer + 0];
		b1 = array[pointer + 1];
		b2 = array[pointer + 2];
		b3 = array[pointer + 3];
		bap.advance(4);
		return (b0 << 24) + (b1 << 16) + (b2 << 8) + b3;
	}
	
	public static void writeInt(int value, byte[] array, ByteArrayPointer bap){
		int pointer = bap.getPointer();
		array[pointer + 0] = (byte)((value & 0xFF000000) >> 24 );
		array[pointer + 1] = (byte)((value & 0x00FF0000) >> 16 );
		array[pointer + 2] = (byte)((value & 0x0000FF00) >> 8 );
		array[pointer + 3] = (byte) (value & 0x000000FF);
		bap.advance(4);
	}
}
