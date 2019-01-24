package cn.edu.fjnu.towide.util;

import org.bson.types.ObjectId;

public class IdGenerator {
	public static String getId(){
		
		ObjectId id=new ObjectId();
		return id.toHexString();
	}
}
