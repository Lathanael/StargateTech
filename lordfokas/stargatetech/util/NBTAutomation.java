package lordfokas.stargatetech.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import net.minecraft.nbt.NBTTagCompound;

public final class NBTAutomation{
	private NBTAutomation(){}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface NBTField{}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface NBTFieldCompound{}
	
	public static boolean save(Object entity, NBTTagCompound nbt){
		Class cls = entity.getClass();
		if(!cls.isAnnotationPresent(NBTFieldCompound.class)) return false;
		Field[] fields = cls.getDeclaredFields();
		for(Field field : fields){
			if(field.isAnnotationPresent(NBTField.class)){
				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				Class typeClass = field.getType();
				String typeName = typeClass.getSimpleName();
				if(typeClass.isAnnotationPresent(NBTFieldCompound.class)){
					NBTTagCompound c = new NBTTagCompound();
					if(save(field, c)){
						nbt.setCompoundTag(field.getName(), c);
					}
				}else try{
					if(typeName == "short"){
						nbt.setShort(field.getName(), field.getShort(entity));
					}else if(typeName == "int"){
						nbt.setInteger(field.getName(), field.getInt(entity));
					}else if(typeName == "long"){
						nbt.setLong(field.getName(), field.getLong(entity));
					}else if(typeName == "boolean"){
						nbt.setBoolean(field.getName(), field.getBoolean(entity));
					}else if(typeName == "float"){
						nbt.setFloat(field.getName(), field.getFloat(entity));
					}else if(typeName == "double"){
						nbt.setDouble(field.getName(), field.getDouble(entity));
					}else if(typeName == "byte"){
						nbt.setByte(field.getName(), field.getByte(entity));
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				field.setAccessible(accessible);
			}
		}
		return true;
	}
	
	public static void load(Object entity, NBTTagCompound nbt){
		Class cls = entity.getClass();
		if(!cls.isAnnotationPresent(NBTFieldCompound.class)) return;
		Field[] fields = cls.getDeclaredFields();
		for(Field field : fields){
			if(field.isAnnotationPresent(NBTField.class)){
				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				Class typeClass = field.getType();
				String typeName = typeClass.getSimpleName();
				if(typeClass.isAnnotationPresent(NBTFieldCompound.class)){
					NBTTagCompound c = nbt.getCompoundTag(field.getName());
					try{
						load(field.get(typeClass), c);
					}catch(Exception e){
						e.printStackTrace();
					}
				}else try{
					if(typeName == "short"){
						field.set(entity, nbt.getShort(field.getName()));
					}else if(typeName == "int"){
						field.set(entity, nbt.getInteger(field.getName()));
					}else if(typeName == "long"){
						field.set(entity, nbt.getLong(field.getName()));
					}else if(typeName == "boolean"){
						field.set(entity, nbt.getBoolean(field.getName()));
					}else if(typeName == "float"){
						field.set(entity, nbt.getFloat(field.getName()));
					}else if(typeName == "double"){
						field.set(entity, nbt.getDouble(field.getName()));
					}else if(typeName == "byte"){
						field.set(entity, nbt.getByte(field.getName()));
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				field.setAccessible(accessible);
			}
		}
	}
	
}
